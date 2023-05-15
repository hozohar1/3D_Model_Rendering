package primitives;

import java.util.List;

/**

 The Ray class represents a ray in 3D space, defined by an origin point and a direction vector.
 */
public class Ray {

    // The origin point of the ray.
   final private Point p0;
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Ray other))
            return false;
        return this.dir.equals(other.dir) && this.p0.equals(other.p0);
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
    public Point getPoint(double t){
       Point p=this.p0.add(this.dir.scale(t));
       return p;
    }
    /**
     * @param lst The list of all the points.
     * @return The closest point to p0 in the list.
     */
    public Point findClosestPoint(List<Point> lst) {
        if (lst == null || lst.size() == 0) return null;

        Point closest = lst.get(0);
        double closestDistance = p0.distanceSquared(closest); // To make the calculations more efficient.
        double tmpDist;
        for (Point point : lst) {
            tmpDist = p0.distanceSquared(point); // To make the calculations more efficient.
            if (tmpDist < closestDistance) {
                closest = point;
                closestDistance = tmpDist;
            }
        }
        return closest;
    }

}
