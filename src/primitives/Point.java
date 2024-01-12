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
