package uk.me.webpigeon.util;

import java.util.Random;

import javax.xml.bind.annotation.XmlAttribute;

public final class Vector2D {

    // fields
    @XmlAttribute(name = "x")
    public double x;
    @XmlAttribute(name = "y")
    public double y;

    // default is to make them immutable.
    private boolean mutable = false;

    private static final Random random = new Random();

    /**
     * Create a new immutable vector of size (0,0).
     */
    public Vector2D() {
        x = 0;
        y = 0;
    }

    /**
     * Create a new vector of size (0,0).
     *
     * @param mutable true if vector can be modified
     */
    public Vector2D(boolean mutable) {
        x = 0;
        y = 0;
        this.mutable = mutable;
    }

    /**
     * Create a new immutable vector of a user defined size.
     *
     * @param x the x component
     * @param y the y component
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new vector of a user defined size.
     *
     * @param x       the x component
     * @param y       the y component
     * @param mutable true if vector can be modified
     */
    public Vector2D(double x, double y, boolean mutable) {
        this(x, y);
        this.mutable = mutable;
    }

    /**
     * Create an immutable copy of vector which is a copy of another vector.
     *
     * @param v the vector to copy
     */
    public Vector2D(Vector2D v) {
        this.x = v.getX();
        this.y = v.getY();
    }

    /**
     * Create an immutable copy of vector which is a copy of another vector.
     *
     * @param v       the vector to copy
     * @param mutable true if vector can be modified
     */
    public Vector2D(Vector2D v, boolean mutable) {
        this(v);
        this.mutable = mutable;
    }

    private void setToMutable() {
        this.mutable = true;
    }

    /**
     * Update the position of this vector.
     *
     * @param x the new x position of this vector
     * @param y the new y position of this vector
     * @throws IllegalArgumentException is this is an immutable vector
     */
    public void set(double x, double y) {
        if (mutable) {
            this.x = x;
            this.y = y;
        } else {
            throw new IllegalAccessError("This Vector2D is immutable");
        }
    }

    /**
     * Update the position of this vector.
     *
     * @param v the vector to copy from
     * @throws IllegalArgumentException is this is an immutable vector
     */
    public void set(Vector2D v) {
        if (mutable) {
            set(v.getX(), v.getY());
        } else {
            throw new IllegalAccessError("This Vector2D is immutable");
        }
    }

    // compare for equality (needs to allow for Object type argument...)

    /**
     * Returns whether a provided object is equal to this one
     *
     * @param o the object to compare against
     * @return boolean
     * whether or not o was the same as this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Vector2D vector2D = (Vector2D) o;

        if (Double.compare(vector2D.x, x) != 0)
            return false;
        if (Double.compare(vector2D.y, y) != 0)
            return false;

        return true;
    }

    /**
     * Returns whether the provided object is at least as close to this
     * as provided
     *
     * @param o   the object to compare against
     * @param eps The provided error rate epsilon
     * @return boolean
     * The result - true if they are ~equal
     */
    public boolean roughlyEquals(Object o, float eps) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Vector2D vector2D = (Vector2D) o;

        if (Math.abs(x - vector2D.x) > eps)
            return false;
        if (Math.abs(y - vector2D.y) > eps)
            return false;

