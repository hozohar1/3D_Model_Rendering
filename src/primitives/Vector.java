package primitives;

/**
 * A class representing a mathematical vector in 3D space.
 */
public class Vector extends Point {
    /**
     * X axis unit vector
     */
    public static final Vector X = new Vector(1, 0, 0);

    /**
     * Y axis unit vector
     */
    public static final Vector Y = new Vector(0, 1, 0);

    /**
     * Z axis unit vector
     */
    public static final Vector Z = new Vector(0, 0, 1);

    /**
     * Creates a new vector with the given x, y, and z components.
     *
     * @param a The x component of the vector.
     * @param b The y component of the vector.
     * @param c The z component of the vector.
     * @throws IllegalArgumentException if the vector has zero length.
     */
    public Vector(double a, double b, double c) {
        this(new Double3(a, b, c));
    }

    /**
     * Creates a new vector with the given Double3 object.
     *
     * @param d The Double3 object.
     * @throws IllegalArgumentException if the vector has zero length.
     */
    Vector(Double3 d) {
        super(d);
        if (d.equals(d.ZERO) == true)//zero vector
        {
            throw new IllegalArgumentException("ERROR:Vector cannot be zero");
        }
    }

    /**
     * Subtracts another vector from this vector and returns the result as a new vector.
     *
     * @param v The vector to subtract.
     * @return The result of the subtraction as a new vector.
     */
    public Vector subtract(Vector v) {
        Double3 newXyz = xyz.subtract(v.xyz);
        return new Vector(newXyz);
    }

    /**
     * Checks if this vector is equal to another object.
     *
     * @param obj The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        //super.equals(obj);
        if (this == obj) return true;
        if (obj instanceof Vector other) return this.xyz.equals(other.xyz);
        return false;
    }

    /**
     * Returns a string representation of this vector.
     *
     * @return A string representation of this vector.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Adds another vector to this vector and returns the result as a new vector.
     *
     * @param v The vector to add.
     * @return The result of the addition as a new vector.
     * @throws IllegalArgumentException if the resulting vector has zero length.
     */
    public Vector add(Vector v) {
        return new Vector(this.xyz.add(v.xyz));
    }

    /**
     * Scales this vector by a given scalar and returns the result.
     *
     * @param b The scalar to scale the vector by.
     * @return The scaled vector.
     */
    public Vector scale(double b) {
        return new Vector(xyz.scale(b));
    }

    /**
     * Calculates the cross product of this vector and another vector and returns the result as a new vector.
     *
     * @param v The vector to compute the cross product with.
     * @return The cross product of this vector and the given vector.
     */
    public Vector crossProduct(Vector v) {
        double x = this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2;
        double y = this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3;
        double z = this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1;
        Vector result = new Vector(x, y, z);
        return result;
    }

    /**
     * Computes the squared length of this vector.
     *
     * @return the squared length of this vector.
     */
    public double lengthSquared() {
        return this.xyz.d1 * this.xyz.d1 + this.xyz.d2 * this.xyz.d2 + this.xyz.d3 * this.xyz.d3;
    }

    /**
     * Computes the length of this vector.
     *
     * @return the length of this vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns a new vector that is the normalized version of this vector.
     * If this vector has zero length, an ArithmeticException will be thrown.
     *
     * @return the normalized vector.
     * @throws ArithmeticException if this vector has zero length.
     */
    public Vector normalize() {
        if (this.equals(Double3.ZERO) == true) {
            throw new ArithmeticException("Cannot normalize a zero-length vector.");
        } else {
            return new Vector(xyz.reduce(this.length()));//Vector(this.xyz.d1 / this.length(), this.xyz.d2 / this.length(), this.xyz.d3 / this.length());
        }
    }

    /**
     * Computes the dot product of this vector with another vector.
     *
     * @param v the vector to compute the dot product with.
     * @return the dot product of this vector with v.
     */
    public double dotProduct(Vector v) {
        double dotProduct = this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 + this.xyz.d3 * v.xyz.d3;
        return dotProduct;
    }

    /**
     * The function construct new vector after rotation calculating
     * with consideration of axis vector and rotation angle.
     *
     * @param axis     of rotation
     * @param thetaRad of rotation in radians
     * @return new rotated vector
     */
    public Vector vectorRotate(Vector axis, double thetaRad) {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();

        double u = axis.getX();
        double v = axis.getY();
        double w = axis.getZ();

        double v1 = u * x + v * y + w * z;

        double xPrime = u * v1 * (1d - Math.cos(thetaRad))
                + x * Math.cos(thetaRad)
                + (-w * y + v * z) * Math.sin(thetaRad);

        double yPrime = v * v1 * (1d - Math.cos(thetaRad))
                + y * Math.cos(thetaRad)
                + (w * x - u * z) * Math.sin(thetaRad);

        double zPrime = w * v1 * (1d - Math.cos(thetaRad))
                + z * Math.cos(thetaRad)
                + (-v * x + u * y) * Math.sin(thetaRad);

        return new Vector(xPrime, yPrime, zPrime);
    }
}
