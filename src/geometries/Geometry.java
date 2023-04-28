package geometries;

import primitives.Point;
import primitives.Vector;
/**

 The Geometry interface represents a geometric shape or object

 and defines a method to get the normal vector at a specific point on the surface.
 @author hodaya zohar && shoham shervi
 */
public interface Geometry extends Intersectable {
    /**

     Returns the normal vector at the given point on the surface of this Geometry.
     @param p the point on the surface for which to retrieve the normal vector
     @return the normal vector at the given point
     */
    public Vector getNormal(Point p);
}