package org.wirez.core.client.canvas.controls.select;

import org.wirez.core.client.animation.AnimationFactory;
import org.wirez.core.client.api.platform.Desktop;
import org.wirez.core.client.canvas.event.selection.CanvasClearSelectionEvent;
import org.wirez.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.HasEventHandlers;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.core.client.shape.view.event.MouseClickEvent;
import org.wirez.core.client.shape.view.event.MouseClickHandler;
import org.wirez.core.client.shape.view.event.ViewEventType;
import org.wirez.core.graph.Element;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
@Desktop
public final class SelectionControlImpl extends AbstractSelectionControl {

    @Inject
    public SelectionControlImpl( final Event<CanvasElementSelectedEvent> elementSelectedEventEvent,
                                 final Event<CanvasClearSelectionEvent> clearSelectionEventEvent ) {

        super( elementSelectedEventEvent, clearSelectionEventEvent );

    }

    /*
        **************************************************************
        *               CANVAS CONTROL METHODS
        ***************************************************************
     */

    @Override
    protected void register( final Element element,
                             final Shape<?> shape ) {

        final ShapeView shapeView = shape.getShapeView();

        if ( shapeView instanceof HasEventHandlers ) {

            final HasEventHandlers hasEventHandlers = (HasEventHandlers) shapeView;

            // Click event.
            final MouseClickHandler clickHandler = new MouseClickHandler() {

                @Override
                public void handle(final MouseClickEvent event) {

                    final boolean isSelected = isSelected( element );

                    SelectionControlImpl.super.handleElementSelection( element, isSelected, !event.isShiftKeyDown() );

                }

            };

            hasEventHandlers.addHandler( ViewEventType.MOUSE_CLICK, clickHandler );

            registerHandler( shape.getUUID(), clickHandler );
        }

    }

}
