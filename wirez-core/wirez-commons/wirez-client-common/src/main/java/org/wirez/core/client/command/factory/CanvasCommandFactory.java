package org.wirez.core.client.command.factory;

import org.wirez.core.client.command.impl.*;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.definition.morph.MorphDefinition;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.content.view.View;

public interface CanvasCommandFactory {
    
    AddCanvasNodeCommand ADD_NODE(Node candidate, ShapeFactory factory);
    
    AddCanvasEdgeCommand ADD_EDGE(Node parent, Edge candidate, ShapeFactory factory);
    
    DeleteCanvasNodeCommand DELETE_NODE(Node candidate);
    
    DeleteCanvasEdgeCommand DELETE_EDGE(Edge candidate);

    DrawCanvasCommand DRAW();

    ClearCanvasCommand CLEAR_CANVAS();
    
    AddCanvasChildEdgeCommand ADD_CHILD_EDGE(Node parent,
                                             Node candidate);
    
    DeleteCanvasChildEdgeCommand DELETE_CHILD_EDGE(Node parent,
                                                   Node candidate);
    
    AddCanvasParentEdgeCommand ADD_PARENT_EDGE(Node parent,
                                               Node candidate);
    
    DeleteCanvasParentEdgeCommand DELETE_PARENT_EDGE(Node parent,
                                                     Node candidate);

    AddCanvasDockEdgeCommand ADD_DOCK_EDGE(Node parent,
                                               Node candidate);

    DeleteCanvasDockEdgeCommand DELETE_DOCK_EDGE(Node parent,
                                                     Node candidate);

    UpdateCanvasElementPositionCommand UPDATE_POSITION(Element element,
                                                       Double x,
                                                       Double y);
    
    UpdateCanvasElementPropertyCommand UPDATE_PROPERTY(Element element,
                                                       String propertyId,
                                                       Object value);

    UpdateCanvasElementPropertiesCommand UPDATE_PROPERTIES(Element element);
    
    AddCanvasChildNodeCommand ADD_CHILD_NODE(Node parent, Node candidate, ShapeFactory factory);

    AddCanvasDockedNodeCommand ADD_DOCKED_NODE(Node parent, Node candidate, ShapeFactory factory);
    
    SetCanvasConnectionSourceNodeCommand SET_SOURCE_NODE(Node<? extends View<?>, Edge> node,
                                                         Edge<? extends View<?>, Node> edge,
                                                         int magnetIndex);
    
    SetCanvasConnectionTargetNodeCommand SET_TARGET_NODE(Node<? extends View<?>, Edge> node,
                                                         Edge<? extends View<?>, Node> edge,
                                                         int magnetIndex);

    MorphCanvasNodeCommand MORPH_NODE( Node<? extends Definition<?>, Edge> candidate,
                                       MorphDefinition morphDefinition,
                                       String morphTarget,
                                       ShapeFactory factory );
    
}
