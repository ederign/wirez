package org.wirez.core.client.shape.animation;

import org.wirez.core.client.animation.Animation;
import org.wirez.core.client.shape.Shape;

public interface ShapeAnimation<S extends Shape> extends Animation<S> {

    ShapeAnimation<S> forShape( S shape );

}
