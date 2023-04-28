/**

 The Intersectable interface represents an object in 3D space that can be intersected by a ray.
 Implementing this interface allows an object to be tested for intersection with a given ray.
 @author hodaya zohar && shoham shervi

 */
package geometries;

import primitives.*;

import java.util.List;

public interface Intersectable {
    /**
     * Returns a list of intersection points between this object and the given ray.
     *
     * @param ray the ray to test for intersection with this object
     * @return a list of intersection points, or null if there are no intersections
     */
    List<Point> findIntsersections(Ray ray);
}