package primitives;

public class Vector extends Point{

    public Vector(double a,double b,double c)throws IllegalArgumentException {
        super(a, b, c);
        if(a==0 && b==0&&c==0)
        {
            throw new IllegalArgumentException("ERROR:Vector cannot be zero");
        }
    }
   Vector(Double3 d) throws IllegalArgumentException{
       super(d.d1, d.d2, d.d3);
       if(d.equals(d.ZERO)==true)//zero vector
       {
           throw new IllegalArgumentException("ERROR:Vector cannot be zero");
       }
    }


 @Override
 public boolean equals(Object obj) {
  //return super.equals(obj);
     if (this == obj) return true;
     if (obj instanceof Point other)
         return this.xyz.equals(other.xyz);
     return false;
 }

 @Override
    public String toString() {
        return super.toString();
    }

    public Vector add(Vector v)
    {
this.xyz.add(v.xyz);
return this;
    }
    public Vector scale(double b)
    {
        this.xyz.scale(b);
        return this;
    }
    public Vector  crossProduct (Vector v)
    {
        double x = this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2;
        double y = this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3;
        double z = this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1;
        Vector result = new Vector(x, y, z);
        return result;
    }
    public double lengthSquared()
    {
return this.xyz.d1*this.xyz.d1+this.xyz.d2*this.xyz.d2+this.xyz.d3*this.xyz.d3;
    }
    public double length()
    {
return Math.sqrt(lengthSquared());
    }
    public Vector normalize()
    {
        if (this.length() == 0) {
            throw new ArithmeticException("Cannot normalize a zero-length vector.");
        }
        return new Vector(this.xyz.d1 / this.length(), this.xyz.d2 / this.length(), this.xyz.d3 / this.length());
    }
    public double dotProduct(Vector v)
    {
        double dotProduct = this.xyz.d1 * v.xyz.d1 +this.xyz.d2 * v.xyz.d2 +this.xyz.d3 * v.xyz.d3;
        return dotProduct;
    }
}
