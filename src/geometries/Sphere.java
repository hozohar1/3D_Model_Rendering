package geometries;

import primitives.Point;
import primitives.Vector;
/**

 The Sphere class is a subclass of the RadialGeometry class and represents a 3D sphere in space.
 */
public class Sphere extends RadialGeometry {
    /**

     The center point represents the center of the sphere.
     */
   final private Point center;

    /**

     Creates a new Sphere object with the specified radius and center point.
     @param r The radius of the sphere.
     @param center The center point of the sphere.
     */
    public Sphere(double r, Point center) {
        super(r);
        this.center = center;
        this.radius = r;
    }
    /**

     Returns null as the normal vector to a sphere is not defined at a single point.
     @param p The point at which the normal vector is required.
     @return null
     */
    public Vector getNormal(Point p) {
        return null;
    }

    /**

     Returns the center point of the sphere.
     @return The center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }
    /**

     Overrides the getRadius() method in the superclass to return the radius of the sphere.
     @return The radius of the sphere.
     */
    @Override
    public double getRadius() {
        return radius;
    }
}
