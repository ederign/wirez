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

package org.wirez.basicset.api;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.factory.DefaultNodeFactory;
import org.wirez.core.api.graph.impl.DefaultEdge;
import org.wirez.core.api.graph.impl.DefaultNode;
import org.wirez.core.api.graph.impl.DefaultNodeImpl;

import java.util.Map;
import java.util.Set;

public abstract class BasicNodeDefinition<W extends Definition> extends BasicDefinition implements DefaultNodeFactory<W> {

    public BasicNodeDefinition(@MapsTo("id") String id) {
        super(id);
    }

    @Override
    public DefaultNode<W, DefaultEdge> build(String uuid, Set<String> labels, Map<String, Object> properties, Bounds bounds) {
        return new DefaultNodeImpl<W>(uuid, (W) this, buildElementProperties(properties), buildElementLabels(labels), bounds);
    }
}