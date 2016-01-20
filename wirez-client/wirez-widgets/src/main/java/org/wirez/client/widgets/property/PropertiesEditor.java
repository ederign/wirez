/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.client.widgets.property;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.uberfire.ext.properties.editor.model.PropertyEditorCategory;
import org.uberfire.ext.properties.editor.model.PropertyEditorEvent;
import org.uberfire.ext.properties.editor.model.PropertyEditorFieldInfo;
import org.uberfire.ext.properties.editor.model.PropertyEditorType;
import org.wirez.core.api.definition.DefaultDefinition;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.DefaultPropertySet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.definition.property.type.BooleanType;
import org.wirez.core.api.definition.property.type.ColorType;
import org.wirez.core.api.definition.property.type.IntegerType;
import org.wirez.core.api.definition.property.type.StringType;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.processing.handler.DefaultGraphHandler;
import org.wirez.core.api.util.PropertyUtils;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.DefaultCanvasListener;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.canvas.impl.BaseCanvasHandler;
import org.wirez.core.client.event.ShapeStateModifiedEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.*;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
public class PropertiesEditor implements IsWidget {

    public interface View extends UberView<PropertiesEditor> {
        
        void handle(PropertyEditorEvent propertyEditorEvent);
        
        void clear();
        
    }
    
    public interface EditorCallback {
        void onShowElement(Element<? extends ViewContent<?>> element);
    }

    ShapeManager wirezClientManager;
    DefaultCanvasCommands defaultCommands;
    DefaultGraphHandler defaultGraphHandler;
    View view;
    private CanvasHandler canvasHandler;
    private DefaultCanvasListener canvasListener;
    private EditorCallback editorCallback;

    @Inject
    public PropertiesEditor(final View view,
                            final ShapeManager wirezClientManager,
                            final DefaultCanvasCommands defaultCommands,
                            final DefaultGraphHandler defaultGraphHandler) {
        this.view = view;
        this.wirezClientManager = wirezClientManager;
        this.defaultCommands = defaultCommands;
        this.defaultGraphHandler = defaultGraphHandler;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }
    
    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public void show(final CanvasHandler canvasHandler) {
        if ( this.canvasHandler != null ) {
            removeCanvasListener();
        }
        this.canvasHandler = canvasHandler;
        addCanvasListener();
    }

    public void setEditorCallback(final EditorCallback editorCallback) {
        this.editorCallback = editorCallback;
    }
    
    interface PropertyValueChangedHandler {

        void onValueChanged(Object value);
    }
    
    @SuppressWarnings("unchecked")
    protected void show(final Element<? extends ViewContent<?>> element) {
        assert element != null;
        
        final String elementId = element.getUUID();
        final Definition definition = element.getContent().getDefinition();
        
        // This editor is for DefaultContent, not type safe properties.
        if (definition instanceof DefaultDefinition) {

            DefaultDefinition defaultDefinition = (DefaultDefinition) definition;
            
            final List<PropertyEditorCategory> categories = new ArrayList<PropertyEditorCategory>();
            final Set<String> processedProperties = new HashSet<String>();

            // Default element's category.
            final PropertyEditorCategory elementCategory = buildElementCategory(element);
            categories.add(elementCategory);

            // Definition property packages.
            final Set<PropertySet> propertyPackageSet = defaultDefinition.getPropertySets();
            if (propertyPackageSet != null) {

                for (final PropertySet propertyPackage : propertyPackageSet) {

                    final DefaultPropertySet defaultPropertySet = (DefaultPropertySet) propertyPackage;
                    final PropertyEditorCategory category = new PropertyEditorCategory(propertyPackage.getName());
                    final Collection<Property> properties = defaultPropertySet.getProperties();
                    if (properties != null) {
                        for (final Property property : properties) {
                            final String propertyId = property.getId();
                            final Object value = PropertyUtils.getValue(element.getProperties(), propertyId);
                            // GWT.log("PropertiesEditor  - Building field info with value [" + ( value != null ? value.toString() : null ) + "] for property [" + propertyId + "] in element with id [" + elementId + "].");
                            final PropertyEditorFieldInfo propFieldInfo = buildGenericFieldInfo(element, property, value, new PropertyValueChangedHandler() {
                                @Override
                                public void onValueChanged(final Object value) {
                                    GWT.log("PropertiesEditor  - Setting value [" + value.toString() + "] for property [" + propertyId + "] in element with id [" + elementId + "].");
                                    executeUpdateProperty(element, property, value);
                                }
                            });

                            if (propFieldInfo != null) {
                                processedProperties.add(propertyId);
                                category.withField(propFieldInfo);
                            }

                        }
                    }


                }

            }


            // Properties (custom) category.
            categories.add(buildPropertiesCategory(element, processedProperties));

            // Show the categories.
            view.handle(new PropertyEditorEvent("wirezPropertiesEditorEvent", categories));

            // Editor callback notifications.
            if ( null != editorCallback ) {
                editorCallback.onShowElement(element);
            }
            
        }
        
    }

