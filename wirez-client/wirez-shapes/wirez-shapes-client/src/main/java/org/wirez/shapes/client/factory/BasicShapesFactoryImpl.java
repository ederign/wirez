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

package org.wirez.shapes.client.factory;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.client.lienzo.canvas.wires.WiresCanvas;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.shape.AbstractCompositeShape;
import org.wirez.core.client.shape.AbstractShape;
import org.wirez.core.client.shape.HasChildren;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.factory.AbstractProxyShapeFactory;
import org.wirez.core.client.shape.view.AbstractBindableShapeGlyphBuilder;
import org.wirez.core.client.shape.view.ShapeGlyph;
import org.wirez.core.client.shape.view.ShapeGlyphBuilder;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.shapes.client.proxy.DynamicIconShape;
import org.wirez.shapes.client.proxy.RectangleShape;
import org.wirez.shapes.client.proxy.StaticIconShape;
import org.wirez.shapes.client.view.ConnectorView;
import org.wirez.shapes.client.view.PolygonView;
import org.wirez.shapes.client.view.RectangleView;
import org.wirez.shapes.client.view.ShapeViewFactory;
import org.wirez.shapes.client.view.glyph.ConnectorGlyph;
import org.wirez.shapes.client.view.icon.dynamics.DynamicIconShapeView;
import org.wirez.shapes.client.view.icon.statics.StaticIconShapeView;
import org.wirez.shapes.factory.BasicShapesFactory;
import org.wirez.shapes.proxy.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

