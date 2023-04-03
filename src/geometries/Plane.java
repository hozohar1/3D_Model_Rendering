package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

import static primitives.Util.*;

/**
 * The Plane class implements the Geometry interface and represents a 3D plane in space.
 */
public class Plane implements Geometry {
    /**
     * The point q0 represents a point on the plane.
     */
    final private Point q0;
    /**
     * The normal vector represents the normal vector to the plane.
     */
    final private  Vector normal;

    /**
     * Creates a new Plane object with the specified q0 and normal vector.
     *
     * @param q0     A point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point q0, Vector vector) {
        this.q0 = q0;
        if (!(isZero(vector.length() - 1d))) {
            this.normal = vector.normalize();
        } else {
            this.normal = vector;
        }
    }

    /**
     * Creates a new Plane object with three points on the plane.
     *
     * @param a A point on the plane.
     * @param b A point on the plane.
     * @param c A point on the plane.
     *           * @throws IllegalArgumentException when the points are on the same line
     *
     */
    public Plane(Point a, Point b, Point c) {
        Vector v1 = b.subtract(a);
        Vector v2 = c.subtract(a);
        normal = v1.crossProduct(v2).normalize();
        q0 = a;
    }

    /**
     * Returns the normal vector to the plane.
     *
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return this.normal;
    }

    /**
     * Returns the normal vector to the plane at a specified point.
     *
     * @param p The point at which the normal vector is required.
     * @return The normal vector to the plane at the specified point.
     */
    public Vector getNormal(Point p) {
        return this.normal;
    }

    /**
     * Returns the point q0 on the plane.
     *
     * @return The point q0 on the plane.
     */
    public Point getQ0() {
        return q0;
    }
}
