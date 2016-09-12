package org.wirez.core.client.shape;

/**
 * The available shape view's states on the canvas.
 */
public enum ShapeState {

    SELECTED( "#0000FF" ), DESELECTED,
    HIGHLIGHT( "#3366CC" ), UNHIGHLIGHT,
    INVALID( "#FF0000" );

    private String color;

    ShapeState() {
        this( null );
    }

    ShapeState( final String color ) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
