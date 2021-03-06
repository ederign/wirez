package org.wirez.core.definition.adapter;

import org.wirez.core.definition.morph.MorphDefinition;
import org.wirez.core.definition.morph.MorphProperty;

import java.util.Collection;

/**
 * The morphing adapter. 
 */
public interface MorphAdapter<S> extends Adapter {

    /**
     * Returns the morphing definitions for the given Definition instance, if any.
     */
    <T> Iterable<MorphDefinition> getMorphDefinitions( T definition );

    /**
     * Returns the morphing properties for the given Definition instance, if any.
     */
    <T> Iterable<MorphProperty> getMorphProperties( T definition );

    /**
     * Returns the morphing targets for the given Morphing Definition.
     */
    <T> Iterable<String> getTargets( T definition, MorphDefinition morphDefinition );

    /**
     * Performs the morph operation for a diven morph definition and a given target.
     */
    <T> T morph( S source, MorphDefinition definition, String target );

}
