package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

import static primitives.Util.alignZero;

/**
 * The Sphere class is a subclass of the RadialGeometry class and represents a 3D sphere in space.
 @author hodaya zohar && shoham shervi
 */
public class Sphere extends RadialGeometry {
    /**
     * The center point represents the center of the sphere.
     */
    final private Point center;

    /**
     * Creates a new Sphere object with the specified radius and center point.
     *
     * @param r      The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double r, Point center) {
        super(r);
        this.center = center;
        this.radius = r;
    }

    /**
     * Returns null as the normal vector to a sphere is not defined at a single point.
     *
     * @param p The point at which the normal vector is required.
     * @return null
     */
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return The center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Overrides the getRadius() method in the superclass to return the radius of the sphere.
     *
     * @return The radius of the sphere.
     */
    @Override
    public double getRadius() {
        return radius;
    }

    /**
     * Finds the intersections of the specified ray with the sphere.
     *
     * @param ray The ray with which the sphere is intersected.
     * @return A list of intersection points with the sphere.
     */
    public List<Point> findIntsersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector u;
        try {
            u = center.subtract(p0);
        } catch (IllegalArgumentException ignore) {
            return List.of(ray.getPoint(radius));
        }

        double tm = alignZero(v.dotProduct(u));
        double dSqr = alignZero(u.lengthSquared() - tm * tm);
        if (dSqr >= radius) {
            return null;
        }
        double thSqr = radius * radius - dSqr;
        // no intersections : the ray direction is above the sphere
        if (alignZero(thSqr) <= 0) return null;

        double th = alignZero(Math.sqrt(thSqr));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 > 0 && t2 > 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(p1, p2);
        }
        if (t1 > 0) {
            Point p1 = ray.getPoint(t1);
            return List.of(p1);
        }
        if (t2 > 0) {
            Point p2 = ray.getPoint(t2);
            return List.of(p2);
        }
        return null;

    }
}
