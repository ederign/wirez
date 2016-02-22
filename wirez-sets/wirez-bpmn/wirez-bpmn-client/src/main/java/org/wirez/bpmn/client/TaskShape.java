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

import com.ait.lienzo.client.core.types.Shadow;
import com.ait.lienzo.shared.core.types.Color;
import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Width;
import org.wirez.client.views.WiresRectangleView;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.HasSizeMutation;
import org.wirez.core.client.mutation.MutationContext;

public class TaskShape extends BPMNBasicShape<Task> implements HasSizeMutation {

    private static final Shadow SHADOW = new Shadow(Color.fromColorString(Task.COLOR).setA(0.80), 10, -10, -10);

    public TaskShape(final WiresRectangleView view) {
        super(view);
    }

    protected WiresRectangleView getView() {
        return (WiresRectangleView) view;
    }
    
    @Override
    public void applyElementProperties(Node<View<Task>, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);
        
        // Size.
        _applySize(element, mutationContext);
        
    }

    protected TaskShape _applySize(final Node<View<Task>, Edge> element, MutationContext mutationContext) {
        final Width widthProperty  = (Width) ElementUtils.getProperty(element, Width.ID);
        final Height heightProperty  = (Height) ElementUtils.getProperty(element, Height.ID);
        final Double width = widthProperty.getValue();
        final Double height = heightProperty.getValue();
        applySize(width, height, mutationContext);
        ElementUtils.updateBounds(width, height, element.getContent());
        return this;
    }

    @Override
    public String toString() {
        return "TaskShape{}";
    }

    @Override
    public void applySize(final double width, final double height, final MutationContext mutationContext) {
        getView().setSize(width, height);
    }

}
