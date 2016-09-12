package org.wirez.shapes.client.view.icon.statics;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.types.BoundingBox;
import org.wirez.shapes.client.view.BasicShapeView;
import org.wirez.shapes.proxy.icon.statics.Icons;

public class StaticIconShapeView<T extends StaticIconShapeView> 
        extends BasicShapeView<T> {

    private Icons icon;
    private double width;
    private double height;
    private Group iconGroup;
    
    public StaticIconShapeView( final Icons icon,
                                final WiresManager manager ) {
        
        super( new MultiPath()
                .setStrokeWidth( 0 )
                .setStrokeAlpha( 0 ), 
                manager );
        
        this.setIcon( icon );
        
    }

    public T setIcon( final Icons icon ) {
        this.icon = icon;

        this.buildIconView();

        return (T) this;
    }

    
    private void buildIconView() {
        
        getPath().clear();
        
        if ( null != iconGroup ) {
        
            this.removeChild( iconGroup );
        }
        
        iconGroup = StaticIconsBuilder.build( icon );
        
        if ( null != iconGroup ) {
            
            final BoundingBox bb = iconGroup.getBoundingBox();
            final double w = bb.getWidth();
            final double h = bb.getHeight();
            
            this.width = w;
            this.height = h;
            
            getPath().rect( 0, 0, w, h );
            
            this.addChild( iconGroup, WiresLayoutContainer.Layout.CENTER );

            // Force to redraw the decorator.
            super.refreshDecorators();

            this.moveChild( decorator, - ( width / 2) , - ( height / 2 ) );
        }
        
    }

    /*@Override
    protected Shape<?> getShape() {
        return iconGroup.asShape();
    }*/

    @Override
    protected void doDestroy() {
        super.doDestroy();
        
        if ( null != iconGroup ) {
            
            iconGroup.removeFromParent();
            iconGroup = null;
        }
        
        icon = null;
        
    }

    @Override
    protected Rectangle createDecorator() {
        final Rectangle r = new Rectangle( width, height );
        this.addChild( r, WiresLayoutContainer.Layout.CENTER );
        return r;
    }

}
