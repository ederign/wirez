package org.wirez.shapes.client.view.animatiion;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.shapes.client.BasicShape;
import org.wirez.shapes.client.view.BasicPrimitiveShapeView;

public final class BasicShapeDecoratorAnimation extends BasicDecoratorAnimation<BasicShape> {

    public BasicShapeDecoratorAnimation( final String color,
                                         final double strokeWidth,
                                         final double strokeAlpha ) {
        super( color, strokeWidth, strokeAlpha );
    }

    @Override
    Shape getDecorator() {
        return getView().getDecorator();
    }

    private BasicPrimitiveShapeView<?> getView() {
        return ( BasicPrimitiveShapeView<?> ) getSource().getShapeView();
    }

}
