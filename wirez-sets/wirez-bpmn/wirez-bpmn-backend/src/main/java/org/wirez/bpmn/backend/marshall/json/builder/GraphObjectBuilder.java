package org.wirez.bpmn.backend.marshall.json.builder;


import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.factory.GraphCommandFactory;
import org.wirez.core.graph.content.definition.DefinitionSet;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.rule.RuleViolation;

import java.util.Collection;

public interface GraphObjectBuilder<W, T extends Element<View<W>>> {
    
    GraphObjectBuilder<W, T> nodeId(String nodeId);

    GraphObjectBuilder<W, T> stencil(String stencilId);

    GraphObjectBuilder<W, T> property(String key, String value);

    GraphObjectBuilder<W, T> out(String nodeId);

    GraphObjectBuilder<W, T> boundUL(Double x, Double y);

    GraphObjectBuilder<W, T> boundLR(Double x, Double y);

    GraphObjectBuilder<W, T> docker(Double x, Double y);
    
    T build(BuilderContext context);
    
    interface BuilderContext {

        void init(Graph<DefinitionSet, Node> graph);

        Graph<DefinitionSet, Node> getGraph();
        
        Collection<GraphObjectBuilder<?, ?>> getBuilders();

        DefinitionManager getDefinitionManager();
        
        FactoryManager getFactoryManager();
        
        GraphUtils getGraphUtils();
        
        Bpmn2OryxManager getOryxManager();

        GraphCommandFactory getCommandFactory();
        
        CommandResult<RuleViolation> execute (Command<GraphCommandExecutionContext, RuleViolation> command);
        
    }
    
}
