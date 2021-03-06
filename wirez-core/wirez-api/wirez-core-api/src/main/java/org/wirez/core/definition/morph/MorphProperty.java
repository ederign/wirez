package org.wirez.core.definition.morph;

import java.util.Collection;

public interface MorphProperty<V> {

    String getProperty();

    String getMorphTarget( V value );

    Collection<String> getMorphTargets();

}
