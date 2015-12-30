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

package org.wirez.core.client.animation;

import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.IColor;
import org.wirez.core.client.Shape;

import java.util.Collection;

import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.STROKE_ALPHA;

public class ShapeSelectionAnimation extends BaseShapeAnimation {

    private double strokeWidth = 4;
    private IColor color = ColorName.RED;
    private AnimationTweener animationTweener = AnimationTweener.LINEAR;
    
    public ShapeSelectionAnimation(final Shape shape) {
        super(shape);
    }

    public ShapeSelectionAnimation setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public ShapeSelectionAnimation setColor(IColor color) {
        this.color = color;
        return this;
    }

    public ShapeSelectionAnimation setAnimationTweener(AnimationTweener animationTweener) {
        this.animationTweener = animationTweener;
        return this;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public IColor getColor() {
        return color;
    }

    public AnimationTweener getAnimationTweener() {
        return animationTweener;
    }

    @Override
    public void run() {
        final Collection<com.ait.lienzo.client.core.shape.Shape> decorators = getDecorators();
        if ( null != decorators && !decorators.isEmpty() ) {
            for( final com.ait.lienzo.client.core.shape.Shape decorator : decorators ) {
                decorator.setStrokeWidth(strokeWidth).setStrokeColor(color).setStrokeAlpha(0);
                decorator.moveToTop();
                decorator.animate(animationTweener, AnimationProperties.toPropertyList(STROKE_ALPHA(1)), 
                        getDuration(), animationCallback);
            }
        }
    }

}
