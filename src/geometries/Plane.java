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
public class Plane extends Geometry {

    final private Point q0;
    final private Vector normal;

    /**
     * constructor for plane by point and vector
     *
     * @param point  point
     * @param vector vector of normal
     */
    public Plane(Point point, Vector vector) {
        normal = vector.normalize();
        q0 = point;
    }

    /**
     * constructor for plane by 3 points
     *
     * @param point1 first point in the plane
     * @param point2 second point in the plane
     * @param point3 third point in the plane
     * @throws IllegalArgumentException when the points are on the same line
     */
    public Plane(Point point1, Point point2, Point point3) {
        q0 = point1;
        Vector v1 = point2.subtract(point1);
        Vector v2 = point3.subtract(point1);
        normal = v1.crossProduct(v2).normalize();
    }

    /**
     * getting point in the plane
     *
     * @return this point
     */
    public Point getQ0() {
        return this.q0;
    }

    /**
     * getting normal
     *
     * @return normal of plane
     */
    public Vector getNormal() {
        return this.normal;
    }

    @Override
    public Vector getNormal(Point p) {
        return this.normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector u;
        try {
            u = q0.subtract(p0);
        } catch (IllegalArgumentException ignore) {
            return null;
        }

        double nv = normal.dotProduct(v);
        //ray parallel to plane or ray begins in the same point which appears as the plane's reference point
        if (isZero(nv)) return null;

        double t = alignZero(normal.dotProduct(u) / nv);
        return t > 0 && alignZero(t - maxDistance) <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t))) : null;
    }
}
