package org.wirez.core.api.graph.command;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.RuleManager;

/**
 * The context beans for the execution of graph commands.
 */
public interface GraphCommandExecutionContext {
    
    DefinitionManager getDefinitionManager();
    
    GraphUtils getGraphUtils();
    
    FactoryManager getFactoryManager();
    
    RuleManager getRuleManager();

    GraphCommandFactory getCommandFactory();
    
}