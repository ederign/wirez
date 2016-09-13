package org.wirez.shapes.client.view.animatiion;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.shapes.client.BasicConnector;
import org.wirez.shapes.client.view.BasicConnectorView;

public final class BasicConnectorDecoratorAnimation extends BasicDecoratorAnimation<BasicConnector> {

    public BasicConnectorDecoratorAnimation( final String color,
                                             final double strokeWidth,
                                             final double strokeAlpha ) {
        super( color, strokeWidth, strokeAlpha );
    }

    @Override
    Shape getDecorator() {
        return getView().getLine();
    }

    private BasicConnectorView<?> getView() {
        return ( BasicConnectorView<?> ) getSource().getShapeView();
    }

}
