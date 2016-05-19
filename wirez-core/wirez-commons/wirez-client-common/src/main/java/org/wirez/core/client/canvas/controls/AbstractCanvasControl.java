package org.wirez.core.client.canvas.controls;

import org.wirez.core.client.canvas.AbstractCanvas;

public abstract class AbstractCanvasControl implements CanvasControl<AbstractCanvas> {

    protected AbstractCanvas canvas;

    protected abstract void doDisable();

    @Override
    public void enable(final AbstractCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void disable() {
        doDisable();
        this.canvas = null;
    }
    
}