    private PropertyEditorCategory buildPropertiesCategory(final Element<? extends ViewContent<?>> element,
                                                           final Set<String> processedPropertyIds) {
        final String title = element.getContent().getDefinition().getTitle();
        final PropertyEditorCategory result = new PropertyEditorCategory(title, 1);
        
        final Set<Property> propertySet =  ( (DefaultDefinition) element.getContent().getDefinition() ).getProperties();
        if (propertySet != null) {
            for (final Property property : propertySet) {
                final String propertyId = property.getId();
                final Object value = PropertyUtils.getValue(element.getProperties(), propertyId);
                if (!processedPropertyIds.contains(propertyId)) {
                    final PropertyEditorFieldInfo fieldInfo = buildGenericFieldInfo(element, property, value, new PropertyValueChangedHandler() {
                        @Override
                        public void onValueChanged(final Object value) {
                            GWT.log("PropertiesEditor  - Setting value [" + value.toString() + "] for property [" + propertyId + "] in element with id [" + element.getUUID() + "].");
                            executeUpdateProperty(element, property, value);
                        }
                    });

                    if (fieldInfo != null) {
                        result.withField(fieldInfo);
                    }
                }
            }
        }
        
        return result;
        
    }
    
    private PropertyEditorFieldInfo buildGenericFieldInfo(final Element<? extends ViewContent<?>> element,
                                                          final Property property,
                                                          final Object value,
                                                          final PropertyValueChangedHandler changedHandler) {
        PropertyEditorType type = null;
        if (property.getType() instanceof StringType) {
            type = PropertyEditorType.TEXT;
        } else if (property.getType() instanceof ColorType) {
            type = PropertyEditorType.COLOR;
        } else if (property.getType() instanceof IntegerType) {
            type = PropertyEditorType.NATURAL_NUMBER;
        } else if (property.getType() instanceof BooleanType) {
            type = PropertyEditorType.BOOLEAN;
        }

        if (type == null) {
            throw new RuntimeException("Unsupported property type for properties adaptor. [PropertyId=" + property.getId() + ", type=" + property.getType().getName() + "].");
        }

        final PropertyEditorType theType = type;
        return new PropertyEditorFieldInfo( property.getCaption() ,
                toEditorValue(theType, value),
                type) {

            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                super.setCurrentStringValue( currentStringValue );
                Object _v = fromEditorValue(theType, currentStringValue);
                changedHandler.onValueChanged(_v);
            }
        };
        
    }

    private String toEditorValue(final PropertyEditorType type, final Object value) {
        if ( null != value ) {
            
            switch (type) {
                case COLOR:
                    final String color = value.toString();
                    if (color.startsWith("#")) {
                        return color.substring(1, color.length());
                    } else {
                        return color;
                    }
                default:
                    return value.toString();
            }
            
        }
        return "";
    }

    private Object fromEditorValue(final PropertyEditorType type, final String value) {
        if ( null != value ) {

            switch (type) {
                case COLOR:
                    return "#" + value;
                case NATURAL_NUMBER:
                    return Integer.parseInt(value);
                case BOOLEAN:
                    return Boolean.parseBoolean(value);
                default:
                    return value;
            }

        }
        return value;
    }
    
    private PropertyEditorCategory buildElementCategory(final Element<? extends ViewContent<?>> element) {
        final PropertyEditorCategory result = new PropertyEditorCategory("Element", 0);

        final String id = element.getUUID();
        final double x = element.getContent().getBounds().getUpperLeft().getX();
        final double y = element.getContent().getBounds().getLowerRight().getY();

        final double w = getWidth(element);
        final double h = getHeight(element);

        // Element ID
        final PropertyEditorFieldInfo idField = new PropertyEditorFieldInfo( "Id",
                id,
                PropertyEditorType.TEXT) {

            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                // Cannot be modified.
                super.setCurrentStringValue( id );
            }
        };

        // Element X
        final PropertyEditorFieldInfo xField = new PropertyEditorFieldInfo( "X",
                Double.toString(x),
                PropertyEditorType.NATURAL_NUMBER) {
            
            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                super.setCurrentStringValue( currentStringValue );
                final Double x = Double.parseDouble(currentStringValue);
                final Double y = element.getContent().getBounds().getUpperLeft().getY();
                GWT.log("PropertiesEditor  tiesEditor - Setting value [" + x + "] for property [X] in element with id [" + id + "].");
                executeMove(element, x, y);
            }
        };

        // Element Y
        final PropertyEditorFieldInfo yField = new PropertyEditorFieldInfo( "Y",
                Double.toString(y),
                PropertyEditorType.NATURAL_NUMBER) {

            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                super.setCurrentStringValue( currentStringValue );
                final Double y = Double.parseDouble(currentStringValue);
                final Double x = element.getContent().getBounds().getUpperLeft().getX();
                GWT.log("PropertiesEditor  - Setting value [" + y + "] for property [Y] in element with id [" + id + "].");
                executeMove(element, x, y);
            }
        };

        return result.withField(idField).withField(xField).withField(yField);
    }
    
    private void executeUpdateProperty(final Element<? extends ViewContent<?>> element, 
                                       final Property property,
                                       final Object value) {
        ((BaseCanvasHandler) canvasHandler).execute(defaultCommands.UPDATE_PROPERTY(element, property.getId(), value));
    }

    private void executeMove(final Element<? extends ViewContent<?>> element,
                                       final double x,
                                       final double y) {
        ((BaseCanvasHandler) canvasHandler).execute(defaultCommands.MOVE(element, x, y));
    }

    private double getWidth(final Element<? extends ViewContent<?>> element) {
        final Bounds.Bound ul = element.getContent().getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getContent().getBounds().getLowerRight();
        return lr.getX() - ul.getX();
    }

    private double getHeight(final Element<? extends ViewContent<?>> element) {
        final Bounds.Bound ul = element.getContent().getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getContent().getBounds().getLowerRight();
        return lr.getY() - ul.getY();
    }
    
    public void clear() {
        removeCanvasListener();
        canvasHandler = null;
        view.clear();
    }

    void onCanvasShapeStateModifiedEvent(@Observes ShapeStateModifiedEvent event) {
        checkNotNull("event", event);
        final Canvas.ShapeState state = event.getState();
        final Shape shape = event.getShape();
        final DefaultGraph defaultGraph = (DefaultGraph) this.canvasHandler.getGraph();
        if ( shape != null ) {
            final String shapeUUID = shape.getId();
            final Element<? extends ViewContent<?>> element = defaultGraphHandler.initialize(defaultGraph).get(shapeUUID);
            if (element != null && Canvas.ShapeState.SELECTED.equals(state)) {
                GWT.log("PropertiesEditor  - Showing properties for node [" + element.getUUID() + "]");
                show(element);
            } else if (Canvas.ShapeState.DESELECTED.equals(state)) {
                view.clear();
            }
        }
        
    }
    
    private void addCanvasListener() {
        removeCanvasListener();

        canvasListener = new DefaultCanvasListener(canvasHandler) {
            @Override
            public void onElementAdded(final Element element) {

            }

            @Override
            public void onElementModified(final Element _element) {
                super.onElementModified(_element);
                show(_element);
            }

            @Override
            public void onElementDeleted(final Element element) {

            }

            @Override
            public void onClear() {
                view.clear();
                final DefaultGraph defaultGraph = (DefaultGraph) PropertiesEditor.this.canvasHandler.getGraph();
                if ( null != defaultGraph) {
                    show(defaultGraph);
                }
            }
        };
        canvasHandler.addListener(canvasListener);
    }

    private void removeCanvasListener() {
        if (canvasListener != null) {
            canvasListener.removeListener();
        }
    }
    
}
