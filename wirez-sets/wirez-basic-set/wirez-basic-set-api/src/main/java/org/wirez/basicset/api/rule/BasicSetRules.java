/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.basicset.api.rule;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.rule.CardinalityRule;
import org.wirez.core.api.rule.ConnectionRule;
import org.wirez.core.api.rule.ContainmentRule;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.rule.impl.*;

import java.util.Collection;
import java.util.HashSet;

/**
 * The following rules are just for development purposes. The basic set is not intended to have any semantic rules.
 */
@Portable
public class BasicSetRules {

    public BasicSetRules() {
    }

    // *******************************************************************************************************
    // Containment rules 
    // *******************************************************************************************************

    public Collection<ContainmentRule> CONTAINMENT_RULES = new HashSet<ContainmentRule>() {{

        // A diagram can contain any element.
        add( new DefaultContainmentRule("diagramContainmentRule", "diagram", new HashSet<String>() {{
                add("all");
            }}
        ));
        
        // A rectangle can only be parent of rectangles.
        add( new DefaultContainmentRule("rectangleContainmentRule", "rectangle", new HashSet<String>() {{
                add("rectangle");
            }}
        ));
        
    }};
    
    // *******************************************************************************************************
    // Connection rules
    // *******************************************************************************************************

    public Collection<ConnectionRule> CONNECTION_RULES = new HashSet<ConnectionRule>() {{

        // Allow connections only between rectangles <-> circles.
        add(new DefaultConnectionRule("connectorConnectionRule", "connector",
                new HashSet<ConnectionRule.PermittedConnection>() {{
                    add(new DefaultPermittedConnection("circle", "rectangle"));
                    add(new DefaultPermittedConnection("rectangle", "circle"));
                }}
        ));

    }};

    // *******************************************************************************************************
    // Cardinality rules
    // *******************************************************************************************************

    public Collection<CardinalityRule> CARDINALITY_RULES = new HashSet<CardinalityRule>() {{

        // Rectangles are only allowed to have one outgoing connector..
        add(new DefaultCardinalityRule("rectangleCardinalityRule", "rectangle", 0l, -1l,
                new HashSet<CardinalityRule.ConnectorRule>() {{
                   
                }}, 
                new HashSet<CardinalityRule.ConnectorRule>() {{
                    add(new DefaultConnectorRule(0l, 1l, "connector", ""));
                }}));

    }};

    public Collection<Rule> getRules() {
        final Collection<Rule> rules = new HashSet<>();
        rules.addAll( CONNECTION_RULES );
        rules.addAll( CARDINALITY_RULES );
        rules.addAll( CONTAINMENT_RULES );
        return rules;
    }
    
}
