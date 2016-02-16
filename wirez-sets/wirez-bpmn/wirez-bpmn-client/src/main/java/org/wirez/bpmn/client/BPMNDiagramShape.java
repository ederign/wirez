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

package org.wirez.bpmn.client;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.LinearGradient;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.general.*;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.HasDecorators;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.impl.BaseShape;
import org.wirez.core.client.mutation.HasGraphElementMutation;
import org.wirez.core.client.mutation.HasSizeMutation;
import org.wirez.core.client.mutation.MutationContext;
import org.wirez.core.client.mutation.MutationType;
import org.wirez.core.client.util.ShapeUtils;

import java.util.ArrayList;
import java.util.Collection;

public class BPMNDiagramShape extends BPMNBasicShape<BPMNDiagram> implements HasSizeMutation {


    public BPMNDiagramShape(final WiresManager manager) {
        super(new MultiPath().rect(0, 0, BPMNDiagram.WIDTH, BPMNDiagram.HEIGHT), manager);
        getPath().setFillAlpha(0.1);
    }

    @Override
    protected WiresLayoutContainer.Layout getTextPosition() {
        return WiresLayoutContainer.Layout.TOP;
    }

    @Override
    public Collection<Shape> getDecorators() {
        return new ArrayList<Shape>(1) {{
            add( getPath() );
        }};
    }

    @Override
    public void applyElementProperties(Node<ViewContent<BPMNDiagram>, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);

        // Size.
        _applySize(element, mutationContext);

    }

    @Override
    public void afterMutations(final Canvas canvas) {
        super.afterMutations(canvas);
        getPath().setFillAlpha(0.1);
    }

    protected BPMNDiagramShape _applySize(final Node<ViewContent<BPMNDiagram>, Edge> element, MutationContext mutationContext) {
        final Width widthProperty  = (Width) ElementUtils.getProperty(element, Width.ID);
        final Height heightProperty  = (Height) ElementUtils.getProperty(element, Height.ID);
        final Double width = widthProperty.getValue();
        final Double height = heightProperty.getValue();
        applySize(width, height, mutationContext);
        ElementUtils.updateBounds(width, height, element.getContent());
        return this;
    }

    @Override
    public void applySize(final double width, final double height, final MutationContext mutationContext) {
        final double x = getPath().getX();
        final double y = getPath().getY();
        getPath().clear().rect(x, y, width, height);
    }

    @Override
    public String toString() {
        return "BPMNDiagramShape{}";
    }

    
}