/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.adapter.*;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.registry.DefinitionSetRegistry;
import org.wirez.core.api.registry.DiagramRegistry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.*;

public abstract class BaseDefinitionManager implements DefinitionManager {

    DefinitionSetRegistry definitionSetRegistry;
    DiagramRegistry diagramRegistry;
    
    protected final List<DefinitionSetAdapter> definitionSetAdapters = new ArrayList<DefinitionSetAdapter>();
    protected final List<DefinitionSetRuleAdapter> definitionSetRuleAdapters = new ArrayList<DefinitionSetRuleAdapter>();
    protected final List<DefinitionAdapter> definitionAdapters = new ArrayList<DefinitionAdapter>();
    protected final List<PropertySetAdapter> propertySetAdapters = new ArrayList<PropertySetAdapter>();
    protected final List<PropertyAdapter> propertyAdapters = new ArrayList<PropertyAdapter>();

    public BaseDefinitionManager(final DefinitionSetRegistry definitionSetRegistry, 
                                 final DiagramRegistry diagramRegistry) {
        this.definitionSetRegistry = definitionSetRegistry;
        this.diagramRegistry = diagramRegistry;
    }

    @Override
    public DefinitionSetRegistry getDefinitionSetRegistry() {
        return definitionSetRegistry;
    }

    @Override
    public DiagramRegistry getDiagramRegistry() {
        return diagramRegistry;
    }

    @Override
    public DefinitionSetAdapter getDefinitionSetAdapter(Object pojo) {
        for (DefinitionSetAdapter adapter : definitionSetAdapters) {
            if ( adapter.accepts(pojo) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public DefinitionSetRuleAdapter getDefinitionSetRuleAdapter(Object pojo) {
        for (DefinitionSetRuleAdapter adapter : definitionSetRuleAdapters) {
            if ( adapter.accepts(pojo) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public DefinitionAdapter getDefinitionAdapter(Object pojo) {
        for (DefinitionAdapter adapter : definitionAdapters) {
            if ( adapter.accepts(pojo) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public PropertySetAdapter getPropertySetAdapter(Object pojo) {
        for (PropertySetAdapter adapter : propertySetAdapters) {
            if ( adapter.accepts(pojo) ) {
                return adapter;
            }
        }

        return null;
    }

    @Override
    public PropertyAdapter getPropertyAdapter(Object pojo) {
        for (PropertyAdapter adapter : propertyAdapters) {
            if ( adapter.accepts(pojo) ) {
                return adapter;
            }
        }

        return null;
    }

    public static <T extends Adapter> void sortAdapters(List<T> adapters) {
        Collections.sort(adapters, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });
    }

}

