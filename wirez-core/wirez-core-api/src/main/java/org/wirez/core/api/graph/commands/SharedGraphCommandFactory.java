package org.wirez.core.api.graph.commands;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.adapter.PropertyAdapter;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.util.PropertyUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class SharedGraphCommandFactory implements GraphCommandFactory {

    DefinitionManager definitionManager;

    @Inject
    public SharedGraphCommandFactory(DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    @Override
    public AddChildNodeCommand addChildNodeCommand(final DefaultGraph target,
                                                   final Node parent,
                                                   final Node candidate) {
        return new AddChildNodeCommand(this, target, parent, candidate);
    }

    @Override
    public AddEdgeCommand addEdgeCommand(DefaultGraph target, Edge edge) {
        return new AddEdgeCommand(this, target, edge);
    }

    @Override
    public AddNodeCommand addNodeCommand(final DefaultGraph target,
                                         final Node candidate) {
        return new AddNodeCommand(this, target, candidate);
    }

    @Override
    public ClearGraphCommand clearGraphCommand(DefaultGraph target) {
        return new ClearGraphCommand(this, target);
    }

    @Override
    public DeleteEdgeCommand deleteEdgeCommand(final DefaultGraph<? extends Definition, Node, Edge> graph,
                                               final Edge<? extends ViewContent, Node> edge) {
        return new DeleteEdgeCommand(this, graph, edge);
    }

    @Override
    public DeleteNodeCommand deleteNodeCommand(final DefaultGraph target,
                                               final Node candidate) {
        return new DeleteNodeCommand(this, target, candidate);
    }

    @Override
    public SetConnectionSourceNodeCommand setConnectionSourceNodeCommand(final Node<? extends ViewContent<?>, Edge> sourceNode,
                                                                         final Edge<? extends ViewContent<?>, Node> edge) {
        return new SetConnectionSourceNodeCommand(this, sourceNode, edge);
    }

    @Override
    public SetConnectionTargetNodeCommand setConnectionTargetNodeCommand(final Node<? extends ViewContent<?>, Edge> targetNode,
                                                                         final Edge<? extends ViewContent<?>, Node> edge) {
        return new SetConnectionTargetNodeCommand(this, targetNode, edge);
    }

    @Override
    public UpdateElementPositionCommand updateElementPositionCommand(final Element element,
                                                                     final Double x,
                                                                     final Double y) {
        return new UpdateElementPositionCommand(this, element, x ,y);
    }

    @Override
    public UpdateElementPropertyValueCommand updateElementPropertyValueCommand(final Element element,
                                                                               final String propertyId,
                                                                               final Object value) {
        final Property p = PropertyUtils.getProperty(element.getProperties(), propertyId);
        PropertyAdapter adapter = getPropertyAdapter(p);
        return new UpdateElementPropertyValueCommand(this, adapter, element, propertyId, value);
    }
    
    
    protected PropertyAdapter getPropertyAdapter(final Property property) {
        return definitionManager.getPropertyAdapter(property.getClass());
    }
    
}
