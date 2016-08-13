package org.wirez.client.widgets.help.carousel.view;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.wirez.client.widgets.help.carousel.HelpCarouselItem;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class HelpCarouselItemView implements TakesValue<HelpCarouselItem> {

    @Inject
    @AutoBound
    protected DataBinder<HelpCarouselItem> binder;

    @Override
    public void setValue( final HelpCarouselItem carouselItem ) {
        binder.setModel( carouselItem );
    }

    @Override
    public HelpCarouselItem getValue() {
        return binder.getModel();
    }

}
