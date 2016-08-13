package org.wirez.client.widgets.help.carousel;

import org.jboss.errai.databinding.client.api.Bindable;

import java.util.List;

@Bindable
public class HelpCarousel {

    private List<HelpCarouselItem> items;

    public List<HelpCarouselItem> getItems() {
        return items;
    }

    public void setItems( List<HelpCarouselItem> items ) {
        this.items = items;
    }

}
