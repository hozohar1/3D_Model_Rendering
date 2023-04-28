package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * The Tube class is a subclass of the RadialGeometry class and represents a 3D tube in space.
 @author hodaya zohar && shoham shervi
 */
public class Tube extends RadialGeometry {
    /**
     * The axisRay represents the axis of the tube.
     */
    final protected Ray axisRay;

    /**
     * Creates a new Tube object with the specified radius and axis ray.
     *
     * @param r        The radius of the tube.
     * @param axisRay The axis ray of the tube.
     */
    public Tube(double r, Ray axisRay) {
        super(r);
        this.axisRay = axisRay;
    }

    /**
     * Returns null as the normal vector to a tube is not defined at a single point.
     *
     * @param p The point at which the normal vector is required.
     * @return null
     */
    public Vector getNormal(Point p) {
        Point p0 = axisRay.getP0();
        Vector vector = axisRay.getDir();
        double t = vector.dotProduct(p.subtract(p0));
        if (t == 0) {
            if (p0.equals(new Point(0, 0, 1)))
                return new Vector(0, 0, 1);
            if (p0.equals(new Point(1, 0, 0)))
                return new Vector(1, 0, 0);
            if (p0.equals(new Point(0, 1, 0)))
                return new Vector(0, 1, 0);
        }
        Point o = p0.add(vector.scale(t));
        return p.subtract(o);

    }

    /**
     * Returns the axis ray of the tube.
     *
     * @return The axis ray of the tube.
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Returns null as there are no intersection points between a tube and a given ray.
     *
     * @param ray The ray to check for intersection with the tube.
     * @return null
     */
    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}

