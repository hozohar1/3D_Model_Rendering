package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * The Plane class implements the Geometry interface and represents a 3D plane in space.
 * @author hodaya zohar && shoham shervi
 */
public class Plane implements Geometry {

    /**
     * The point q0 represents a point on the plane.
     */
    final private Point q0;

    /**
     * The normal vector represents the normal vector to the plane.
     */
    final private Vector normal;

    /**
     * Creates a new Plane object with the specified q0 and normal vector.
     *
     * @param q0     A point on the plane.
     * @param vector The normal vector to the plane.
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
     * @throws IllegalArgumentException when the points are on the same line
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

    /**
     * Computes intersections of the plane with a given ray.
     *
     * @param ray The ray to compute intersections with.
     * @return A list of intersection points (empty if none).
     */
    @Override
    public List<Point> findIntsersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        if (q0.equals(p0)) {
            // The ray's starting point is on the plane
            return null;
        }

        Vector u = q0.subtract(p0);
        double k = alignZero(normal.dotProduct(u));

        if (isZero(k)) {
            // The ray is parallel to the plane
            return null;
        }

        double nv = alignZero(normal.dotProduct(v));

        if (isZero(nv)) {
            // The ray is parallel to the plane
            return null;
        }

        double t = alignZero(k / nv);

        if (t <= 0) {
            // The intersection point is behind the ray's starting point
            return null;
        }

        Point intersection = ray.getPoint(t);

        return List.of(intersection);
    }
}
