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

package org.wirez.core.api.graph.processing.index;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;

import java.util.Collection;
import java.util.List;

/**
 * <p>A generic graph index based on element's identifiers. Allows performing fast look-ups over the graph elements.</p>
 */
public interface Index<N extends Node, E extends Edge> {

    /**
     * Returns the element (node or edge) with the given uuid.
     */
    Element get(String uuid);

    /**
     * Returns the node with the given uuid.
     */
    N getNode(String uuid);

    /**
     * Returns the edge with the given uuid.
     */
    E getEdge(String uuid);

    

}