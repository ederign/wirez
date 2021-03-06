package org.wirez.basicset.shape.proxy.icon.dynamics;

import org.wirez.basicset.definition.icon.dynamics.DynamicIcon;
import org.wirez.shapes.proxy.AbstractBasicDynamicShapeProxy;
import org.wirez.shapes.proxy.AbstractBasicDynamicShapeProxy;
import org.wirez.shapes.proxy.icon.dynamics.IconProxy;

public abstract class AbstractDynamicIconProxy<I extends DynamicIcon>
        extends AbstractBasicDynamicShapeProxy<I>
        implements IconProxy<I> {
    
    @Override
    public double getWidth( final I element ) {
        return element.getWidth().getValue();
    }

    @Override
    public double getHeight( final I element ) {
        return element.getHeight().getValue();
    }

    @Override
    public String getBackgroundColor( final I element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final I element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final I element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

}
