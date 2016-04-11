package org.wirez.core.api.definition.adapter;

import java.util.Set;

/**
 * A Property Set pojo adapter.. 
 */
public interface PropertySetAdapter<T> extends Adapter<T> {

    /**
     * Returns the property set's identifier for a given pojo.
     */
    String getId(T pojo);

    /**
     * Returns the property set's name for a given pojo.
     */
    String getName(T pojo);

    /**
     * Returns the property set's properties for a given pojo.
     */
    Set<?> getProperties(T pojo);
    
}
