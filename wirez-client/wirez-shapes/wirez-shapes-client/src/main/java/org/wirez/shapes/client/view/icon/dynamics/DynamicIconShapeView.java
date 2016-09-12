package org.wirez.shapes.client.view.icon.dynamics;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.core.client.shape.view.HasSize;
import org.wirez.shapes.client.view.BasicShapeView;
import org.wirez.shapes.proxy.icon.dynamics.Icons;

public class DynamicIconShapeView<T extends DynamicIconShapeView> 
        extends BasicShapeView<T>
        implements HasSize<T>  {

    private final static String BLACK = "#000000";
    private final static double STROKE_WIDTH = 0;
    
    private Icons icon;
    private double width;
    private double height;
    
    public DynamicIconShapeView(final Icons icon,
                                final double width,
                                final double height,
                                final WiresManager manager ) {
        super( buildIcon( new MultiPath(),
                icon, 
                width, 
                height,
                BLACK,
                BLACK,
                STROKE_WIDTH ), 
                manager );
        this.icon = icon;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void doDestroy() {
        super.doDestroy();
        
        icon = null;
        width = 0;
        height = 0;
    }

    @Override
    protected Rectangle createDecorator() {
        final Rectangle r = new Rectangle( width, height );
        this.addChild( r, WiresLayoutContainer.Layout.CENTER );
        return r;
    }

    public T setIcon( final Icons icon ) {
        this.icon = icon;
        updateIcon();
        
        return (T) this;
    }

    @Override
    public T setSize( final double width, 
                      final double height) {

        this.width = width;
        this.height = height;
        
        updateIcon();
        
        return (T) this;
    }

    private void updateIcon() {

        buildIcon( getPath(), 
                icon, 
                width, 
                height, 
                getFillColor(), 
                getStrokeColor(),
                getStrokeWidth() );
        
        // Force to redraw the decorator.
        super.refreshDecorators();

        this.moveChild( decorator, - ( width / 2) , - ( height / 2 ) );

    }

    private static MultiPath buildIcon(final MultiPath path, 
                                       final Icons icon,
                                       final double w,
                                       final double h,
                                       final String fillColor,
                                       final String strokeColor,
                                       final double strokeWidth) {

        return DynamicIconsBuilder.build( path, icon, w, h )
                .setFillColor( fillColor )
                .setStrokeColor( strokeColor )
                .setStrokeWidth( strokeWidth );
    }

}
