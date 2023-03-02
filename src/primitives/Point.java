package primitives;

import java.util.Vector;

public class Point {
    Double3 xyz;
    public Point(double a,double b,double c) {
        this.xyz = new Double3(a, b, c);
    }
  Point(Double3 d) {
this.xyz=d;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point other = (Point) obj;
        return this.xyz == other.xyz;
        //return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
    public  Point add(Vector v)
    {
       // this.xyz.add()
       Double3 d=new Double3(this.x + p.x,this.y + p.y,this.z + p.z);
      //  double newX = this.x + p.x;
      //  double newY = this.y + p.y;
       // double newZ = this.z + p.z;
        return this;
    }
    public Vector subtract(Point p)
    {

    }
    public double distance( Point p)
    {
        return Math.sqrt(distanceSquared(p));
    }
    public double distanceSquared(Point p)
    {
        return (this.xyz.d1-p.xyz.d1* this.xyz.d1-p.xyz.d1+ this.xyz.d2-p.xyz.d2* this.xyz.d2-p.xyz.d2+ this.xyz.d3-p.xyz.d3* this.xyz.d3-p.xyz.d3);

    }
}
