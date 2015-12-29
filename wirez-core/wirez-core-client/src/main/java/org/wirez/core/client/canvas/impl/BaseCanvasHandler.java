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

package org.wirez.core.client.canvas.impl;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.google.gwt.core.client.GWT;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.command.DefaultCommandManager;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.event.NotificationEvent;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.impl.DefaultEdge;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.impl.DefaultNode;
import org.wirez.core.api.rule.DefaultRuleManager;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.*;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.mutation.*;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;

// TODO: Implement SelectionManager<Element>
public abstract class BaseCanvasHandler implements CanvasHandler, CanvasCommandManager {
    
    protected Event<NotificationEvent> notificationEvent;
    protected DefaultCommandManager commandManager;
    protected DefaultRuleManager ruleManager;
    protected CanvasSettings settings;
    protected Canvas canvas;
    protected DefaultGraph<? extends Definition, DefaultNode, DefaultEdge> graph;
    protected Collection<CanvasListener> listeners = new LinkedList<CanvasListener>();

    @Inject
    public BaseCanvasHandler(final Event<NotificationEvent> notificationEvent, 
                             final DefaultCommandManager commandManager, 
                             final DefaultRuleManager ruleManager) {
        this.notificationEvent = notificationEvent;
        this.commandManager = commandManager;
        this.ruleManager = ruleManager;
    }

    @Override
    public CanvasHandler initialize(final CanvasSettings settings) {
        this.settings = settings;
        this.canvas = settings.getCanvas();
        this.graph = (DefaultGraph<? extends Definition, DefaultNode, DefaultEdge>) settings.getGraph();
        return this;
    }

    @Override
    public String getUUID() {
        return settings.getUUID();
    }

    @Override
    public Graph<? extends Definition, ? extends Node> getGraph() {
        return graph;
    }

    @Override
    public Canvas getCanvas() {
        return canvas;
    }
    
    /*
        ***************************************************************************************
        * Shape/element handling
        ***************************************************************************************
     */

    public void register(final ShapeFactory factory, final Element candidate) {
        assert factory != null && candidate != null;

        final Definition wirez = candidate.getDefinition();
        final Shape shape = factory.build(wirez, this);

        shape.setId(candidate.getUUID());

        
        if (shape instanceof HasMutation) {
            
            final HasMutation hasMutation = (HasMutation) shape;
            
            if (hasMutation.accepts(MutationType.STATIC)) {

                MutationContext context = new StaticMutationContext();
                
                if (shape instanceof HasGraphElementMutation) {
                    final HasGraphElementMutation hasGraphElement = (HasGraphElementMutation) shape;
                    hasGraphElement.applyElementPosition(candidate, this, context);
                    hasGraphElement.applyElementSize(candidate, this, context);
                    hasGraphElement.applyElementProperties(candidate, this, context);
                }
                
            }
            
        }
        
        // Selection handling.
        if (canvas instanceof SelectionManager) {
            final SelectionManager<Shape> selectionManager = (SelectionManager<Shape>) canvas;
            shape.getShapeNode().addNodeMouseClickHandler(new NodeMouseClickHandler() {
                @Override
                public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {

                    if (!nodeMouseClickEvent.isShiftKeyDown()) {
                        selectionManager.clearSelection();
                    }

                    final boolean isSelected = selectionManager.isSelected(shape);
                    if (isSelected) {
                        GWT.log("Deselect [shape=" + shape.getId() + "]");
                        selectionManager.deselect(shape);
                    } else {
                        GWT.log("Select [shape=" + shape.getId() + "]");
                        selectionManager.select(shape);
                    }

                }
            });

        }

        // TODO: Shape controls
        /*if (factory instanceof HasShapeControlFactories) {

            final Collection<ShapeControlFactory<?, ?>> factories = ((HasShapeControlFactories) factory).getFactories();
            for (ShapeControlFactory controlFactory : factories) {
                ShapeControl control = controlFactory.build(shape);

                // DRAG handling..
                if (control instanceof DefaultDragControl && shape instanceof HasDragControl) {
                    final HasDragControl hasDragControl = (HasDragControl) shape;
                    hasDragControl.setDragControl((DefaultDragControl) control);
                    ((DefaultDragControl) control).setCommandManager(this);
                    control.enable(shape, candidate);
                }

                // RESIZE handling.
                if (control instanceof DefaultResizeControl && shape instanceof HasResizeControl) {
                    final HasResizeControl hasResizeControl = (HasResizeControl) shape;
                    hasResizeControl.setResizeControl((DefaultResizeControl) control);
                    ((DefaultResizeControl) control).setCommandManager(this);
                    control.enable(shape, candidate);
                }
            }

        }*/

        // TODO: Contextual menu.
        /*if (canvas instanceof HasContextualMenu) {
            final ContextualMenu<Element> contextualMenu = ((HasContextualMenu) canvas).getContextualMenu();
            getContainer().addNodeMouseDoubleClickHandler(new NodeMouseDoubleClickHandler() {
                @Override
                public void onNodeMouseDoubleClick(NodeMouseDoubleClickEvent nodeMouseDoubleClickEvent) {
                    final double mx = nodeMouseDoubleClickEvent.getX();
                    final double my = nodeMouseDoubleClickEvent.getY();
                    GWT.log("Double click for " + candidate.getId() + " at [mx=" + mx + ", my=" + my + "]");
                    contextualMenu.show(candidate, null, mx, my);
                }
            });

        }*/

        // Add the shapes on canvas and fire events.
        canvas.addShape(shape);
        canvas.draw();
        fireElementAdded(candidate);
    }

    public void deregister(final Element element) {
        // TODO
    }
    
    /*
        ***************************************************************************************
        * Listeners handling
        ***************************************************************************************
     */

    @Override
    public CanvasHandler addListener(final CanvasListener listener) {
        assert listener != null;
        listeners.add(listener);
        return this;
    }

    public void fireElementAdded(final Element element) {
        for (final CanvasListener listener : listeners) {
            listener.onElementAdded(element);
        }
    }

    public void fireElementDeleted(final Element element) {
        for (final CanvasListener listener : listeners) {
            listener.onElementDeleted(element);
        }
    }

    public void fireElementUpdated(final Element element) {
        for (final CanvasListener listener : listeners) {
            listener.onElementModified(element);
        }
    }

    public void fireCanvasClear() {
        for (final CanvasListener listener : listeners) {
            listener.onClear();
        }
    }
    
    /*
        ***************************************************************************************
        * Command handling
        ***************************************************************************************
     */

    @Override
    public boolean allow(final CanvasCommand command) {
        return this.allow(ruleManager, command);
    }

    @Override
    public CommandResults execute(final CanvasCommand... commands) {
        return this.execute(ruleManager, commands);
    }

    @Override
    public CommandResults undo() {
        return this.undo(ruleManager);
    }

    @Override
    public boolean allow(final RuleManager ruleManager,
                         final CanvasCommand command) {
        command.setCanvas(this);
        return commandManager.allow(ruleManager, command);
    }

    @Override
    public CommandResults execute(final RuleManager ruleManager,
                                  final CanvasCommand... commands) {

        // TODO: Join results.
        CommandResults results = null;
        for (final CanvasCommand command : commands) {
            command.setCanvas(this);
            results = commandManager.execute(ruleManager, command);
            // TODO: Check errors.
            command.apply();
        }

        return results;
    }

    @Override
    public CommandResults undo(final RuleManager ruleManager) {
        return commandManager.undo(ruleManager);
    }

    protected BaseCanvas getBaseCanvas() {
        return (BaseCanvas) canvas;
    }
}
