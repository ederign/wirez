package org.wirez.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.session.impl.DefaultCanvasReadOnlySession;
import org.wirez.core.client.util.CanvasHighlightVisitor;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class VisitGraphCommand extends AbstractToolbarCommand<DefaultCanvasReadOnlySession> {


    @Inject
    public VisitGraphCommand(final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                             final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
    }

    @Override
    public IconType getIcon() {
        return IconType.EYE;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getTooltip() {
        return "Visit the graph";
    }

    @Override
    public <T> void execute(final DefaultCanvasReadOnlySession session, 
                            final ToolbarCommandCallback<T> callback) {

        new CanvasHighlightVisitor(session.getCanvasHandler()).run(() -> {
            if ( null != callback ) {
                callback.onSuccess( null );
            }
        });
    }
    
}