@ApplicationScoped
public class BasicShapesFactoryImpl 
        extends AbstractProxyShapeFactory<Object, ShapeView, MutableShape<Object, ShapeView>, BasicShapeProxy<Object>>
        implements BasicShapesFactory<Object, AbstractCanvasHandler> {

    protected static final double DEFAULT_SIZE = 50;

    protected ShapeViewFactory shapeViewFactory;
    protected DefinitionManager definitionManager;
    protected ShapeGlyphBuilder<Group> glyphBuilder;
    
    protected BasicShapesFactoryImpl() {
    }
    
    @Inject
    public BasicShapesFactoryImpl(final FactoryManager factoryManager,
                                  final org.wirez.shapes.client.view.ShapeViewFactory shapeViewFactory,
                                  final DefinitionManager definitionManager,
                                  final ShapeGlyphBuilder<Group> glyphBuilder) {
        super( factoryManager );
        this.shapeViewFactory = shapeViewFactory;
        this.definitionManager = definitionManager;
        this.glyphBuilder = glyphBuilder;
        
    }

    @Override
    @SuppressWarnings("unchecked")
    public MutableShape<Object, ShapeView> build( final Object definition, 
                     final AbstractCanvasHandler context) {

        final String id = definitionManager.adapters().forDefinition().getId( definition );
        final BasicShapeProxy<Object> proxy = getProxy( id );

        return build( definition, proxy, context );
    }

    @SuppressWarnings("unchecked")
    protected MutableShape<Object, ShapeView> build( final Object definition,
                                                final BasicShapeProxy<Object> proxy,
                                                final AbstractCanvasHandler context) {

        WiresManager wiresManager = context != null ?
                ((WiresCanvas) context.getCanvas()).getWiresManager() : null;
        
        MutableShape<Object, ShapeView> shape = null;
        
        if ( isCircle( proxy ) ) {

            final CircleProxy<Object> circleProxy = (CircleProxy<Object>) proxy;

            final double radius = circleProxy.getRadius( definition );

            final org.wirez.shapes.client.view.CircleView view =
                    shapeViewFactory.circle( radius, wiresManager );

            shape = new org.wirez.shapes.client.proxy.CircleShape( view, circleProxy );

        }

        if ( isRing( proxy ) ) {

            final RingProxy<Object> ringProxy = (RingProxy<Object>) proxy;

            final double oRadius = ringProxy.getOuterRadius( definition );

            final org.wirez.shapes.client.view.RingView view =
                    shapeViewFactory.ring( oRadius, wiresManager );

            shape = new org.wirez.shapes.client.proxy.RingShape( view, ringProxy );

        }

        if ( isRectangle( proxy ) ) {

            final RectangleProxy<Object> rectangleProxy = (RectangleProxy<Object>) proxy;

            final double width = rectangleProxy.getWidth( definition );
            final double height = rectangleProxy.getHeight( definition );

            final RectangleView view =
                    shapeViewFactory.rectangle( width, height, wiresManager );

            shape = new RectangleShape( view, rectangleProxy );

        }

        if ( isPolygon( proxy ) ) {

            final PolygonProxy<Object> polygonProxy = (PolygonProxy<Object>) proxy;

            final double radius = polygonProxy.getRadius( definition );
            final String fillColor = polygonProxy.getBackgroundColor( definition );

            final PolygonView view =
                    shapeViewFactory.polygon( radius,
                            fillColor,
                            wiresManager );

            shape = new org.wirez.shapes.client.proxy.PolygonShape( view, polygonProxy );

        }

        if ( isConnector( proxy ) ) {

            final ConnectorProxy<Object> polygonProxy = (ConnectorProxy<Object>) proxy;

            final ConnectorView view = shapeViewFactory.connector( wiresManager, 0, 0, 100, 100 );

            shape = new org.wirez.shapes.client.proxy.ConnectorShape( view, polygonProxy );

        }

        if ( isStaticIcon( proxy ) ) {

            final org.wirez.shapes.proxy.icon.statics.IconProxy<Object> iconProxy = 
                    (org.wirez.shapes.proxy.icon.statics.IconProxy<Object>) proxy;

            final org.wirez.shapes.proxy.icon.statics.Icons icon = iconProxy.getIcon( definition );

            final StaticIconShapeView view =
                    shapeViewFactory.staticIcon( icon, wiresManager );

            shape = new StaticIconShape( view, iconProxy );

        }

        if ( isDynamicIcon( proxy ) ) {

            final org.wirez.shapes.proxy.icon.dynamics.IconProxy<Object> iconProxy =
                    (org.wirez.shapes.proxy.icon.dynamics.IconProxy<Object>) proxy;

            final org.wirez.shapes.proxy.icon.dynamics.Icons icon = DynamicIconShape.getIcon( definition, iconProxy );
            final double width = iconProxy.getWidth( definition );
            final double height = iconProxy.getHeight( definition );

            final DynamicIconShapeView view =
                    shapeViewFactory.dynamicIcon( icon, width, height, wiresManager );

            shape = new DynamicIconShape( view, iconProxy );

        }
        
        // Add children, if any.
        if ( null != shape && proxy instanceof HasChildProxies) {

            final HasChildProxies<Object> hasChildren = (HasChildProxies<Object>) proxy;
            final Map<BasicShapeProxy<Object>, HasChildren.Layout> childProxies = hasChildren.getChildProxies();
            if ( null != childProxies && !childProxies.isEmpty() ) {
                for ( final Map.Entry<BasicShapeProxy<Object>, HasChildren.Layout> entry : childProxies.entrySet() ) {

                    final BasicShapeProxy<Object> child = entry.getKey();
                    final HasChildren.Layout layout = entry.getValue();

                    final MutableShape<Object, ShapeView> childShape = this.build( definition, child, context);

                    if ( childShape instanceof AbstractCompositeShape ) {

                        ( (AbstractCompositeShape) shape).addChild( (AbstractShape) childShape, layout );

                    }
                    
                }
            }

        }

        if ( null != shape ) {
        
            return shape;
            
        }
        
        final String id = definitionManager.adapters().forDefinition().getId( definition );
        throw new RuntimeException( "This factory supports [" + id + "] but cannot built a shape for it." );

    }
    
    private boolean isCircle( final BasicShapeProxy<Object> proxy ) {
        return proxy instanceof CircleProxy;
    }

    private boolean isRing( final BasicShapeProxy<Object> proxy ) {
        return proxy instanceof RingProxy;
    }

    private boolean isRectangle( final BasicShapeProxy<Object> proxy ) {
        return proxy instanceof RectangleProxy;
    }

    private boolean isPolygon( final BasicShapeProxy<Object> proxy ) {
        return proxy instanceof PolygonProxy;
    }

    private boolean isConnector( final BasicShapeProxy<Object> proxy ) {
        return proxy instanceof ConnectorProxy;
    }

    private boolean isDynamicIcon(final BasicShapeProxy<Object> proxy ) {
        return proxy instanceof org.wirez.shapes.proxy.icon.dynamics.IconProxy;
    }

    private boolean isStaticIcon(final BasicShapeProxy<Object> proxy ) {
        return proxy instanceof org.wirez.shapes.proxy.icon.statics.IconProxy;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected ShapeGlyph glyph( final Class<?> clazz, 
                                final double width, 
                                final double height) {

        final String id = getDefinitionId( clazz );
        final BasicShapeProxy<Object> proxy = getProxy( id );

        // Custom shape glyphs.

        if ( null != proxy && isConnector( proxy ) ) {

            return new ConnectorGlyph( width, height, ColorName.BLACK.getColorString() );

        }

        // Use of Shape Glyph Builder - it builds the glyph by building the shape for the given Definition
        // and scaling it to the given size.

        if ( null != proxy ) {

            if ( glyphBuilder instanceof AbstractBindableShapeGlyphBuilder ) {

                final AbstractBindableShapeGlyphBuilder bindableShapeGlyphBuilder =
                        (AbstractBindableShapeGlyphBuilder) glyphBuilder;

                bindableShapeGlyphBuilder.glyphProxy( proxy, clazz );

            } else {

                glyphBuilder.glyphProxy( proxy, id );

            }

        } else {

            glyphBuilder.definition( id );

        }


        return glyphBuilder
                .factory( this )
                .height( height )
                .width( width )
                .build();
    }
    
}
