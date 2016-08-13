package org.wirez.client.widgets.help.carousel;

import org.jboss.errai.databinding.client.api.Bindable;

@Bindable
public class HelpCarouselItem {

    private String dataURL;

    private String title;

    public String getDataURL() {
        return dataURL;
    }

    public void setDataURL( String dataURL ) {
        this.dataURL = dataURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }
}
