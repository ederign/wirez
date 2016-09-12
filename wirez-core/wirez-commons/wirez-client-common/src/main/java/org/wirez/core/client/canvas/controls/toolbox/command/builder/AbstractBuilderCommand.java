package org.wirez.core.client.canvas.controls.toolbox.command.builder;

import org.uberfire.mvp.Command;
import org.wirez.core.client.api.ClientDefinitionManager;
import org.wirez.core.client.animation.AnimationFactory;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.BuildRequest;
import org.wirez.core.client.canvas.controls.builder.BuilderControl;
import org.wirez.core.client.canvas.controls.toolbox.command.AbstractToolboxCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.Context;
import org.wirez.core.client.canvas.util.CanvasHighlight;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.DragProxy;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.util.UUID;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractBuilderCommand<I> extends AbstractToolboxCommand<I> {

    private static Logger LOGGER = Logger.getLogger(AbstractBuilderCommand.class.getName());

    protected ClientDefinitionManager clientDefinitionManager;
    protected ClientFactoryServices clientFactoryServices;
    protected GraphBoundsIndexer graphBoundsIndexer;

    protected CanvasHighlight canvasHighlight;

    protected AbstractBuilderCommand() {
        this( null, null, null );
    }

    public AbstractBuilderCommand(final ClientDefinitionManager clientDefinitionManager,
                                  final ClientFactoryServices clientFactoryServices,
                                  final GraphBoundsIndexer graphBoundsIndexer ) {
        this.clientDefinitionManager = clientDefinitionManager;
        this.clientFactoryServices = clientFactoryServices;
        this.graphBoundsIndexer = graphBoundsIndexer;
    }

    protected abstract String getDefinitionIdentifier( Context<AbstractCanvasHandler> context );

    protected abstract DragProxy getDragProxyFactory();

    protected abstract DragProxyCallback getDragProxyCallback( Context<AbstractCanvasHandler> context, Element element, Element newElement );

    protected abstract BuilderControl getBuilderControl();

    protected abstract Object createtBuilderControlItem( Context<AbstractCanvasHandler> context, Element source, Element newElement );

    protected abstract boolean onDragProxyMove( int x, int y, Element source, Element newElement, Node targetNode );

    protected abstract BuildRequest createBuildRequest( int x, int y, Element source, Element newElement, Node targetNode );

    protected void onDefinitionInstanceBuilt( final Context<AbstractCanvasHandler> context,
                                              final Element source,
                                              final Element newElement,
                                              final Command callback ) {

        callback.execute();

    }

    @Override
    public void mouseDown( final Context<AbstractCanvasHandler> context,
                           final Element element ) {
        super.mouseDown( context, element );

        showDragProxy( context, element );
    }

    @Override
    public void click(final Context<AbstractCanvasHandler> context,
                        final Element element) {
        super.click( context, element );

    }

    @SuppressWarnings("unchecked")
    private void showDragProxy( final Context<AbstractCanvasHandler> context,
                                final Element element ) {

        final AbstractCanvasHandler canvasHandler = context.getCanvasHandler();
        final double x = context.getX();
        final double y = context.getY();

        graphBoundsIndexer.setRootUUID( canvasHandler.getDiagram().getSettings().getCanvasRootUUID() );

        clientFactoryServices.newElement( UUID.uuid(), getDefinitionIdentifier( context ), new ServiceCallback<Element>() {

            @Override
            public void onSuccess( final Element  item ) {

                onDefinitionInstanceBuilt( context, element, item, () -> {

                    getBuilderControl().enable( canvasHandler );

                    canvasHighlight = new CanvasHighlight( canvasHandler );

                    graphBoundsIndexer.build( canvasHandler.getDiagram().getGraph() );

                    DragProxyCallback proxyCallback = getDragProxyCallback( context, element, item );

                    getDragProxyFactory()
                            .proxyFor( canvasHandler )
                            .show(
                                    createtBuilderControlItem( context, element, item ),
                                    (int) x,
                                    (int) y,
                                    proxyCallback
                            );

                });

            }

            @Override
            public void onError(final ClientRuntimeError error) {
                AbstractBuilderCommand.this.onError( context, error );
            }

        });

    }

    @SuppressWarnings( "unchecked" )
    protected void onStart( final Context<AbstractCanvasHandler> context,
                            final Element element,
                         final Element item,
                         final int x1,
                         final int y1 ) {

    }

    @SuppressWarnings( "unchecked" )
    protected void onMove( final Context<AbstractCanvasHandler> context,
                           final Element element,
                         final Element item,
                         final int x1,
                         final int y1 ) {

        // TODO: Two expensive calls to bounds indexer, this one and the one inside connectorDragProxyFactory.

        final Node targetNode = graphBoundsIndexer.getAt(x1, y1);

        final boolean accepts = onDragProxyMove(x1, y1, element, item, targetNode );

        if ( accepts ) {

            canvasHighlight.highLight( targetNode );

        } else {

            canvasHighlight.unhighLight();

        }

    }

    @SuppressWarnings( "unchecked" )
    protected void onComplete( final Context<AbstractCanvasHandler> context,
                               final Element element,
                         final Element item,
                         final int x1,
                         final int y1 ) {

        fireLoadingStarted( context );

        final Node targetNode = graphBoundsIndexer.getAt(x1, y1);

        if ( null != targetNode ) {

            final BuildRequest buildRequest = createBuildRequest(x1, y1, element, item, targetNode );

            getBuilderControl().build( buildRequest, new BuilderControl.BuildCallback() {

                @Override
                public void onSuccess( final String uuid ) {

                    onItemBuilt( context, uuid );

                }

                @Override
                public void onError( final ClientRuntimeError error ) {

                    AbstractBuilderCommand.this.onError( context, error );

                }

            } );


        }

    }

    protected void onError( final Context<AbstractCanvasHandler> context,
                            final ClientRuntimeError error ) {

        fireLoadingCompleted( context );

        LOGGER.log( Level.SEVERE, error.toString() );

    }

    protected void onItemBuilt( final Context<AbstractCanvasHandler> context,
                                final String uuid ) {

        fireLoadingCompleted( context );

        if ( null != canvasHighlight ) {

            canvasHighlight.unhighLight();

        }

    }

    @Override
    public void destroy() {
      
        this.getDragProxyFactory().destroy();
        this.getBuilderControl().disable();
        this.graphBoundsIndexer.destroy();

        if ( null != canvasHighlight ) {

            this.canvasHighlight.destroy();
            this.canvasHighlight = null;

        }

        this.clientDefinitionManager = null;
        this.clientFactoryServices = null;
        this.graphBoundsIndexer = null;

    }
    
}
