package org.wirez.client.lienzo.canvas.controls.toolbox.command.palette;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.client.lienzo.LienzoLayer;
import org.wirez.client.lienzo.components.palette.AbstractLienzoGlyphItemsPalette;
import org.wirez.client.lienzo.components.palette.LienzoGlyphsHoverPalette;
import org.wirez.client.lienzo.components.palette.LienzoPalette;
import org.wirez.client.lienzo.util.SVGUtils;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.AnimationFactory;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.canvas.controls.toolbox.command.palette.AbstractPaletteMorphCommand;
import org.wirez.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.wirez.core.client.command.CanvasCommandManager;
import org.wirez.core.client.command.Session;
import org.wirez.core.client.components.drag.NodeDragProxy;
import org.wirez.core.client.components.palette.model.definition.DefinitionsPaletteBuilder;
import org.wirez.core.client.components.palette.view.PaletteView;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.command.SessionCommandManager;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.lookup.util.CommonLookups;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class LienzoPaletteMorphToolboxCommand extends AbstractPaletteMorphCommand<Shape<?>> {
    
    @Inject
    public LienzoPaletteMorphToolboxCommand(final DefinitionUtils definitionUtils,
                                            final CanvasCommandFactory commandFactory,
                                            final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                            final ClientFactoryServices clientFactoryServices,
                                            final CommonLookups commonLookups,
                                            final ShapeManager shapeManager,
                                            final DefinitionsPaletteBuilder definitionsPaletteBuilder,
                                            final LienzoGlyphsHoverPalette palette,
                                            final NodeDragProxy<AbstractCanvasHandler> nodeDragProxyFactory,
                                            final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                                            final GraphBoundsIndexer graphBoundsIndexer,
                                            final Event<CanvasElementSelectedEvent> elementSelectedEvent ) {
        
        super( definitionUtils, commandFactory, canvasCommandManager, clientFactoryServices,
                commonLookups,shapeManager, definitionsPaletteBuilder, palette, nodeDragProxyFactory,
                nodeBuilderControl, graphBoundsIndexer,  SVGUtils.createSVGIcon( SVGUtils.getGearIcon() ),
                elementSelectedEvent );
        
    }

    @PostConstruct
    public void init() {
        getLienzoPalette()
                .collapse()
                .setExpandable( false )
                .setIconSize( 18 )
                .setPadding( 10 )
                .setLayout(LienzoPalette.Layout.HORIZONTAL );

        ( (AbstractLienzoGlyphItemsPalette ) getLienzoPalette()).getDefinitionGlyphTooltip().setPrefix( "Convert to " );

    }

    @Override
    @SuppressWarnings("unchecked")
    protected void attachPaletteView() {
        final LienzoLayer lienzoLayer = (LienzoLayer) canvasHandler.getCanvas().getLayer();
        getPaletteView().attach( lienzoLayer.getLienzoLayer() );
    }

    @Override
    protected PaletteView getPaletteView() {
        return getLienzoPalette().getView();
    }

    protected LienzoPalette getLienzoPalette() {
        return (LienzoPalette) palette;
    }
}
