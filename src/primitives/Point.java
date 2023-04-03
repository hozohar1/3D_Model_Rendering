package primitives;


/**
 * Represents a point in three-dimensional space.
 */

public class Point {
    /** The coordinates of the point. */
   final Double3  xyz;

    /**
     * Constructs a new point with the specified coordinates.
     *
     * @param a the x-coordinate of the point
     * @param b the y-coordinate of the point
     * @param c the z-coordinate of the point
     */
    public Point(double a,double b,double c) {
        this.xyz = new Double3(a, b, c);
    }

    /**
     * Constructs a new point with the specified coordinates.
     *
     * @param d the coordinates of the point
     */
  protected Point(Double3 d) {
this.xyz=d;
    }

    /**
     * Determines whether this point is equal to the specified object.
     *
     * @param obj the object to compare to this point
     * @return true if the specified object is equal to this point, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if(obj==null)
            return false;
        if (!(obj instanceof Point )) {
            return false;
        }
        Point other = (Point) obj;

        return this.xyz.equals(other.xyz);
        //return super.equals(obj);
    }
    /**
     * Returns a string representation of this point.
     *
     * @return a string representation of this point
     */
    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }

    /**
     * Returns a new point that is the result of adding the specified vector to this point.
     *
     * @param v the vector to add to this point
     * @return a new point that is the result of adding the specified vector to this point
     */
    public  Point add(Vector v)
    {
        return new Point(xyz.add(v.xyz));
    }

    /**
     * Returns the vector that represents the difference between this point and the specified point.
     *
     * @param p the point to subtract from this point
     * @return the vector that represents the difference between this point and the specified point
     */
    public Vector subtract(Point p)
    {
        Double3 newXyz = xyz.subtract(p.xyz);
        return new Vector(newXyz);
    }
    /**
     * Returns the distance between this point and the specified point.
     *
     * @param p the point to calculate the distance to
     * @return the distance between this point and the specified point
     */
    public double distance( Point p)
    {
        return Math.sqrt(distanceSquared(p));
    }

    /**
     * Returns the squared distance between this point and the specified point.
     *
     * @param p the point to calculate the squared distance to
     * @return the squared distance between this point and the specified point
     */
    public double distanceSquared(Point p)
    {
        return (((this.xyz.d1-p.xyz.d1)* (this.xyz.d1-p.xyz.d1))+ ((this.xyz.d2-p.xyz.d2)* (this.xyz.d2-p.xyz.d2))+ ((this.xyz.d3-p.xyz.d3)*( this.xyz.d3-p.xyz.d3)));

    }
}
