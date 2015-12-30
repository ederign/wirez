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

package org.wirez.core.client.canvas.command.impl;


import org.wirez.core.api.graph.impl.DefaultEdge;
import org.wirez.core.api.graph.impl.DefaultNode;
import org.wirez.core.client.factory.ShapeFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultCanvasCommands {

    public ClearCanvasCommand CLEAR() {
        return new ClearCanvasCommand( );
    }

    public AddCanvasNodeCommand ADD_NODE(final DefaultNode node, final ShapeFactory factory ) {
        return new AddCanvasNodeCommand( node, factory );
    }

    public DeleteCanvasNodeCommand DELETE_NODE(final DefaultNode node) {
        return new DeleteCanvasNodeCommand( node );
    }
    
    public AddCanvasEdgeCommand ADD_EDGE(final DefaultEdge edge, final ShapeFactory factory ) {
        return new AddCanvasEdgeCommand( edge, factory );
    }

    public DeleteCanvasEdgeCommand DELETE_EDGE(final DefaultEdge edge) {
        return new DeleteCanvasEdgeCommand( edge );
    }


}
