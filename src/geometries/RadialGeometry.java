package geometries;
/**

 The RadialGeometry class is an abstract class that represents a geometric shape with a radial property.
 */
public abstract class RadialGeometry {
    /**

     The radius represents the radial property of the shape.
     */
    protected double radius;
    /**

     Creates a new RadialGeometry object with the specified radius.
     @param r The radius of the shape.
     */
    public RadialGeometry(double r)
    {
        this.radius=r;
    }
    /**

     Returns the radius of the shape.
     @return The radius of the shape.
     */
    public double getRadius() {
        return radius;
    }
}
