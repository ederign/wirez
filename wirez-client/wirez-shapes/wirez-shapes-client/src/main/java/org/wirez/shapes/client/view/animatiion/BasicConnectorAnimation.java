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

package org.wirez.shapes.client.view.animatiion;

import com.ait.lienzo.client.core.animation.*;
import com.ait.lienzo.client.core.shape.Group;
import org.wirez.core.client.shape.animation.AbstractShapeAnimation;
import org.wirez.shapes.client.BasicConnector;
import org.wirez.shapes.client.view.BasicConnectorView;

import java.util.LinkedList;
import java.util.List;

public class BasicConnectorAnimation<S extends BasicConnector>
        extends AbstractShapeAnimation<S> {

    private final List<AnimationProperty> decoratableLineAnimationProperties = new LinkedList<>();

    @Override
    public void run() {

        clear();

        final AnimationTweener tweener = AnimationTweener.LINEAR;
        final BasicConnectorView<?> view = ( BasicConnectorView<?> ) getSource().getShapeView();
        final Group group = view.getGroup();
        final long duration = getDuration();

        // Shape property animations.
        final AnimationProperties _ps = translate( decoratableLineAnimationProperties );
        group.animate( tweener, _ps, duration, animationCallback );
        decoratableLineAnimationProperties.clear();

    }

    private AnimationProperties translate( final List<AnimationProperty> ps ) {
        final AnimationProperties _ps = new AnimationProperties();
        for ( final com.ait.lienzo.client.core.animation.AnimationProperty p : ps ) {
            _ps.push( p );
        }
        return _ps;
    }

    public void clear() {
        decoratableLineAnimationProperties.clear();
    }

    public void animateFillColor( final String value ) {
        decoratableLineAnimationProperties.add( AnimationProperty.Properties.FILL_COLOR( value ) );
    }

    public void animateStrokeColor( final String value ) {
        decoratableLineAnimationProperties.add( AnimationProperty.Properties.STROKE_COLOR( value ) );
    }

    public void animateStrokeWidth( final Double value ) {
        decoratableLineAnimationProperties.add( AnimationProperty.Properties.STROKE_WIDTH( value ) );
    }

    private final com.ait.lienzo.client.core.animation.AnimationCallback animationCallback =
            new com.ait.lienzo.client.core.animation.AnimationCallback() {

        @Override
        public void onStart( IAnimation animation, IAnimationHandle handle ) {
            super.onStart( animation, handle );
            if ( null != getCallback() ) {
                getCallback().onStart();
            }
        }

        @Override
        public void onFrame( IAnimation animation, IAnimationHandle handle ) {
            super.onFrame( animation, handle );
            if ( null != getCallback() ) {
                getCallback().onFrame();
            }
        }

        @Override
        public void onClose( IAnimation animation, IAnimationHandle handle ) {
            super.onClose( animation, handle );
            if ( null != getCallback() ) {
                getCallback().onComplete();
            }
        }
    };

}
