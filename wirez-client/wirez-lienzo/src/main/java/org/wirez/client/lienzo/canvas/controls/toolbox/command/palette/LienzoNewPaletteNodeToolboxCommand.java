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
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.canvas.controls.toolbox.command.palette.NewPaletteNodeCommand;
import org.wirez.core.client.components.drag.NodeDragProxy;
import org.wirez.core.client.components.palette.model.definition.DefinitionsPaletteBuilder;
import org.wirez.core.client.components.palette.view.PaletteView;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.lookup.util.CommonLookups;

import javax.annotation.PostConstruct;

/// Abstract for not being discovered by the CDI environment, for now...
public abstract class LienzoNewPaletteNodeToolboxCommand extends NewPaletteNodeCommand<Shape<?>> {
    
    public LienzoNewPaletteNodeToolboxCommand(final ClientFactoryServices clientFactoryServices,
                                              final CommonLookups commonLookups,
                                              final ShapeManager shapeManager,
                                              final DefinitionsPaletteBuilder definitionsPaletteBuilder,
                                              final LienzoGlyphsHoverPalette palette,
                                              final NodeDragProxy<AbstractCanvasHandler> nodeDragProxyFactory,
                                              final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                                              final GraphBoundsIndexer graphBoundsIndexer ) {
        super(clientFactoryServices, commonLookups, shapeManager,
                definitionsPaletteBuilder, palette, nodeDragProxyFactory,
                nodeBuilderControl, graphBoundsIndexer, SVGUtils.createSVGIcon(SVGUtils.getAddIcon()) );
    }

    @PostConstruct
    public void init() {
        getLienzoPalette()
                .expand()
                .setIconSize( 15 )
                .setPadding( 5 )
                .setLayout(LienzoPalette.Layout.VERTICAL );

        ( (AbstractLienzoGlyphItemsPalette ) getLienzoPalette()).getDefinitionGlyphTooltip().setPrefix( "Create a new " );

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
