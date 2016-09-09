package org.wirez.client.widgets.session.presenter.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.wirez.client.widgets.session.presenter.CanvasSessionPresenter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Templated
public class CanvasSessionPresenterView
        extends Composite
        implements CanvasSessionPresenter.View {

    @Inject
    @DataField
    private FlowPanel loadingPanel;


    @Inject
    @DataField
    private FlowPanel toolbarPanel;

    @Inject
    @DataField
    private FlowPanel canvasPanel;

    @Override
    public CanvasSessionPresenter.View setToolbar(final IsWidget widget) {
        toolbarPanel.clear();
        toolbarPanel.add( widget );
        return this;
    }

    @Override
    public CanvasSessionPresenter.View setCanvas(final IsWidget widget) {
        canvasPanel.clear();
        canvasPanel.add( widget );
        return this;
    }

    @Override
    public CanvasSessionPresenter.View setLoading( final boolean loading ) {
        loadingPanel.setVisible( loading );
        return this;
    }

    @Override
    public void destroy() {
        this.removeFromParent();
    }

}
