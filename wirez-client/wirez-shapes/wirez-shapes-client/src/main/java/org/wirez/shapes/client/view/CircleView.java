package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.core.client.shape.view.HasRadius;

public class CircleView<T extends CircleView> extends org.wirez.shapes.client.view.BasicPrimitiveShapeView<T>
        implements HasRadius<T> {

    protected Circle circle;

    public CircleView(final double radius,
                      final WiresManager manager) {
        super(new MultiPath().rect(0,0, radius * 2, radius * 2), manager);
    }

    @Override
    protected Shape getPrimitive() {
        return circle;
    }

    @Override
    protected Shape<?> createChildren() {
        circle = new Circle(1);
        this.addChild(circle, WiresLayoutContainer.Layout.CENTER);
        final Circle decorator = new Circle(1);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
        return decorator;
    }

    @Override
    public T setRadius(final double radius) {
        return super.setRadius( radius );
    }

    @Override
    protected void doDestroy() {
        super.doDestroy();
        
        circle = null;
    }
    
}
