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

import org.wirez.core.api.adapter.*;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.registry.DefinitionSetRegistry;
import org.wirez.core.api.registry.DiagramRegistry;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface DefinitionManager {

    DefinitionSetRegistry getDefinitionSetRegistry();
    
    DiagramRegistry getDiagramRegistry();
    
    DefinitionSetAdapter getDefinitionSetAdapter(Object pojo);
    
    DefinitionSetRuleAdapter getDefinitionSetRuleAdapter(Object pojo);
    
    DefinitionAdapter getDefinitionAdapter(Object pojo);
    
    PropertySetAdapter getPropertySetAdapter(Object pojo);
    
    PropertyAdapter getPropertyAdapter(Object pojo);
    
    
}
