package primitives;

/**

 The Ray class represents a ray in 3D space, defined by an origin point and a direction vector.
 */
public class Ray {

    // The origin point of the ray.
    private Point p0;
    // The direction vector of the ray.
   private Vector dir;

    /**

     Constructs a new Ray object with the specified origin point and direction vector.
     The direction vector is normalized before being stored.
     @param p0 the origin point of the ray
     @param dir the direction vector of the ray
     */
    public Ray(Point p0,Vector dir) {
        this.dir=dir.normalize();
        this.p0 = p0;
    }

    /**

     Checks whether this Ray object is equal to the specified object.
     Two Ray objects are equal if they have the same origin point and direction vector.
     @param obj the object to compare to
     @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ray)) {
            return false;
        }
        Ray other = (Ray) obj;
        return this.dir == other.dir &&this.p0 == other.p0;
    }

    /**

     Returns a string representation of this Ray object.
     @return a string representation of this Ray object
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
    /**

     Returns the origin point of this Ray object.
     @return the origin point of this Ray object
     */
    public Point getP0() {
        return p0;
    }

    /**

     Returns the direction vector of this Ray object.
     @return the direction vector of this Ray object
     */
    public Vector getDir() {
        return dir;
    }
}