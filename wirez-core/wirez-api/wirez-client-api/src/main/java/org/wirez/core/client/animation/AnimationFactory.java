package org.wirez.core.client.animation;

public interface AnimationFactory<T, A extends Animation> {

    A newAnimation( T type );

}
