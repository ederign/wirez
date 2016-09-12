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

import org.wirez.core.client.animation.Animation;
import org.wirez.core.client.shape.AbstractConnector;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.ShapeState;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.ViewConnector;
import org.wirez.shapes.client.view.BasicConnectorView;
import org.wirez.shapes.client.view.animatiion.BasicConnectorAnimation;

public abstract class BasicConnector<W, V extends BasicConnectorView>
        extends AbstractConnector<W, Edge<ViewConnector<W>, Node>, V> {

    private BasicConnectorAnimation animation = null;
    private Double _strokeWidth = null;
    private String _strokeColor = null;

    public BasicConnector( final V view) {
        super( view );
    }

    protected abstract String getBackgroundColor( Edge<ViewConnector<W>, Node> element );
    protected abstract Double getBackgroundAlpha( Edge<ViewConnector<W>, Node> element );
    protected abstract String getBorderColor( Edge<ViewConnector<W>, Node> element );
    protected abstract Double getBorderSize( Edge<ViewConnector<W>, Node> element );
    protected abstract Double getBorderAlpha( Edge<ViewConnector<W>, Node> element );

    @Override
    public void applyProperties(final Edge<ViewConnector<W>, Node> element,
                                final MutationContext mutationContext) {
        super.applyProperties( element, mutationContext );

        // Fill color.
        _applyFillColor( element, mutationContext );

        // Fill alpha.
        _applyFillApha( element, mutationContext );

        // Apply border styles.
        _applyBorders( element, mutationContext );

        // Apply border alpha.
        _applyBorderApha( element, mutationContext );


    }

    @Override
    public void beforeDraw() {
        super.beforeDraw();

        if ( hasAnimation() ) {

            getAnimation().setCallback( new Animation.AnimationCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onFrame() {

                }

                @Override
                public void onComplete() {
                    BasicConnector.this.animation = null;
                }
            } );

            getAnimation().run();

        }

    }

    @Override
    public void applyState( final ShapeState shapeState ) {

        if ( ShapeState.SELECTED.equals(shapeState) ) {
            applySelectedState();
        } else if ( ShapeState.HIGHLIGHT.equals(shapeState) ) {
            applyHighlightState();
        } else if ( ShapeState.DESELECTED.equals(shapeState) ) {
            applyUnSelectedState();
        } else if ( ShapeState.UNHIGHLIGHT.equals(shapeState) ) {
            applyUnHighlightState();
        } else if ( ShapeState.INVALID.equals(shapeState) ) {
            applyInvalidState();
        }

    }

    private void applySelectedState() {
        applyActiveState(ShapeState.SELECTED.getColor());
    }

    private void applyInvalidState() {
        applyActiveState(ShapeState.INVALID.getColor());
    }

    private void applyHighlightState() {
        applyActiveState(ShapeState.HIGHLIGHT.getColor());
    }

    private void applyUnSelectedState() {
        applyDeActiveState();
    }

    private void applyUnHighlightState() {
        applyDeActiveState();
    }

    // TODO Animations...?
    private void applyActiveState(final String color ) {
        if ( null == this._strokeWidth ) {
            this._strokeWidth = getShapeView().getLine().getStrokeWidth();
        }

        if ( null == this._strokeColor) {
            this._strokeColor = getShapeView().getLine().getStrokeColor();
        }

        getShapeView().getLine().setStrokeWidth( 5 );
        getShapeView().getLine().setStrokeColor( color );

    }

    // TODO Animations...?
    private void applyDeActiveState() {
        if ( null != this._strokeWidth ) {
            getShapeView().getLine().setStrokeWidth( this._strokeWidth );
            this._strokeWidth = null;
        }

        if ( null != this._strokeColor ) {
            getShapeView().getLine().setStrokeColor( this._strokeColor );
            this._strokeColor = null;
        }

    }

    protected BasicConnector<W, V> _applyFillColor( final Edge<ViewConnector<W>, Node> element,
                                                    final MutationContext mutationContext ) {
        final String color = getBackgroundColor( element );

        if (color != null && color.trim().length() > 0) {

            if ( isAnimationMutation(mutationContext) ) {

                getAnimation().animateFillColor( color );

            } else {

                super._applyFillColor( color, mutationContext );

            }

        }

        return this;
    }

    protected BasicConnector<W, V> _applyFillApha( final Edge<ViewConnector<W>, Node> element,
                                                   final MutationContext mutationContext ) {
        final Double alpha = getBackgroundAlpha( element );
        super._applyFillAlpha( alpha, mutationContext );
        return this;
    }

    protected BasicConnector<W, V> _applyBorders( final Edge<ViewConnector<W>, Node> element,
                                                  final MutationContext mutationContext ) {
        final String color = getBorderColor( element );
        final Double width = getBorderSize( element );
        super._applyBorders( color, width, mutationContext );
        return this;
    }

    @Override
    protected void _applyBorderColor( final String color,
                                      final MutationContext mutationContext ) {

        final boolean isAnimation = isAnimationMutation( mutationContext );

        if ( isAnimation ) {

            getAnimation().animateStrokeColor( color );

        } else {

            super._applyBorderColor( color, mutationContext );

        }

    }

    @Override
    protected void _applyBorderWidth( final double width,
                                      final MutationContext mutationContext ) {

        final boolean isAnimation = isAnimationMutation( mutationContext );

        if ( isAnimation ) {

            getAnimation().animateStrokeWidth( width );

        } else {

            super._applyBorderWidth( width, mutationContext );

        }

    }

    protected BasicConnector<W, V> _applyBorderApha( final Edge<ViewConnector<W>, Node> element,
                                                     final MutationContext mutationContext ) {
        final Double alpha = getBorderAlpha( element );
        super._applyBorderAlpha( alpha, mutationContext );
        return this;
    }

    private boolean hasAnimation() {
        return null != animation;
    }

    private BasicConnectorAnimation getAnimation() {

        if ( !hasAnimation() ) {

            return this.animation = new BasicConnectorAnimation();
        }

        return animation;
    }

}
