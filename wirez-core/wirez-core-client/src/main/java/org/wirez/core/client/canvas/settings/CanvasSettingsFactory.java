package org.wirez.core.client.canvas.settings;

import org.wirez.core.api.command.CommandManager;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.index.IndexBuilder;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.control.ConnectionAcceptor;
import org.wirez.core.client.canvas.control.ContainmentAcceptor;
import org.wirez.core.client.canvas.wires.WiresCanvasHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The default canvas handler's settings producer.
 */
@ApplicationScoped
public class CanvasSettingsFactory {
    
    IncrementalIndexBuilder indexBuilder;
    CommandManager<WiresCanvasHandler, CanvasCommandViolation> commandManager;
    RuleManager ruleManager;
    ConnectionAcceptor<WiresCanvasHandler> connectionAcceptor;
    ContainmentAcceptor<WiresCanvasHandler> containmentAcceptor;

    @Inject
    public CanvasSettingsFactory(final IncrementalIndexBuilder indexBuilder,
                                 final CommandManager<WiresCanvasHandler, CanvasCommandViolation> commandManager,
                                 final @Named( "default" ) RuleManager ruleManager, 
                                 final ConnectionAcceptor<WiresCanvasHandler> connectionAcceptor,
                                 final ContainmentAcceptor<WiresCanvasHandler> containmentAcceptor) {
        this.indexBuilder = indexBuilder;
        this.commandManager = commandManager;
        this.ruleManager = ruleManager;
        this.connectionAcceptor = connectionAcceptor;
        this.containmentAcceptor = containmentAcceptor;
    }
    
    public CanvasViewSettings getViewSettings() {
        return new CanvasViewSettingsBuilderImpl()
                .indexBuilder( (IndexBuilder<?, ?, ? ,?>) indexBuilder)
                .build();
    }

    public WiresCanvasSettings getDefaultSettings() {
        return new WiresCanvasSettingsBuilderImpl()
                .indexBuilder( (IndexBuilder<?, ?, ? ,?>) indexBuilder)
                .commandManager(commandManager)
                .ruleManager(ruleManager)
                .connectionAcceptor(connectionAcceptor)
                .containmentAcceptor(containmentAcceptor)
                .build();
    }
    
}