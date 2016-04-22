package org.wirez.core.backend.definition.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.api.definition.annotation.definition.Definition;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.util.GraphUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Dependent
public class AnnotatedDefinitionAdapter<T> extends AbstractAnnotatedAdapter<T> implements DefinitionAdapter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedDefinitionAdapter.class);

    GraphUtils graphUtils;

    @Inject
    public AnnotatedDefinitionAdapter(GraphUtils graphUtils) {
        this.graphUtils = graphUtils;
    }

    @Override
    public boolean accepts(Class<?> pojo) {
        return pojo.getAnnotation(Definition.class) != null;
    }

    @Override
    public String getId(T definition) {
        String result = null;

        if ( null != definition ) {
            result = BindableAdapterUtils.getDefinitionId(definition.getClass());
        }

        return result;
    }

    @Override
    public String getCategory(T definition) {
        try {
            return getAnnotatedFieldValue( definition, org.wirez.core.api.definition.annotation.definition.Category.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for Definition with id " + getId( definition ));
        }
        
        return null;
    }

    @Override
    public String getTitle(T definition) {
        try {
            return getAnnotatedFieldValue( definition, org.wirez.core.api.definition.annotation.definition.Title.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated title for Definition with id " + getId( definition ));
        }

        return null;

    }

    @Override
    public String getDescription(T definition) {
        try {
            return getAnnotatedFieldValue( definition, org.wirez.core.api.definition.annotation.Description.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated description for Definition with id " + getId( definition ));
        }

        return null;

    }

    @Override
    public Set<String> getLabels(T definition) {
        try {
            return getAnnotatedFieldValue( definition, org.wirez.core.api.definition.annotation.definition.Labels.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated labels for Definition with id " + getId( definition ));
        }

        return null;
    }

    @Override
    public Set<?> getPropertySets(final T definition) {
        Set<Object> result = null;

        if ( null != definition ) {
            Field[] fields = definition.getClass().getDeclaredFields();
            if ( null != fields ) {
                result = new HashSet<>();
                for (Field field : fields) {
                    org.wirez.core.api.definition.annotation.definition.PropertySet annotation = field.getAnnotation(org.wirez.core.api.definition.annotation.definition.PropertySet.class);
                    if ( null != annotation ) {
                        try {
                            result.add( _getValue( field, annotation, definition) );
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated property sets for Definition with id " + getId( definition ));
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Set<?> getProperties(final T definition) {
        Set<Object> result = null;

        if ( null != definition ) {
            Field[] fields = definition.getClass().getDeclaredFields();
            if ( null != fields ) {
                result = new HashSet<>();
                
                // Obtain all properties from property sets.
                Set<?> propertySetProperties = graphUtils.getPropertiesFromPropertySets( definition );
                if ( null != propertySetProperties ) {
                    result.addAll( propertySetProperties );
                }

                // Find annotated runtime properties on the pojo.
                for (Field field : fields) {
                    org.wirez.core.api.definition.annotation.definition.Property annotation = field.getAnnotation(org.wirez.core.api.definition.annotation.definition.Property.class);
                    if ( null != annotation ) {
                        try {
                            result.add( _getValue( field, annotation, definition) );
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated properties for Definition with id " + getId( definition ));
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private <V> V _getValue( Field field, Object annotation, T definition) throws IllegalAccessException {
        if ( null != annotation) {
            field.setAccessible(true);
            V property = (V) field.get(definition);
            return property;
        }
        return null;
    }

    @Override
    public Class<? extends Element> getGraphElement(T definition) {

        if ( null != definition ) {
            return getGraphElement( definition.getClass() );
        }
        
        return null;
    }

    public static Class<? extends Element> getGraphElement( Class<?> definitionClass ) {

        Class<? extends Element> result = null;

        if ( null != definitionClass ) {
            org.wirez.core.api.definition.annotation.definition.Definition annotation = definitionClass.getAnnotation(org.wirez.core.api.definition.annotation.definition.Definition.class);
            if ( null != annotation ) {
                result = annotation.type();
            }
        }

        return result;
    }

    @Override
    public String getElementFactory(T definition) {
        String result = null;

        if ( null != definition ) {
            org.wirez.core.api.definition.annotation.definition.Definition annotation = definition.getClass().getAnnotation(org.wirez.core.api.definition.annotation.definition.Definition.class);
            if ( null != annotation ) {
                result = annotation.factory();
            }
        }

        return result;
    }

    @Override
    public int getPriority() {
        return 100;
    }
    
}
