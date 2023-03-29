package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 The Tube class is a subclass of the RadialGeometry class and represents a 3D tube in space.
 */
public class Tube extends RadialGeometry {
    /**
     The axisRay represents the axis of the tube.
     */
    final protected Ray axisRay;
    /**

     Creates a new Tube object with the specified radius and axis ray.
     @param r The radius of the tube.
     @param axisRay The axis ray of the tube.
     */
    public Tube(double r, Ray axisRay) {
        super(r);
        this.axisRay = axisRay;
    }
    /**

     Returns null as the normal vector to a tube is not defined at a single point.
     @param p The point at which the normal vector is required.
     @return null
     */
    public Vector getNormal(Point p) {
return null;
    }
    /**

     Returns the axis ray of the tube.
     @return The axis ray of the tube.
     */
    public Ray getAxisRay() {
        return axisRay;
    }
}
