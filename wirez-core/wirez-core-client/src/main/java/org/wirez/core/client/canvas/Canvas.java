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

package org.wirez.core.client.canvas;

import com.ait.lienzo.client.core.shape.Layer;
import org.wirez.core.client.Shape;

import java.util.List;

public interface Canvas {

    /**
     * Initialize a wirez layer.
     */
    Canvas initialize(Layer layer);

    /**
     * Draws or batches the updates on the canvas. 
     */
    Canvas draw();

    /**
     * Get a list of all Shapes on the Canvas
     * @return
     */
    List<Shape> getShapes();

    /**
     * Returns the shape with the given identifier.
     */
    Shape getShape(String id);

    /**
     * Add a Shape to the Canvas
     * @param shape
     */
    Canvas addShape(Shape shape);

    /**
     * Delete a Shape from the Canvas. Implementations may prompt the User for confirmation.
     * @param shape
     */
    Canvas deleteShape(Shape shape);

    /**
     * Clears the canvas. 
     */
    Canvas clear();

    /**
     * Shape's available states.
     */
    public enum ShapeState {
        SELECTED, DESELECTED;
    }
}
