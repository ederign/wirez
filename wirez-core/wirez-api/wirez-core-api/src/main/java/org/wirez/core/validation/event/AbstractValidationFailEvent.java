package org.wirez.core.validation.event;

import org.wirez.core.validation.ValidationViolation;

public abstract class AbstractValidationFailEvent<E, V extends ValidationViolation> extends AbstractValidationEvent<E> {

    protected final Iterable<V> violations;

    public AbstractValidationFailEvent( final E entity,
                                        final Iterable<V> violations ) {
        super( entity );
        this.violations = violations;
    }

    public Iterable<V> getViolations() {
        return violations;
    }

}
