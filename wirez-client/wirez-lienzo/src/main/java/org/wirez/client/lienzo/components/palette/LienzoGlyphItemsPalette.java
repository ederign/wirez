package org.wirez.client.lienzo.components.palette;

import org.wirez.client.lienzo.components.palette.view.LienzoPaletteView;
import org.wirez.core.client.components.glyph.DefinitionGlyphTooltip;
import org.wirez.core.client.components.palette.model.GlyphPaletteItem;
import org.wirez.core.client.components.palette.model.HasPaletteItems;

import java.util.List;

public interface LienzoGlyphItemsPalette<I extends HasPaletteItems<? extends GlyphPaletteItem>, V extends LienzoPaletteView>
        extends LienzoPalette<I, V> {

    interface GlyphTooltipCallback {

        boolean onShowTooltip(DefinitionGlyphTooltip<?> glyphTooltip,
                              final GlyphPaletteItem item,
                              double mouseX,
                              double mouseY,
                              double itemX,
                              double itemY);

    }

    List<GlyphPaletteItem> getItems();

    GlyphPaletteItem getItem( int pos );

    LienzoGlyphItemsPalette<I, V> onShowGlyTooltip( GlyphTooltipCallback callback );


}
