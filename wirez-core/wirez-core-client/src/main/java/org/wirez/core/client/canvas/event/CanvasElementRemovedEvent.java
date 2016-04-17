package org.wirez.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.CanvasHandler;

@NonPortable
public final class CanvasElementRemovedEvent extends AbstractCanvasHandlerElementEvent {

    public CanvasElementRemovedEvent(final CanvasHandler canvasHandler,
                                     final Element<?> element) {
        super(canvasHandler, element);
    }
    
}
