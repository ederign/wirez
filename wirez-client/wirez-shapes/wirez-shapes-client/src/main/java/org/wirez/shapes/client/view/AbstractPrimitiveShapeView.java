package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.types.BoundingBox;

/**
 * This shape handles two primitives added as wires shape's children: 
 * - The visible primitive
 * - The decorator primitive
 */
public abstract class AbstractPrimitiveShapeView<T> extends org.wirez.shapes.client.view.AbstractDecoratableShapeView<T> {
    
    public AbstractPrimitiveShapeView(final MultiPath path,
                                      final WiresManager manager) {
        super( path, manager );
    }

    protected abstract Shape<?> getPrimitive();

    protected abstract Shape<?> createChildren();

    @Override
    protected void doDestroy() {
        super.doDestroy();
        
        if ( null != getPrimitive() ) {
            getPrimitive().removeFromParent();
        }
        
    }

    protected void initialize() {
        super.initialize();
        getPath().setFillAlpha(0).setStrokeAlpha(0);
        getPath().moveToTop();
        createEventHandlerManager( getPrimitive() );
    }

    @Override
    protected Shape<?> createDecorator() {
        return createChildren();
    }

    protected Shape<?> getShape() {
        return getPrimitive();
    }

    protected  T setRadius( final double radius ) {
        
        if (radius > 0) {
            
            final double size = radius * 2;
            updatePath( size, size );
            getShape().getAttributes().setRadius( radius );
            
            setDecoratorRadius( radius );

            doMoveChildren( size, size );

            super.updateFillGradient( radius * 2, radius * 2 );
            
        }
        return (T) this;
    }
    
    protected void setDecoratorRadius( final double radius ) {

        if ( null != decorator ) {

            decorator.getAttributes().setRadius( radius );

        }
        
    }

    protected T setSize( final double width, 
                         final double height ) {
        
        updatePath( width, height );
        
        getShape().getAttributes().setWidth( width );
        getShape().getAttributes().setHeight( height );
        
        if ( null != decorator ) {

            decorator.getAttributes().setWidth( width );
            decorator.getAttributes().setHeight( height );
            
        }

        doMoveChildren( width, height );
        
        super.updateFillGradient( width, height );
        
        return (T) this;
    }
    
    protected void doMoveChildren( final double width, 
                                   final double height ) {

        doMoveChild( getShape(), width, height );
        
        if ( null != decorator ) {

            doMoveChild( decorator, width, height );

        }

        if ( !children.isEmpty() ) {
            
            for ( final BasicShapeView<T> child : children ) {

                final IPrimitive<?> childPrimitive = (IPrimitive<?>) child.getContainer();
                final BoundingBox bb = child.getPath().getBoundingBox();
                
                doMoveChild( childPrimitive, bb.getWidth(), bb.getHeight() );
                
            }
            
        }
        
    }

    protected void doMoveChild( final IPrimitive<?> child, 
                                final double width, 
                                final double height ) {

        final double sx = getChildCenterCoordinate( child, width );

        final double sy = getChildCenterCoordinate( child, height );

        if ( sx != 0 || sy != 0 ) {

            this.moveChild( child, sx, sy );

        }

    }

    protected double getChildCenterCoordinate( final IPrimitive<?> child, 
                                               final double delta ) {

        if ( child.getAttributes().getRadius() == 0 ) {

            return - ( delta / 2 );

        } else {
            
            return  0;
            
        }
        
    }


    protected void updatePath( final double width, 
                               final double height ) {

        final double x = getPath().getX();
        final double y = getPath().getY();
        getPath().clear().rect(x, y, width, height);
    }

}
