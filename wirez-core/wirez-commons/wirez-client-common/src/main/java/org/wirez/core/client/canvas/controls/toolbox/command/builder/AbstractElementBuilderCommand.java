package org.wirez.core.client.canvas.controls.toolbox.command.builder;

import org.wirez.core.client.api.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.AnimationFactory;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.toolbox.command.Context;
import org.wirez.core.client.canvas.event.keyboard.KeyDownEvent;
import org.wirez.core.client.canvas.event.keyboard.KeyboardEvent;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.glyph.DefinitionGlyphTooltip;
import org.wirez.core.client.components.glyph.GlyphTooltip;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

public abstract class AbstractElementBuilderCommand<I> extends AbstractBuilderCommand<I> {

    protected ShapeManager shapeManager;
    protected DefinitionGlyphTooltip<?> glyphTooltip;
    protected I iconView;
    protected ShapeFactory factory;

    protected AbstractElementBuilderCommand() {
        this( null, null, null, null, null );
    }

    @Inject
    public AbstractElementBuilderCommand(final ClientDefinitionManager clientDefinitionManager,
                                         final ClientFactoryServices clientFactoryServices,
                                         final ShapeManager shapeManager,
                                         final DefinitionGlyphTooltip<?> glyphTooltip,
                                         final GraphBoundsIndexer graphBoundsIndexer ) {
        super( clientDefinitionManager, clientFactoryServices, graphBoundsIndexer );
        this.shapeManager = shapeManager;
        this.glyphTooltip = glyphTooltip;
    }

    protected abstract String getGlyphDefinitionId();

    @Override
    public void destroy() {
      
        super.destroy();
        this.shapeManager = null;
        this.glyphTooltip = null;
        this.factory = null;
        this.iconView = null;

    }

    @Override
    @SuppressWarnings("unchecked")
    public I getIcon( final double width,
                      final double height ) {

        if ( null == iconView ) {

            final ShapeFactory factory = getFactory();
            final ShapeGlyph<I> glyph = factory.glyph( getGlyphDefinitionId(), width, height );
            this.iconView = glyph.getGroup();

        }

        return iconView;
    }

    @Override
    public void mouseEnter( final Context<AbstractCanvasHandler> context,
                            final Element element ) {

        super.mouseEnter( context, element );

        if ( null != getFactory() ) {

            glyphTooltip
                    .showTooltip( getGlyphDefinitionId(),
                            context.getClientX() + 20,
                            context.getClientY(),
                            GlyphTooltip.Direction.WEST );

        }

    }

    @Override
    public void mouseExit( final Context<AbstractCanvasHandler> context,
                           final Element element ) {

        super.mouseExit( context, element );

        glyphTooltip.hide();

    }

    protected ShapeFactory getFactory() {

        if ( null == factory ) {

            factory = shapeManager.getFactory( getGlyphDefinitionId() );

        }

        return factory;
    }

    @Override
    protected DragProxyCallback getDragProxyCallback( final Context<AbstractCanvasHandler> context,
                                                      final Element element,
                                                      final Element item ) {
        return new DragProxyCallback() {

            @Override
            public void onStart(final int x1,
                                final int y1) {

                AbstractElementBuilderCommand.this.onStart(  context, element, item, x1, y1 );

            }

            @Override
            public void onMove(final int x1,
                               final int y1) {

                AbstractElementBuilderCommand.this.onMove(  context, element, item, x1, y1 );

            }

            @Override
            public void onComplete(final int x1,
                                   final int y1) {

                AbstractElementBuilderCommand.this.onComplete(  context, element, item, x1, y1 );

            }
        };

    }

    protected void clearDragProxy() {

        getDragProxyFactory().clear();

    }

    void onKeyDownEvent( @Observes KeyDownEvent keyDownEvent ) {
        checkNotNull( "keyDownEvent", keyDownEvent );

        final KeyboardEvent.Key key = keyDownEvent.getKey();

        if ( null != key && KeyboardEvent.Key.ESC.equals( key ) ) {

            clearDragProxy();

        }

    }

}
