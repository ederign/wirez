package org.wirez.core.client.util;

import com.google.gwt.core.client.GWT;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.shape.EdgeShape;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.animation.HasAnimations;

import java.util.Collection;

public class ShapeUtils {

    public static String getModuleAbsPath( final String path ) {
        return GWT.getModuleBaseURL() + path;
    }
    
    @SuppressWarnings("unchecked")
    public static void applyConnections(final Edge<?, ?> edge,
                                 final CanvasHandler canvasHandler,
                                 final MutationContext mutationContext) {

        final Canvas<?> canvas = canvasHandler.getCanvas();
        final Node sourceNode = edge.getSourceNode();
        final Node targetNode = edge.getTargetNode();
        final Shape<?> source = sourceNode != null ? canvas.getShape( sourceNode.getUUID() ) : null;
        final Shape<?> target = targetNode != null ? canvas.getShape( targetNode.getUUID() ) : null;

        EdgeShape connector = (EdgeShape) canvas.getShape( edge.getUUID() );
       
        connector.applyConnections( edge, 
                source != null ? source.getShapeView() : null, 
                target != null ? target.getShapeView() : null, 
                mutationContext );
    }
    
    public static boolean isStaticMutation( final MutationContext mutationContext ) {
        return mutationContext == null || MutationContext.Type.STATIC.equals( mutationContext.getType() );
    }

    public static boolean isAnimationMutation( final Object view, final MutationContext mutationContext ) {
        return mutationContext != null && MutationContext.Type.ANIMATION.equals( mutationContext.getType() )
                && view instanceof HasAnimations;
    }
    
    public static ShapeSet getShapeSet(final Collection<ShapeSet> shapeSets, final String id) {
        if ( null != id && null != shapeSets ) {
            
            for (final ShapeSet shapeSet : shapeSets) {
                if (id.equals(shapeSet.getId())) {
                    return shapeSet;
                }
            }
            
        }
        
        return null;
    }

    public static double[] getContainerXY(final Shape shape) {
        return new double[] { shape.getShapeView().getShapeX(),
                shape.getShapeView().getShapeY()};
    }
    
    /**
     * Returns the distance between two points in a dual axis cartesian graph. 
     */
    public static double dist(final double x0, final double y0, final double x1, final double y1) {
        final double dx = Math.abs( x1 - x0 );
        final double dy = Math.abs( y1 - y0 );
        return ( Math.sqrt( ( dx * dx ) + ( dy * dy ) ) );
    }
}
