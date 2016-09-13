package org.wirez.shapes.client.view.animatiion;

import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import org.wirez.core.client.shape.Shape;

import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.STROKE_ALPHA;

// TODO: highlight state.
abstract class BasicDecoratorAnimation<S extends Shape> extends AbstractBasicAnimation<S> {

    private final String color;
    private final double strokeWidth;
    private final double strokeAlpha;

    public BasicDecoratorAnimation( final String color,
                                    final double strokeWidth,
                                    final double strokeAlpha ) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.strokeAlpha = strokeAlpha;
    }

    abstract com.ait.lienzo.client.core.shape.Shape getDecorator();

    @Override
    public void run() {

        getDecorator().setStrokeColor( color );
        getDecorator().setStrokeWidth( strokeWidth );
        getDecorator().setStrokeAlpha( 0 );

        getDecorator().animate(
                AnimationTweener.LINEAR,
                AnimationProperties.toPropertyList( STROKE_ALPHA( strokeAlpha ) ),
                getDuration(),
                getAnimationCallback() );

    }


}
