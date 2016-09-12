package org.wirez.core.client.shape;

public interface MutationContext {
    
    enum Type {
        STATIC, ANIMATION;
    }
    
    Type getType();

    MutationContext STATIC = new StaticContext();
    MutationContext ANIMATED = new AnimationContext();

    class StaticContext implements MutationContext {

        @Override
        public Type getType() {
            return Type.STATIC;
        }
        
    }
    
    class AnimationContext implements MutationContext {

        @Override
        public Type getType() {
            return Type.ANIMATION;
        }

    }
    
}
