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

package org.wirez.shapes.client;

import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

public abstract class BasicNamedShape<W, V extends org.wirez.shapes.client.view.BasicShapeView>
    extends BasicDynShape<W, V> {

    public BasicNamedShape( final V shapeView) {
        super(shapeView);
    }

    @Override
    public void applyProperties(Node<View<W>, Edge> element, final MutationContext mutationContext) {
        super.applyProperties(element, mutationContext);

        // Apply font styles.
        _applyFont(element, mutationContext);
    }
    
    protected abstract String getFontFamily( Node<View<W>, Edge> element );
    protected abstract String getFontColor( Node<View<W>, Edge> element );
    protected abstract Double getFontSize( Node<View<W>, Edge> element );
    protected abstract Double getFontBorderSize( Node<View<W>, Edge> element );

    protected Double getFontAlpha( Node<View<W>, Edge> element ) {
        return 1d;
    }

    protected BasicNamedShape<W, V> _applyFont( final Node<View<W>, Edge> element, final MutationContext mutationContext) {
        
        final String family = getFontFamily( element );
        final String color = getFontColor( element );
        final Double size = getFontSize( element );
        final Double borderSize = getFontBorderSize( element );
        final Double alpha = getFontAlpha( element );

        super._applyFont(family, color, size, borderSize, alpha, mutationContext);

        return this;
    }

}
