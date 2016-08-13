package org.wirez.client.widgets.help.carousel.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.common.client.api.IsElement;
import org.jboss.errai.common.client.dom.Div;
import org.jboss.errai.common.client.dom.HTMLElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.client.mvp.UberView;
import org.wirez.client.widgets.resources.WirezWidgetsImageResources;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.util.GraphUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Logger;

@Dependent
@Templated
public class HelpCarouselDisplayer extends HelpCarouselView implements IsElement {

    @Inject
    @DataField
    private Div theCarousel;

    @Inject
    @DataField
    private Div theCarouselIndicators;

    @Inject
    @DataField
    private Div theCarouselInner;

    @Override
    public HTMLElement getElement() {
        return theCarousel;
    }

}
