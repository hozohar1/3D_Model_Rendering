package primitives;

public class Ray {
    private Point p0;
   private Vector dir;

    public Ray(Point p0,Vector dir) {
        this.dir=dir/dir.length();
        this.p0 = p0;
    }


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

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }
}
