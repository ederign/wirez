package org.wirez.core.client.canvas.controls.toolbox.command.builder;

import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.AnimationFactory;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.BuildRequest;
import org.wirez.core.client.canvas.controls.builder.BuilderControl;
import org.wirez.core.client.canvas.controls.builder.EdgeBuilderControl;
import org.wirez.core.client.canvas.controls.builder.request.EdgeBuildRequest;
import org.wirez.core.client.canvas.controls.builder.request.EdgeBuildRequestImpl;
import org.wirez.core.client.canvas.controls.toolbox.command.Context;
import org.wirez.core.client.components.drag.ConnectorDragProxyFactory;
import org.wirez.core.client.components.drag.DragProxyFactory;
import org.wirez.core.client.components.glyph.DefinitionGlyphTooltip;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;

public abstract class NewConnectorCommand<I> extends AbstractElementBuilderCommand<I> {

    protected ConnectorDragProxyFactory<AbstractCanvasHandler> connectorDragProxyFactory;
    protected EdgeBuilderControl<AbstractCanvasHandler> edgeBuilderControl;

    private String edgeId;

    protected NewConnectorCommand() {
        this( null, null, null, null, null, null, null, null );
    }

    public NewConnectorCommand(final ClientDefinitionManager clientDefinitionManager,
                                final ClientFactoryServices clientFactoryServices,
                                final ShapeManager shapeManager,
                               final DefinitionGlyphTooltip<?> glyphTooltip,
                                final GraphBoundsIndexer graphBoundsIndexer,
                                final ConnectorDragProxyFactory<AbstractCanvasHandler> connectorDragProxyFactory,
                                final EdgeBuilderControl<AbstractCanvasHandler> edgeBuilderControl,
                                final AnimationFactory animationFactory ) {
        super( clientDefinitionManager, clientFactoryServices, shapeManager, glyphTooltip, graphBoundsIndexer,
                animationFactory );
        this.connectorDragProxyFactory = connectorDragProxyFactory;
        this.edgeBuilderControl = edgeBuilderControl;

    }

    public void setEdgeIdentifier( final String edgeId ) {
        this.edgeId = edgeId;
    }

    @Override
    protected String getDefinitionIdentifier( final Context<AbstractCanvasHandler> context ) {
        return edgeId;
    }


    @Override
    protected String getGlyphDefinitionId() {
        return edgeId;
    }

    @Override
    public String getTitle() {
        return "Creates a new connector";
    }

    @Override
    protected DragProxyFactory getDragProxyFactory() {
        return connectorDragProxyFactory;
    }

    @Override
    protected BuilderControl getBuilderControl() {
        return edgeBuilderControl;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object createtBuilderControlItem( final Context<AbstractCanvasHandler> context,
                                                final Element source,
                                                final Element newElement ) {

        final Node<View<?>, Edge> sourceNode = (Node<View<?>, Edge>) source;
        final Edge<View<?>, Node> edge = (Edge<View<?>, Node>) newElement;
        final ShapeFactory<? ,?, ?> edgeFactory = shapeManager.getFactory( edgeId );

        return new ConnectorDragProxyFactory.Item() {
            @Override
            public Edge<View<?>, Node> getEdge() {
                return edge;
            }

            @Override
            public Node<View<?>, Edge> getSourceNode() {
                return sourceNode;
            }

            @Override
            public ShapeFactory getShapeFactory() {
                return edgeFactory;
            }
        };

    }

    @Override
    @SuppressWarnings("unchecked")
    protected boolean onDragProxyMove( final int x,
                                       final int y,
                                       final Element source,
                                       final Element newElement,
                                       final Node targetNode ) {

        final Node<View<?>, Edge> sourceNode = (Node<View<?>, Edge>) source;
        final Edge<View<?>, Node> edge = (Edge<View<?>, Node>) newElement;

        if ( null != targetNode ) {

            final EdgeBuildRequest request = new EdgeBuildRequestImpl( x, y, edge, sourceNode, targetNode );
            return edgeBuilderControl.allows( request );

        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected BuildRequest createBuildRequest(final int x,
                                              final int y,
                                              final Element source,
                                              final Element newElement,
                                              final Node targetNode) {

        final Node<View<?>, Edge> sourceNode = (Node<View<?>, Edge>) source;
        final Edge<View<?>, Node> edge = (Edge<View<?>, Node>) newElement;

        return new EdgeBuildRequestImpl( x, y, edge, sourceNode, targetNode );
    }

    @Override
    public void destroy() {

        super.destroy();

        this.connectorDragProxyFactory = null;
        this.edgeBuilderControl = null;

    }

}
