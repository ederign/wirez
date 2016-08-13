package org.wirez.client.widgets.help.carousel;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.client.widgets.help.carousel.view.HelpCarouselDisplayer;
import org.wirez.client.widgets.resources.WirezWidgetsImageResources;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.util.GraphUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Logger;

@Dependent
public class HelpCarouselPresenter implements IsWidget {

    private static Logger LOGGER = Logger.getLogger( HelpCarouselPresenter.class.getName() );

    HelpCarouselDisplayer helpCarouselDisplayer;

    @Inject
    public HelpCarouselPresenter( final HelpCarouselDisplayer helpCarouselDisplayer ) {
        this.helpCarouselDisplayer = helpCarouselDisplayer;
    }

    @Override
    public Widget asWidget() {
        final FlowPanel panel = new FlowPanel(  );
        panel.getElement().insertFirst( ( Node ) helpCarouselDisplayer.getElement() );
        return panel;
    }

    public void show() {

        final WirezWidgetsImageResources imageResources = WirezWidgetsImageResources.INSTANCE;

        // TODO: Use Errai UI...

        // Bs3Modal


    }

    public void hide() {

    }

}
