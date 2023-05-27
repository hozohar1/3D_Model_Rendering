package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Triangle class is a subclass of the Polygon class and represents a 3D triangle in space.
 @author hodaya zohar && shoham shervi
 */
public class Triangle extends Polygon {

    /**
     * Creates a new Triangle object with the specified vertices.
     * @param vertices The vertices of the triangle in 3D space.
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }

    /**
     * Finds the intersection points of a ray with the triangle.
     *
     * @param ray The ray to intersect with the triangle.
     * @return A list of intersection points with the triangle, or null if the ray doesn't intersect the triangle.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = plane.findGeoIntersectionsHelper(ray);

        //Check if the ray intersect the plane.
        if (result == null) return null;

        for (GeoPoint g : result)
            g.geometry = this;

        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector n1 = v1.crossProduct(v2).normalize();
        double vn1 = alignZero(v.dotProduct(n1));
        if (vn1 == 0) return null;

        Vector v3 = vertices.get(2).subtract(p0);
        Vector n2 = v2.crossProduct(v3).normalize();
        double vn2 = alignZero(v.dotProduct(n2));
        if (vn1 * vn2 <= 0) return null;

        Vector n3 = v3.crossProduct(v1).normalize();
        double vn3 = alignZero(v.dotProduct(n3));
        if (vn1 * vn3 <= 0) return null;

        return result;
    }
}
