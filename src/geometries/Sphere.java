package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Sphere class is a subclass of the RadialGeometry class and represents a 3D sphere in space.
 *
 * @author hodaya zohar && shoham shervi
 */
public class Sphere extends Geometry {
    final private Point center;
    final private double radius;
    final private double radiusSqr;

    /**
     * constructor for sphere by point and radius
     *
     * @param center point
     * @param radius radius of sphere
     */
    public Sphere(double radius, Point center) {
        this.center = center;
        this.radius = radius;
        this.radiusSqr = radius * radius;
    }

    /**
     * getting center
     *
     * @return center of sphere
     */
    public Point getCenter() {
        return center;
    }

    /**
     * getting radius
     *
     * @return radius of sphere
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector u;
        try {
            u = center.subtract(p0);
        } catch (IllegalArgumentException ignore) {
            return alignZero(radius - maxDistance) > 0 ? null : List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        double tm = alignZero(v.dotProduct(u));
        double dSqr = alignZero(u.lengthSquared() - tm * tm);
        double thSqr = radius * radius - dSqr;
        // no intersections : the ray direction is above the sphere
        if (alignZero(thSqr) <= 0) return null;

        double th = alignZero(Math.sqrt(thSqr));

        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        if (t1 <= 0) {
            if (alignZero(t2 - maxDistance) <= 0)
                return List.of(new GeoPoint(this, ray.getPoint(t2)));
            return null;
        } else {
            List<GeoPoint> result = new LinkedList<>();
            if (alignZero(t1 - maxDistance) <= 0)
                result.add(new GeoPoint(this, ray.getPoint(t1)));
            if (alignZero(t2 - maxDistance) <= 0)
                result.add(new GeoPoint(this, ray.getPoint(t2)));
            return result.isEmpty() ? null : result;
        }
    }
}