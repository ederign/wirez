package org.wirez.core.client.canvas.controls.toolbox.command;

import org.wirez.core.client.components.toolbox.ToolboxButton;
import org.wirez.core.graph.Element;
import org.wirez.core.client.canvas.CanvasHandler;

public interface ToolboxCommand<H extends CanvasHandler, I> {

    enum Status {
        ENABLED, DISABLED;
    }
    
    I getIcon( double width, double height );

    String getTitle();

    ToolboxButton.HoverAnimation getButtonAnimation();

    // TODO: add method -> Status check( Element element );
    
    void execute( Context<H> context, Element element );
    
    void destroy();
    
}
