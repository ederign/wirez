package org.wirez.core.client.command.impl;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Element;

public abstract class AddCanvasElementCommand<E extends Element> extends AbstractCanvasGraphCommand {
    
    protected E candidate;
    protected ShapeFactory factory;

    public AddCanvasElementCommand(final E candidate, final ShapeFactory factory) {
        this.candidate = candidate;
        this.factory = factory;
    }

    @Override
    public CommandResult<CanvasViolation> doExecute(final AbstractCanvasHandler context) {
        doRegister(context);
        doMutate(context);
        return buildResult();
    }
    
    protected void doRegister(final AbstractCanvasHandler context) {
        context.register( factory, candidate );
    }

    protected void doMutate(final AbstractCanvasHandler context) {
        context.applyElementMutation( candidate, MutationContext.ANIMATED );
    }
    
}
