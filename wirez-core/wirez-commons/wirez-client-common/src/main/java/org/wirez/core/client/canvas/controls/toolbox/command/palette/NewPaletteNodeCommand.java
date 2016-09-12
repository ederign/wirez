package org.wirez.core.client.canvas.controls.toolbox.command.palette;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.AnimationFactory;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.BuilderControl;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.canvas.controls.builder.request.NodeBuildRequest;
import org.wirez.core.client.canvas.controls.builder.request.NodeBuildRequestImpl;
import org.wirez.core.client.canvas.util.CanvasHighlight;
import org.wirez.core.client.components.drag.NodeDragProxyCallback;
import org.wirez.core.client.components.drag.NodeDragProxy;
import org.wirez.core.client.components.palette.Palette;
import org.wirez.core.client.components.palette.model.GlyphPaletteItem;
import org.wirez.core.client.components.palette.model.HasPaletteItems;
import org.wirez.core.client.components.palette.model.definition.DefinitionsPaletteBuilder;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.lookup.util.CommonLookups;
import org.wirez.core.util.UUID;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class NewPaletteNodeCommand<I> extends AbstractPaletteCommand<I> {

    private static Logger LOGGER = Logger.getLogger(NewPaletteNodeCommand.class.getName());
    
    public NewPaletteNodeCommand(final ClientFactoryServices clientFactoryServices,
                                 final CommonLookups commonLookups,
                                 final ShapeManager shapeManager,
                                 final DefinitionsPaletteBuilder definitionsPaletteBuilder,
                                 final Palette<HasPaletteItems<? extends GlyphPaletteItem>> palette,
                                 final NodeDragProxy<AbstractCanvasHandler> nodeDragProxyFactory,
                                 final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                                 final GraphBoundsIndexer graphBoundsIndexer,
                                 final I icon) {
        
        super(clientFactoryServices, commonLookups, shapeManager, definitionsPaletteBuilder, palette,
                nodeDragProxyFactory, nodeBuilderControl, graphBoundsIndexer, icon);
        
    }

    protected abstract String getDefinitionSetIdentifier();

    protected abstract String getEdgeIdentifier();

    @Override
    @SuppressWarnings("unchecked")
    protected Set<String> getDefinitions() {

        // TODO: Handle all response buckets/pages.

        return commonLookups.getAllowedDefinitions( getDefinitionSetIdentifier(),
                canvasHandler.getDiagram().getGraph(), this.sourceNode,  getEdgeIdentifier(), 0, 10 );
        
    }

    @Override
    @SuppressWarnings( "unchecked" )
    protected void onItemSelected( final String definitionId, 
                                   final ShapeFactory<?, ?, ?> factory,
                                   final double x,
                                   final double y ) {

        clientFactoryServices.newElement( UUID.uuid(), getEdgeIdentifier(), new ServiceCallback<Element>() {

            @Override
            public void onSuccess(final Element edgeItem) {

                final Edge<View<?>, Node> edge = (Edge<View<?>, Node>) edgeItem;

                // Manually set the source node as the drag proxy will need it.
                edge.setSourceNode( sourceNode );

                clientFactoryServices.newElement( UUID.uuid(), definitionId, new ServiceCallback<Element>() {

                    @Override
                    public void onSuccess(final Element nodeItem) {

                        final Node<View<?>, Edge> node = (Node<View<?>, Edge>) nodeItem;

                        final ShapeFactory<?, AbstractCanvasHandler, ?> nodeShapeFactory = shapeManager.getFactory( definitionId );
                        final ShapeFactory<?, AbstractCanvasHandler, ?> edgeShapeFactory = shapeManager.getFactory( getEdgeIdentifier() );


                        final NodeDragProxy.Item<AbstractCanvasHandler> item = new NodeDragProxy.Item<AbstractCanvasHandler>() {
                            @Override
                            public Node<View<?>, Edge> getNode() {
                                return node;
                            }

                            @Override
                            public ShapeFactory<?, AbstractCanvasHandler, ?> getNodeShapeFactory() {
                                return nodeShapeFactory;
                            }

                            @Override
                            public Edge<View<?>, Node> getInEdge() {
                                return edge;
                            }

                            @Override
                            public Node<View<?>, Edge> getInEdgeSourceNode() {
                                return edge.getSourceNode();
                            }

                            @Override
                            public ShapeFactory<?, AbstractCanvasHandler, ?> getInEdgeShapeFactory() {
                                return edgeShapeFactory;
                            }


                        };

                        nodeBuilderControl.enable( canvasHandler );

                        canvasHighlight = new CanvasHighlight( canvasHandler );

                        graphBoundsIndexer.build( canvasHandler.getDiagram().getGraph() );

                        nodeDragProxyFactory
                                .proxyFor( canvasHandler )
                                .show(item, (int) x, (int) y, new NodeDragProxyCallback() {

                                    @Override
                                    public void onStart(final int x,
                                                        final int y) {

                                    }

                                    @Override
                                    public void onMove(final int x,
                                                       final int y) {

                                        final NodeBuildRequest request = new NodeBuildRequestImpl( x, y , node, edge );
                                        final boolean accepts = nodeBuilderControl.allows( request );

                                        if ( accepts ) {

                                            final Node parent = graphBoundsIndexer.getAt( x, y );

                                            if ( null != parent ) {
                                                canvasHighlight.highLight( parent );
                                            }

                                        } else {

                                            canvasHighlight.unhighLight();

                                        }

                                    }

                                    @Override
                                    public void onComplete(final int x,
                                                           final int y) {



                                    }

                                    @Override
                                    public void onComplete( int x, int y, int sourceMagnet, int targetMagnet ) {

                                        final NodeBuildRequest request = new NodeBuildRequestImpl( x, y , node, edge, sourceMagnet, targetMagnet );

                                        nodeBuilderControl.build( request, new BuilderControl.BuildCallback() {

                                            @Override
                                            public void onSuccess( final String uuid ) {

                                                nodeBuilderControl.disable();

                                                canvasHighlight.unhighLight();

                                            }

                                            @Override
                                            public void onError( final ClientRuntimeError error ) {

                                                log( Level.SEVERE, error.toString() );
                                                
                                            }

                                        } );

                                    }

                                });

                    }

                    @Override
                    public void onError(final ClientRuntimeError error) {
                        log( Level.SEVERE, error.toString() );
                    }
                });

            }

            @Override
            public void onError(final ClientRuntimeError error) {
                log( Level.SEVERE, error.toString() );
            }
        });
        
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
