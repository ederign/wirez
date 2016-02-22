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

package org.wirez.bpmn.client.factory;

import org.wirez.bpmn.api.Lane;
import org.wirez.bpmn.client.LaneShape;
import org.wirez.bpmn.client.factory.control.BPMNToolboxControlFactory;
import org.wirez.bpmn.client.glyph.LaneGlyph;
import org.wirez.client.views.ShapeViewFactory;
import org.wirez.client.views.WiresRectangleView;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.client.ShapeGlyph;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.control.ShapeGlyphDragHandler;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.factory.control.DefaultShapeControlFactories;
import org.wirez.core.client.factory.control.ShapeControlFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LaneShapeFactory extends BaseBPMNShapeFactory<Lane, LaneShape> {

    public LaneShapeFactory() {
    }
    
    @Inject
    public LaneShapeFactory(final ShapeViewFactory shapeViewFactory,
                            final DefaultShapeControlFactories defaultShapeControlFactories,
                            final ShapeGlyphDragHandler shapeGlyphDragHandler,
                            final BPMNToolboxControlFactory bpmnToolboxControlFactory) {
        super(shapeViewFactory, defaultShapeControlFactories, shapeGlyphDragHandler, bpmnToolboxControlFactory);
    }

    @Override
    public boolean accepts(final Definition definition) {
        return definition instanceof Lane;
    }

    @Override
    public ShapeGlyph getGlyph() {
        return LaneGlyph.INSTANCE;
    }

    @Override
    public String getDescription() {
        return "A lane";
    }

    @Override
    protected ShapeControlFactory<?, ?> getResizeControlFactory() {
        return defaultShapeControlFactories.lienzoResizeControlFactory();
    }

    @Override
    public LaneShape build(final Lane definition, final CanvasHandler canvasHandler) {
        final WiresCanvas wiresCanvas = (WiresCanvas) canvasHandler.getCanvas();
        final WiresRectangleView view = shapeViewFactory.rectangle( Lane.WIDTH, Lane.HEIGHT, wiresCanvas.getWiresManager());
        return new LaneShape(view);
    }

}
