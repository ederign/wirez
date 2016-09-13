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
public abstract class BasicPrimitiveShapeView<T> extends org.wirez.shapes.client.view.BasicShapeView<T> {
    
    public BasicPrimitiveShapeView( final MultiPath path,
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

    @Override
    protected void postInitialize() {
        super.postInitialize();
        getPath().setFillAlpha(0).setStrokeAlpha(0);
        getPath().moveToTop();
        createEventHandlerManager( getPrimitive() );
    }

    @Override
    protected Shape<?> createDecorator() {
        return createChildren();
    }

    public Shape<?> getShape() {
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
    




}
