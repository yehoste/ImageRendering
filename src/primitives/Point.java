package primitives;
import java.lang.Math;

/**
 * The Point class represents a point in three-dimensional space and provides methods for performing
 * operations on points.
 */
public class Point {
    protected Double3 cords;

    // The `public Point(double x,double y,double z)` constructor is creating a new `Point` object with the
    // specified coordinates `(x, y, z)`. It initializes the `cords` variable with a new `Double3` object
    // that holds the coordinates `(x, y, z)`.
    public Point(double x,double y,double z){
        cords = new Double3(x,y,z);
    }

    // The `protected Point(Double3 cord)` constructor is a protected constructor that creates a new
    // `Point` object with the specified `Double3` object as its coordinates. It is used to create a new
    // `Point` object from an existing `Double3` object. The `cords` variable is initialized with the
    // `Double3` object passed as the parameter.
    protected Point(Double3 cord){
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
     * 
     * @param thatPoint The parameter "thatPoint" is of type "Point". It represents another point in a
     * two-dimensional space.
     * @return The method is returning the distance between the current point and the specified point.
     */
    public double distance(Point thatPoint){
        return Math.sqrt(this.distanceSquared(thatPoint));
    }

    /**
     * The function checks if two Point objects are equal by comparing their coordinates.
     * 
     * @param Check The parameter "Check" is an Object, which means it can accept any type of object as an
     * argument. In this case, it is being casted to a Point object inside the method.
     * @return The method is returning a boolean value, which indicates whether the two objects being
     * compared are equal or not.
     */
    @Override
    public boolean equals(Object Check){
        Point CheckIfEqual=(Point) Check;
        return ((CheckIfEqual.cords.d1==this.cords.d1) &&
                (CheckIfEqual.cords.d2==this.cords.d2) &&
                (CheckIfEqual.cords.d3==this.cords.d3));
    }


}
