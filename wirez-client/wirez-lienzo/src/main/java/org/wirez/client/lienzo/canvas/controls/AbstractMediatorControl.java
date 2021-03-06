package org.wirez.client.lienzo.canvas.controls;

import com.ait.lienzo.client.core.mediator.IMediator;
import com.ait.lienzo.client.core.mediator.Mediators;
import org.wirez.client.lienzo.LienzoLayer;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.controls.AbstractCanvasControl;

public abstract class AbstractMediatorControl<M extends IMediator> extends AbstractCanvasControl {

    protected abstract M buildMediator();

    protected Mediators mediators;
    protected M mediator;
    
    @Override
    public void enable(final AbstractCanvas canvas) {
        super.enable(canvas);
        
        this.mediator = buildMediator();
        final LienzoLayer lienzoLayer = (LienzoLayer) canvas.getLayer();
        mediators = lienzoLayer.getLienzoLayer().getViewport().getMediators();
        mediators.push( getMediator() );
    }

    @Override
    protected void doDisable() {
        mediators.remove( getMediator() );
    }

    public M getMediator() {
        return mediator;
    }
    
}
