package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Ring;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.core.client.shape.view.HasRadius;

public class RingView<T extends RingView> extends org.wirez.shapes.client.view.BasicPrimitiveShapeView<T>
    implements HasRadius<T> {

    protected Ring ring;

    public RingView(final double outer,
                    final WiresManager manager) {
        super(new MultiPath().rect(0,0, outer * 2, outer * 2), manager);
    }

    @Override
    protected Shape getPrimitive() {
        return ring;
    }

    @Override
    protected Shape<?> createChildren() {
        
        ring = new Ring( 1, 1 );
        this.addChild( ring, WiresLayoutContainer.Layout.CENTER );
        
        final Ring decorator = new Ring( 1, 1 );
        this.addChild( decorator, WiresLayoutContainer.Layout.CENTER );
        
        return decorator;
    }

    @Override
    public T setRadius( final double radius ) {
        final double o = radius;
        final double i = radius - ( radius / 4 );
        setOuterRadius( o );
        setInnerRadius( i );
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setOuterRadius(final double radius) {

        final double size = radius * 2;
        
        updatePath( size, size );
        
        getShape().getAttributes().setOuterRadius( radius );
        decorator.getAttributes().setOuterRadius( radius );

        super.updateFillGradient( size, size );
        
        return (T) this;
        
    }

    @SuppressWarnings("unchecked")
    public T setInnerRadius(final double radius) {

        getShape().getAttributes().setInnerRadius( radius );
        decorator.getAttributes().setInnerRadius( radius );
        
        return (T) this;

    }

    @Override
    protected void doDestroy() {
        super.doDestroy();
        
        ring = null;
    }
    
}
