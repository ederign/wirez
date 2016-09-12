package org.wirez.core.client.canvas.util;

import org.wirez.core.client.shape.ShapeState;
import org.wirez.core.graph.Node;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.shape.Shape;

public class CanvasHighlight {

    private final AbstractCanvasHandler canvasHandler;
    private Shape shape;
    private long duration = 200;

    public CanvasHighlight(final AbstractCanvasHandler canvasHandler ) {
        this.canvasHandler = canvasHandler;
    }

    public CanvasHighlight setDuration(final long duration) {
        this.duration = duration;
        return this;
    }

    public void highLight(final Node node) {
        if ( null != this.shape && !node.getUUID().equals(shape.getUUID()) ) {
            unhighLight();
        }
        if ( null == this.shape ) {
            final String uuid = node.getUUID();
            final Shape shape = getShape(uuid);
            if ( null != shape ) {
                this.shape = shape;

                shape.applyState( ShapeState.SELECTED );

                getCanvas().draw();


            }
        }
    }

    public void unhighLight() {
        if ( null != this.shape ) {

            this.shape.applyState( ShapeState.UNHIGHLIGHT );

            getCanvas().draw();

            this.shape = null;

        }
    }
    
    public void destroy() {
      
        this.shape = null;

    }


    private Shape getShape(final String uuid) {
        return getCanvas().getShape(uuid);
    }
    
    private AbstractCanvas getCanvas() {
        return canvasHandler.getCanvas();
    }
    
}
