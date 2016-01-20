package org.wirez.core.api.adapter.impl;

import org.wirez.core.api.adapter.DefinitionAdapter;
import org.wirez.core.api.definition.DefaultDefinition;
import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.factory.ElementFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class DefaultDefinitionAdapter implements DefinitionAdapter<DefaultDefinition> {
    
    @Override
    public Set<PropertySet> getPropertySets(final DefaultDefinition pojo) {
        Set<PropertySet> result = null;
        if ( null != pojo ) {
            result = pojo.getPropertySets();
        }
        
        return result;
    }

    @Override
    public Set<Property> getProperties(final DefaultDefinition pojo) {
        Set<Property> result = null;
        if ( null != pojo ) {
            result = pojo.getProperties();
        }

        return result;
    }

    @Override
    public Map<Property, Object> getPropertiesValues(final DefaultDefinition pojo) {
        Map<Property, Object> result = null;
        if ( null != pojo ) {
            final Set<Property> properties = pojo.getProperties();
            if ( null != properties && !properties.isEmpty() ) {
                result = new HashMap<>(properties.size());
                for (final Property property : properties) {
                    HasValue hasValue = (HasValue) property;
                    result.put(property, hasValue.getValue());
                }
            }
        }

        return result;
    }

    @Override
    public Class<? extends Element> getGraphElementType(final DefaultDefinition pojo) {
        // TODO
        return null;
    }

    @Override
    public ElementFactory getElementFactory(final DefaultDefinition pojo) {
        // TODO
        return null;
    }

    @Override
    public boolean accepts(final Class pojoClass) {
        return pojoClass.equals(DefaultDefinition.class); 
    }
    
}
