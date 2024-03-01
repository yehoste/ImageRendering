package primitives;
import java.lang.Math;

/**
 * The Point class represents a point in three-dimensional space and provides methods for performing
 * operations on points.
 */
public class Point {

    protected final Double3 cords;

    /**
     * The constructor initializes the x, y, and z coordinates of the point.
     * 
     * @param x the x-coordinate of the point.
     * @param y the y-coordinate of the point.
     * @param z the z-coordinate of the point.
     */
    public Point(double x,double y,double z){
        cords = new Double3(x,y,z);
    }

    public double getX() {
        return cords.d1;
    }

    public double getY() {
        return cords.d2;
    }

    public double getZ() {
        return cords.d3;
    }

    
    /**
     * Returns the ith component of the point.
     * 
     * @param i the index of the component to return (0, 1, or 2)
     * @return the ith component of the point
     * @throws IllegalArgumentException if i is not 0, 1, or 2
     */
    public double get(int i) {
        switch (i) {
            case 0:
                return cords.d1;
            case 1:
                return cords.d2;
            case 2:
                return cords.d3;
            default:
                throw new IllegalArgumentException("Invalid value for cord");
        }
    }

    /**
     * Returns the axis along which this vector has the largest absolute value.
     *
     * @return The index (0, 1, or 2) representing the axis with the largest absolute value.
     *         - 0: X-axis
     *         - 1: Y-axis
     *         - 2: Z-axis
     */
    public int getAxis() {
        // Find the absolute values of each component (X, Y, Z)
        double absX = Math.abs(getX());
        double absY = Math.abs(getY());
        double absZ = Math.abs(getZ());
    
        // Find the index (0, 1, or 2) corresponding to the largest absolute value
        int largestIndex = 0;
        if (absY > absX) {
            largestIndex = 1;
        }
        if (absZ > absY) {
            largestIndex = 2;
        }
    
        return largestIndex;
    }

    /**
     * Constructor for Point using a Double3 object as input.
     * @param cord
     */
    private Point(Double3 cord){
        cords = cord;
    }

    // The line `public static final Point ZERO = new Point(0, 0, 0);` is creating a constant variable
    // named `ZERO` of type `Point`. This constant represents a point in three-dimensional space with coordinates (0, 0, 0).
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * The function subtracts the coordinates of a given point from the coordinates of the current point
     * and returns a new vector.
     * 
     * @param ourPoint The parameter `ourPoint` is of type `Point`.
     * @return A new Vector object is being returned.
     */
    public Vector subtract(Point ourPoint){
        return new Vector(this.cords.subtract(ourPoint.cords));
    }

    /**
     * The add function takes a Vector as input and returns a new Point object with the coordinates of the
     * sum of the input Vector and the current Point object.
     * 
     * @param v1 A Vector object that represents the vector to be added to the current Point object.
     * @return The method is returning a new Point object.
     */
    public Point add(Vector v1) {
        return new Point(v1.cords.add(this.cords));
    }

    /**
     * The function calculates the squared distance between two points in three-dimensional space.
     * (x₂ - x₁)² + (y₂ - y₁)² + (z₂ - z₁)²
     * 
     * @param thatPoint thatPoint is an instance of the Point class, representing another point in
     * three-dimensional space.
     * @return The method is returning the squared distance between the current point and the given point.
     */
    public double distanceSquared(Point thatPoint){
        return(((this.cords.d1-thatPoint.cords.d1)*(this.cords.d1-thatPoint.cords.d1))+
                ((this.cords.d2-thatPoint.cords.d2)*(this.cords.d2-thatPoint.cords.d2))+
                ((this.cords.d3-thatPoint.cords.d3)*(this.cords.d3-thatPoint.cords.d3))
        );
    }

    /**
     * The function calculates the distance between two points using the distance formula.
     * √(return from distanceSquared) because Distance between two points in 3D space is: 
     * d = √((x₂ - x₁)² + (y₂ - y₁)² + (z₂ - z₁)²). 
     * And (x₂ - x₁)² + (y₂ - y₁)² + (z₂ - z₁)² provided by the distanceSquared func.
     * 
     * @param thatPoint The parameter "thatPoint" is of type "Point". It represents another point in a
     * two-dimensional space.
     * @return The method is returning the distance between the current point and the specified point.
     */
    public double distance(Point thatPoint){
        return Math.sqrt(this.distanceSquared(thatPoint));
    }


    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        return ((obj instanceof Point CheckIfEqual) &&
                (this.cords.equals(CheckIfEqual.cords)));
    }

    @Override
    public String toString() {
        return "Point:" + cords;
    }


}
