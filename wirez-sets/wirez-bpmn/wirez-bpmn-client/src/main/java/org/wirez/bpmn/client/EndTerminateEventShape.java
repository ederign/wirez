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

import org.wirez.bpmn.api.EndTerminateEvent;
import org.wirez.bpmn.api.property.Radius;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.client.shapes.WiresCircleView;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.Context;
import org.wirez.core.client.mutation.GraphContext;
import org.wirez.core.client.view.HasTitle;

public class EndTerminateEventShape extends BPMNBasicShape<EndTerminateEvent> {

    public EndTerminateEventShape(final WiresCircleView view) {
        super(view);
        getView().setPosition(HasTitle.Position.BOTTOM);
    }

    protected WiresCircleView getView() {
        return (WiresCircleView) view;
    }
    
    @Override
    public void applyElementProperties(Node<View<EndTerminateEvent>, Edge> element, CanvasHandler wirezCanvas, GraphContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);

        // Radius.
        _applyRadius(element, mutationContext);
    }

    protected void applyRadius(double radius, Context mutationContext) {
        if (radius > 0) {
            getView().setRadius(radius);
        }
    }

    protected EndTerminateEventShape _applyRadius(final Node<View<EndTerminateEvent>, Edge> element, GraphContext mutationContext) {
        final Radius radiusProperty  = element.getContent().getDefinition().getRadius();
        final Double radius = radiusProperty.getValue();
        if ( null != radius ) {
            applyRadius(radius, mutationContext);
            GraphUtils.updateBounds(radius, element.getContent());
        }
        return this;
    }

    public static Double[] getRingRadius(final double radius) {
        final double r = radius / 8;
        final double inner = r * 6;
        final double outer = inner + r;

        return new Double[] { inner, outer};
    }

    @Override
    protected BackgroundSet getBackgroundSet(final Node<View<EndTerminateEvent>, Edge> element) {
        return element.getContent().getDefinition().getBackgroundSet();
    }

    @Override
    protected FontSet getFontSet(final Node<View<EndTerminateEvent>, Edge> element) {
        return element.getContent().getDefinition().getFontSet();
    }
    
    @Override
    public String toString() {
        return "EndTerminateEventShape{}";
    }
    
}
