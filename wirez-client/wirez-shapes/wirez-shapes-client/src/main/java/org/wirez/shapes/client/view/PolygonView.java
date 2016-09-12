package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.RegularPolygon;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.core.client.shape.view.HasRadius;

public class PolygonView<T extends PolygonView> extends BasicPrimitiveShapeView<T>
        implements HasRadius<T> {

    protected RegularPolygon polygon;

    public PolygonView(final double radius,
                       final String fillColor,
                       final WiresManager manager) {
        super(new MultiPath()
                        .M(0 ,radius)
                        .L(radius, 0)
                        .L(radius * 2, radius)
                        .L(radius, ( radius * 2) )
                        .Z(),
                manager);
    }

    @Override
    protected Shape<?> createChildren() {
        polygon = new RegularPolygon(4, 1);
        this.addChild(polygon, WiresLayoutContainer.Layout.CENTER);
        final RegularPolygon decorator = new RegularPolygon(4, 1);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
        return decorator;
    }

    @Override
    protected Shape getPrimitive() {
        return polygon;
    }

    @Override
    public T setRadius(final double radius) {
        return super.setRadius( radius );
    }

    @Override
    protected void doDestroy() {
        super.doDestroy();

        polygon = null;
    }
    
}
