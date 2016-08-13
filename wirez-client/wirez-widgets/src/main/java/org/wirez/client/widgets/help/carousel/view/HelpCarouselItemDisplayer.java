package org.wirez.client.widgets.help.carousel.view;

import com.google.gwt.user.client.ui.Label;
import org.jboss.errai.common.client.api.IsElement;
import org.jboss.errai.common.client.dom.Div;
import org.jboss.errai.common.client.dom.HTMLElement;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Templated
public class HelpCarouselItemDisplayer extends HelpCarouselItemView implements IsElement {

    @Inject
    @DataField
    private Div citem;

    @Inject
    @Bound
    @DataField
    private Label title = new Label();

    @Override
    public HTMLElement getElement() {
        return citem;
    }
}
