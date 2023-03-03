package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**

 Represents a cylinder in 3D space, which is a type of tube with a specific height.
 */
public class Cylinder extends  Tube{
    private  double height;
    /**

     Constructs a new cylinder with the specified radius, axis ray, and height.
     @param r the radius of the cylinder.
     @param axisRay the axis ray of the cylinder.
     @param height the height of the cylinder.
     */
    public Cylinder(double r, Ray axisRay, double height) {
        super(r,axisRay);
        this.height = height;
    }


    /**

     Returns the height of the cylinder.
     @return the height of the cylinder.
     */
    public double getHeight() {
        return height;
    }
}
