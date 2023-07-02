package geometries;

import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hodaya zohar && shoham shervi
 */
public class Geometries extends Intersectable {

    private final List<Intersectable> geometries = new LinkedList<>();

    private final List<Intersectable> infinites = new LinkedList<>();

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

    /**
     * adds geometries to the list
     *
     * @param geometries the geomtries to add
     */
    public void add(List<Intersectable> geometries) {
        if (!cbr) {
            this.geometries.addAll(geometries);
            return;
        }

        for (var g : geometries) {
            if (g.box == null)
                infinites.add(g);
            else {
                this.geometries.add(g);
                if (infinites.isEmpty()) {
                    if (box == null)
                        box = new Border();
                    if (g.box.minX < box.minX)
                        box.minX = g.box.minX;
                    if (g.box.minY < box.minY)
                        box.minY = g.box.minY;
                    if (g.box.minZ < box.minZ)
                        box.minZ = g.box.minZ;
                    if (g.box.maxX > box.maxX)
                        box.maxX = g.box.maxX;
                    if (g.box.maxY > box.maxY)
                        box.maxY = g.box.maxY;
                    if (g.box.maxZ > box.maxZ)
                        box.maxZ = g.box.maxZ;
                }
            }
        }
        // if there are inifinite objects
        if (!infinites.isEmpty())
            box = null;
    }


    /**
     * create the hierarchy and put into the right boxes
     */
    public void setBVH() {
        if (!cbr)
            return;
        // min amount of geometries in a box is 2
        if (geometries.size() <= 4)
            return;

        if (box == null) {
            var finites = new Geometries((Intersectable) geometries);
            geometries.clear();
            geometries.add(finites);
            return;
        }

        double x = box.maxX - box.minX;
        double y = box.maxY - box.minY;
        double z = box.maxZ - box.minZ;
        // which axis we are reffering to
        final char axis = y > x && y > z ? 'y' : z > x && z > y ? 'z' : 'x';
//		Collections.sort(geometries, //
//				(i1, i2) -> Double.compare(average(i1, axis), average(i2, axis)));

        var l = new Geometries();
        var m = new Geometries();
        var r = new Geometries();
        double midX = (box.maxX + box.minX) / 2;
        double midY = (box.maxY + box.minY) / 2;
        double midZ = (box.maxZ + box.minZ) / 2;
        switch (axis) {
            case 'x':
                for (var g : geometries) {
                    if (g.box.minX > midX)
                        r.add(g);
                    else if (g.box.maxX < midX)
                        l.add(g);
                    else
                        m.add(g);
                }
                break;
            case 'y':
                for (var g : geometries) {
                    if (g.box.minY > midY)
                        r.add(g);
                    else if (g.box.maxY < midY)
                        l.add(g);
                    else
                        m.add(g);
                }
                break;
            case 'z':
                for (var g : geometries) {
                    if (g.box.minZ > midZ)
                        r.add(g);
                    else if (g.box.maxZ < midZ)
                        l.add(g);
                    else
                        m.add(g);
                }
                break;
        }

        geometries.clear();
        if (l.geometries.size() <= 2)
            geometries.addAll(l.geometries);
        else {
            l.setBVH();
            geometries.add(l);
        }

        if (m.geometries.size() <= 2)
            geometries.addAll(m.geometries);
        else
            geometries.add(m);

        if (r.geometries.size() <= 2)
            geometries.addAll(r.geometries);
        else {
            r.setBVH();
            geometries.add(r);
        }
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