        return true;
    }

    /**
     * Returns the hashcode for this object
     *
     * @return int
     * The hashcode result
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = x != +0.0d ? Double.doubleToLongBits(x) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = y != +0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Returns the mathematical magnitude of this
     *
     * @return double
     * The magnitude
     */
    public double mag() {
        return (Math.sqrt(x * x + y * y));
    }

    /**
     * Returns the angle of this vector in respect to (1,0)
     *
     * @return double
     * The angle - in Radians
     */
    public double theta() {
        return (Math.atan2(y, x));
    }

    /**
     * Returns the direction between the two vectors starting from this one
     *
     * @param other The other vector for the comparison
     * @return Vector2D
     * The result of the operation
     */
    public Vector2D getNormalDirectionBetween(Vector2D other) {
        Vector2D direction = new Vector2D(true);
        direction.x = other.x - x;
        direction.y = other.y - y;
        direction.normalise();
        // System.out.println(direction);
        return direction;
    }

    /**
     * Returns the angle between the two Vectors in radians
     *
     * @param v The other vector
     * @return double
     * The angle - In radians
     */
    public double angleBetween(Vector2D v) {
        // return (float)
        // Math.toDegrees(Math.acos(Math.toRadians(scalarProduct(v) / (mag() *
        // v.mag()))));
        // return (float)
        // Math.toDegrees(Vector2D.toPolar(getNormalDirectionBetween(v)).getTheta());
        return Math.toDegrees(theta() - v.theta());
    }

    // String for displaying vector as text
    @Override
    public String toString() {
        return "Vector2D{" + "x=" + x + ", y=" + y + '}';
    }

    // add argument vector

    /**
     * Mathematical add operation
     * @param v
     *          The Vector to add to this one
     */
    public void add(Vector2D v) {
        add(v.getX(), v.getY());
    }

    /**
     * Static mathematical add operation, returning a new Vector2D
     * @param first
     *          The first argument
     * @param second
     *          The second argument
     * @return Vector2D
     *          A unique Vector2D that represents the addition of
     *          the two inputs
     */
    public static Vector2D add(final Vector2D first, final Vector2D second) {
        Vector2D third = new Vector2D(first, true);
        third.add(second);
        return third;
    }

    // add coordinate values

    /**
     * Mathematical add operation with two inputs
     * @param x
     *          The value to be added to X axis
     * @param y
     *          The value to be added to Y axis
     */
    public void add(double x, double y) {
        if (mutable) {
            this.x += x;
            this.y += y;
        } else {
            throw new IllegalAccessError("This Vector2D is immutable");
        }
    }

    /**
     * Static mathematical add operation with two inputs
     * @param first
     *          The starting point to add to
     * @param x
     *          The value to be added to the X axis
     * @param y
     *          The value to be added to the Y axis
     * @return
     *          The unique Vector2D that represents the result of the operation
     */
    public static Vector2D add(final Vector2D first, double x, double y) {
        Vector2D third = new Vector2D(first, true);
        third.add(x, y);
        return third;
    }

    // weighted add - frequently useful
    public void add(Vector2D v, double fac) {
        add(v.getX() * fac, v.getY() * fac);
    }

    public static Vector2D add(final Vector2D first, final Vector2D second,
                               double fac) {
        Vector2D third = new Vector2D(first, true);
        third.add(second.x * fac, second.y * fac);
        return third;
    }

    // subtract argument vector
    public void subtract(Vector2D v) {
        subtract(v.getX(), v.getY());
    }

    public static Vector2D subtract(final Vector2D first, final Vector2D second) {
        Vector2D third = new Vector2D(first, true);
        third.subtract(second);
        return third;
    }

    // subtract coordinate values
    public void subtract(double x, double y) {
        if (mutable) {
            this.x -= x;
            this.y -= y;
        } else {
            throw new IllegalAccessError("This Vector2D is immutable");
        }
    }

    public static Vector2D subtract(final Vector2D first, double x, double y) {
        Vector2D third = new Vector2D(first, true);
        third.subtract(x, y);
        return third;
    }

    // multiply with factor
    public void multiply(double fac) {
        if (mutable) {
            this.x *= fac;
            this.y *= fac;
        } else {
            throw new IllegalAccessError("This Vector2D is immutable");
        }
    }

    public static Vector2D multiply(Vector2D first) {
        return multiply(first, 1.0);
    }

    public static Vector2D multiply(Vector2D first, double fac) {
        Vector2D second = new Vector2D(first, true);
        second.multiply(fac);
        return second;
    }

    public void divide(double fac) {
        // TODO Check for zero division
        if (fac == 0)
            throw new IllegalArgumentException(
                    "Factor is 0 - Can't divide by 0");
        if (mutable) {
            this.x /= fac;
            this.y /= fac;
        } else {
            throw new IllegalAccessError("This Vector2D is immutable");
        }
    }

    public static Vector2D divide(Vector2D first, double fac) {
        Vector2D second = new Vector2D(first, true);
        second.divide(fac);
        second.mutable = first.mutable;
        return second;
    }

    // "wrap" vector with respect to given positive values w and h

    // method assumes that x >= -w and y >= -h

    public void wrap(double w, double h) {
        if (mutable) {
            if (x >= w) {
                x = x % w;
            }
            if (y >= h) {
                y = y % h;
            }
            if (x < 0) {
                x = (x + w) % w;
            }
            if (y < 0) {
                y = (y + h) % h;
            }
        } else {
            throw new IllegalAccessError("This Vector2D is immutable");
        }
    }

    // rotate by angle given in radians
    public void rotate(double theta) {
        set(x * Math.cos(theta) - y * Math.sin(theta), x * Math.sin(theta) + y
                * Math.cos(theta));
    }

    // scalar product with argument vector
    public double scalarProduct(Vector2D v) {
        return ((x * v.getX()) + (y * v.getY()));
    }

    public static Vector2D scalarProduct(Vector2D v1, Vector2D v2) {
        Vector2D vector = new Vector2D(v1, true);
        vector.scalarProduct(v2);
        return vector;
    }

    public static Vector2D toCartesian(Vector2D input) {
        double x = (input.getY() * Math.cos(input.getR()));
        double y = (input.getY() * Math.sin(input.getR()));
        return new Vector2D(x, y, input.mutable);
    }

    public static Vector2D toPolar(Vector2D input) {
        double r = Math.sqrt(input.getX() * input.getX() + input.getY()
                * input.getY());
        double theta = Math.tanh(input.getY() / input.getX());
        return new Vector2D(r, theta, input.mutable);
    }

    // distance to argument vector
    public double dist(Vector2D v) {
        double tempX = (x - v.getX()) * (x - v.getX());
        double tempY = (y - v.getY()) * (y - v.getY());

        return (Math.sqrt(tempX + tempY));
    }

    public void clone(Vector2D target) {
        if (mutable) {
            this.x = target.x;
            this.y = target.y;
        } else {
            throw new IllegalAccessError("This Vector2D is immutable");
        }
    }

    public static void clone(Vector2D target, Vector2D source) {
        if (source.mutable) {
            source.x = target.x;
            source.y = target.y;
        } else {
            throw new IllegalAccessError("This Vector2D is immutable");
        }
    }

    // normalise vector so that mag becomes 1

    // direction is unchanged

    public void normalise() {
        if (mutable) {
            if (x != 0 || y != 0) {
                multiply((1 / mag()));
            }
        } else {
            throw new IllegalAccessError("This Vector2D is immutable");
        }
    }

    public static Vector2D normalise(Vector2D first) {
        Vector2D second = new Vector2D(first, false);
        second.normalise();
        if (first.mutable)
            second.setToMutable();
        return second;
    }

    public double getX() {
        return x;
    }

    public double getR() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getTheta() {
        return y;
    }

    public static Vector2D getRandomCartesian(double xLimit, double yLimit,
                                              boolean mutable) {
        return new Vector2D(random.nextFloat() * xLimit, random.nextFloat()
                * yLimit, mutable);
    }

    public static Vector2D getRandomPolar(double angleRange, double speedMin,
                                          double speedMax, boolean mutable) {
        return new Vector2D(
                random.nextFloat() * angleRange - angleRange / 2,
                (speedMin != speedMax) ? (random.nextFloat() * speedMax - speedMin)
                        + speedMin
                        : speedMax, mutable);
    }
}
