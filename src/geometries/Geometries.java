package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.*;
/**@author hodaya zohar && shoham shervi  */
public class Geometries extends Intersectable {

    private final List<Intersectable> geometries = new LinkedList<>();

    /**
     * constructor for list of geometries
     *
     * @param geometries list of shapes of all kinds
     */
    public Geometries(Intersectable... geometries) {
        this.add(geometries);
    }

    /**
     * adds geometries to list
     *
     * @param geometries list of shapes of all kinds
     */
    public void add(Intersectable... geometries) {
        if (geometries.length != 0)
            this.geometries.addAll(List.of(geometries));
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable item : geometries) {
            List<GeoPoint> itemResult = item.findGeoIntersectionsHelper(ray, maxDistance);
            if (itemResult != null) {
                if (result == null)
                    result = new LinkedList<>(itemResult);
                else
                    result.addAll(itemResult);
            }
        }
        return result;
    }
}
