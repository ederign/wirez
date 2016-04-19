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
package org.wirez.core.api.graph.command.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandResultBuilder;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import java.util.Collection;

/**
 * A Command to delete an edge from a graph
 */
@Portable
public final class DeleteEdgeCommand extends AbstractGraphCommand {

    private Edge<? extends View, Node> edge;
    private Node parent;

    public DeleteEdgeCommand(@MapsTo("edge") Edge<? extends View, Node> edge) {
        this.edge = PortablePreconditions.checkNotNull( "edge",
                edge );;
    }
    
    @Override
    public CommandResult<RuleViolation> allow(final GraphCommandExecutionContext context) {
        return check( context );
    }

    @Override
    public CommandResult<RuleViolation> execute(final GraphCommandExecutionContext context) {
        final CommandResult<RuleViolation> results = check( context );
        if ( !results.getType().equals(CommandResult.Type.ERROR) ) {
            
            final Node outNode = edge.getTargetNode();
            final Node inNode = edge.getSourceNode();

            this.parent = inNode;
            
            if ( null != outNode ) {
                outNode.getInEdges().remove( edge );
            }
            if ( null != inNode ) {
                inNode.getOutEdges().remove( edge );
            }

        }
        return results;
    }
    
    @SuppressWarnings("unchecked")
    private CommandResult<RuleViolation> check(final GraphCommandExecutionContext context) {
        final Collection<RuleViolation> cardinalityRuleViolations = 
                (Collection<RuleViolation>) context.getRuleManager().checkCardinality( edge.getTargetNode(), edge.getSourceNode(), 
                        (Edge<? extends View<?>, ? extends Node>) edge, RuleManager.Operation.DELETE).violations();
        return new GraphCommandResultBuilder( cardinalityRuleViolations ).build();
    }

    @Override
    public CommandResult<RuleViolation> undo(GraphCommandExecutionContext context) {
        final AddEdgeCommand undoCommand = new AddEdgeCommand( parent, edge );
        return undoCommand.execute( context );
    }

    @Override
    public String toString() {
        return "DeleteEdgeCommand [edge=" + edge.getUUID() + "]";
    }
}
