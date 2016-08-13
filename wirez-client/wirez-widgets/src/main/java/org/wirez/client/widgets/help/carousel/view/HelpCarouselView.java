package org.wirez.client.widgets.help.carousel.view;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.wirez.client.widgets.help.carousel.HelpCarousel;
import org.wirez.client.widgets.help.carousel.HelpCarouselItem;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class HelpCarouselView  implements TakesValue<HelpCarousel> {

    @Inject
    @AutoBound
    protected DataBinder<HelpCarousel> binder;

    @Override
    public void setValue( final HelpCarousel carouselItem ) {
        binder.setModel( carouselItem );
    }

    @Override
    public HelpCarousel getValue() {
        return binder.getModel();
    }

}
