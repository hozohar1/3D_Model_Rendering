package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * Represents a cylinder in 3D space, which is a type of tube with a specific height.
 * @author hodaya zohar && shoham shervi
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder.
     */
    private double height;
    /**
     * Constructs a new cylinder with the specified radius, axis ray, and height.
     *
     * @param r        the radius of the cylinder.
     * @param axisRay  the axis ray of the cylinder.
     * @param height   the height of the cylinder.
     */
    public Cylinder(double r, Ray axisRay, double height) {
        super( r,axisRay);
        this.height = height;
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return the height of the cylinder.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Calculates the normal vector to the cylinder at a given point.
     *
     * @param p the point at which the normal vector is required.
     * @return the normal vector to the cylinder at the specified point.
     */
    @Override
    public Vector getNormal(Point p) {
        // Calculate the vector from the point P0 to the point we got
        if (axisRay.getP0().equals(p)) {
            return axisRay.getDir();
        }
        Vector v = p.subtract(axisRay.getP0());

        // We will do a scalar multiplication between the vector of the line and the vector we got.
        // And because the vector of the line is normalized, the result will be the projection of
        // our vector on the line
        double d = alignZero(v.dotProduct(axisRay.getDir()));
        if (d == 0) {
            return axisRay.getDir();
        }
        if (d < height && d > -height) {
            return super.getNormal(p);
        }

        if (d >= height) {
            return axisRay.getDir().normalize();
        }

        if (d <= -height) {
            return axisRay.getDir().scale(-1).normalize();
        }

        return null; // impossible case
    }
}
