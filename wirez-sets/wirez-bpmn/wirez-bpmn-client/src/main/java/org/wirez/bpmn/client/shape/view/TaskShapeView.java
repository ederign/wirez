package org.wirez.bpmn.client.shape.view;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.SVGPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.bpmn.client.shape.TaskShape;
import org.wirez.client.shapes.view.RectangleView;

public class TaskShapeView extends RectangleView<TaskShapeView> implements TaskShape.TaskView<TaskShapeView> {

    private Group taskTypeIcon;
    
    public TaskShapeView(final double width, final double height, final WiresManager manager) {
        super(width, height, manager);
    }

    @Override
    protected Shape<?> createChildren() {
        final Shape<?> s = super.createChildren();
        taskTypeIcon = taskTypeUser();
        this.addChild(taskTypeIcon, WiresLayoutContainer.Layout.CENTER);
        return s;
    }

    @Override
    protected void doMoveChildren(final double width, 
                                  final double height) {
        super.doMoveChildren(width, height);
        this.moveChild( taskTypeIcon, getChildCoordinate( width ), getChildCoordinate( height ) );
    }

    private Group taskTypeUser() {

        final String p1 = "M0.585,24.167h24.083v-7.833c0,0-2.333-3.917-7.083-5.167h-9.25\n" +
                "\t\t\tc-4.417,1.333-7.833,5.75-7.833,5.75L0.585,24.167z";

        final String p2 = "M 6 20 L 6 24";

        final String p3 = "M 20 20 L 20 24";

        final String p4 = "M8.043,7.083c0,0,2.814-2.426,5.376-1.807s4.624-0.693,4.624-0.693\n" +
                "\t\t\tc0.25,1.688,0.042,3.75-1.458,5.584c0,0,1.083,0.75,1.083,1.5s0.125,1.875-1,3s-5.5,1.25-6.75,0S8.668,12.834,8.668,12\n" +
                "\t\t\ts0.583-1.25,1.25-1.917C8.835,9.5,7.419,7.708,8.043,7.083z";

        final Group userTypeGroup = new Group();

        final SVGPath svgP1 = createSVGPath(p1);
        userTypeGroup.add(svgP1);
        final SVGPath svgP2 = createSVGPath(p2);
        userTypeGroup.add(svgP2);
        final SVGPath svgP3 = createSVGPath(p3);
        userTypeGroup.add(svgP3);
        final SVGPath svgP4 = createSVGPath(p4);
        userTypeGroup.add(svgP4);

        return userTypeGroup.setDraggable( false );
    }

    private SVGPath createSVGPath(String path) {
        return new SVGPath(path)
                .setStrokeColor(ColorName.BLACK)
                .setDraggable(false);
    }


    @Override
    public TaskShapeView setTaskType(final TaskType type) {
        // Only one type supported for now.
        return null;
    }
    
}
