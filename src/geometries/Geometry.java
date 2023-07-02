package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometric shape or object
 * <p>
 * and defines a method to get the normal vector at a specific point on the surface.
 *
 * @author hodaya zohar && shoham shervi
 */
public abstract class Geometry extends Intersectable {
    /**
     * the color of the geometry
     */
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * getter for material
     *
     * @return the material of the geometry object
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * setter for material
     *
     * @param material the material of the geometry object
     * @return the geometry object
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * the function get point on the geometry and return the vector normal to the geometry surface at the point
     *
     * @param p The point on the geometry
     * @return The vector normal
     */
    public abstract Vector getNormal(Point p);

    /**
     * getter for emission
     *
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter for emission
     *
     * @param emission the emission color
     * @return the geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }


}