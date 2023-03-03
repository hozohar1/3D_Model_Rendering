package geometries;

import primitives.Point;
/**

 The Triangle class is a subclass of the Polygon class and represents a 3D triangle in space.
 */
public class Triangle extends Polygon {

    /**

     Creates a new Triangle object with the specified vertices.
     @param vertices The vertices of the triangle in 3D space.
     */
    public Triangle(Point... vertices) {
        super(vertices);

    }
}
