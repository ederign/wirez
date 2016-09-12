package org.wirez.shapes.client.view.animatiion;

import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import org.wirez.core.client.shape.ShapeState;
import org.wirez.core.client.shape.animation.AbstractShapeAnimation;
import org.wirez.shapes.client.BasicShape;
import org.wirez.shapes.client.view.BasicPrimitiveShapeView;

import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.STROKE_ALPHA;

// TODO: Connectors & highlight state.
final class BasicShapeStateAnimation extends AbstractShapeAnimation<BasicShape> {

    private final ShapeState state;
    private final double strokeWidth;

    BasicShapeStateAnimation( final ShapeState state,
                              final double strokeWidth ) {
        this.state = state;
        this.strokeWidth = strokeWidth;
    }

    @Override
    public void run() {

        final BasicPrimitiveShapeView<?> view = getView();

        view.setStrokeColor( state.getColor() );
        view.setStrokeWidth( strokeWidth );
        view.setStrokeAlpha( 0 );

        view.getShape().animate(
                AnimationTweener.LINEAR,
                AnimationProperties.toPropertyList( STROKE_ALPHA( 1 ) ),
                getDuration(),
                animationCallback );

    }

    private BasicPrimitiveShapeView<?> getView() {
        return ( BasicPrimitiveShapeView<?> ) getSource().getShapeView();
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
