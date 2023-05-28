/**

 The Intersectable interface represents an object in 3D space that can be intersected by a ray.
 Implementing this interface allows an object to be tested for intersection with a given ray.
 @author hodaya zohar && shoham shervi

 */
package geometries;

import primitives.*;

import java.util.List;

public abstract class Intersectable {
    /**
     * find all intersection {@link Point}s
     * that intersect with a specific {@link Ray}
     *
     * @param ray ray pointing towards the graphic object
     * @return list of intersection {@link Point}s
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /** find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray} in a range of distance
     * @param ray ray pointing towards the graphic object
     * @param maxDistance the maximum distance between the point to the start of the ray
     * @return immutable list of intersection geo points {@link GeoPoint}
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /** find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray}
     * @param ray ray pointing towards the graphic object
     * @return immutable list of intersection geo points {@link GeoPoint}
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * helper of findGeoIntersections
     * @param ray ray pointing towards the graphic object
     * @param maxDistance the maximum distance between the point to the start of the ray
     * @return immutable list of intersection geo points {@link GeoPoint}
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
    /**
     * geo point is the point with the geometry object that it's on
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * constructor for GeoPoint
         *
         * @param geometry the geometry object
         * @param point    the point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}