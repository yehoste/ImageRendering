package primitives;

/**
 * The Vector class represents a vector in three-dimensional space and provides methods for vector
 * operations such as normalization, addition, scaling, dot product, cross product, and calculating the
 * length of the vector.
 */
public class Vector extends Point {

    //throws IllegalArgumentException
    // The `public Vector(double d1, double d2, double d3)` constructor is creating a new Vector object
    // with the given coordinates `d1`, `d2`, and `d3`. It calls the superclass constructor `super(d1,
    // d2, d3)` to set the coordinates of the Vector object.
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        //cords.equals(Double3.ZERO)
        if(cords.equals(Double3.ZERO)) throw new IllegalArgumentException("the zero vector not ok");
    }

    // The `public Vector(Double3 ObjD3)` constructor is creating a new Vector object using the coordinates
    // from a Double3 object. It calls the superclass constructor `super(ObjD3.d1, ObjD3.d2, ObjD3.d3)` to
    // set the coordinates of the Vector object.
    public Vector(Double3 ObjD3) {
        super(ObjD3.d1, ObjD3.d2, ObjD3.d3);
        if(cords.equals(Double3.ZERO)) throw new IllegalArgumentException("the zero vector not ok");
    }

    /**
     * The normalize function normalizes the vector by dividing each coordinate by the magnitude of the
     * vector.
     * 
     * @return The method is returning the normalized vector.
     */
    public Vector normalize(){
        double normal = this.distance(Point.ZERO);
        return new Vector(this.cords.d1/normal,this.cords.d2/normal,this.cords.d3/normal);
    }

    /**
     * The add function takes a Vector object as input and returns a new Vector object that is the sum of
     * the current Vector object and the input Vector object.
     * 
     * @param v1 The parameter `v1` is a Vector object that is being passed into the `add` method.
     * @return The method is returning a new Vector object.
     */
    public Vector add(Vector v1) {
        return new Vector(this.cords.add(v1.cords));
    }

    /**
     * The function scales the coordinates of a vector by a given factor.
     * 
     * @param d1 The parameter `d1` is a double value that represents the scaling factor.
     * @return The method is returning a new Vector object.
     */
    public Vector scale(double d1) {
        return new Vector(this.cords.scale(d1));
    }

    /**
     * Returns the dot product of this vector and another vector.
     * 
     * @param v1 the other vector
     * @return the dot product of this vector and the other vector
     */
    public double dotProduct(Vector v1) {
        return (this.cords.d1 * v1.cords.d1 + this.cords.d2 * v1.cords.d2 + this.cords.d3 * v1.cords.d3);
    }

    /**
     * The crossProduct function calculates the cross product of two vectors.
     * 
     * @param v1 The parameter `v1` is a Vector object that represents the second vector in the cross
     * product calculation.
     * @return The method is returning a new Vector object.
     */
    public Vector crossProduct(Vector v1) {
        return new Vector(  (this.cords.d2 * v1.cords.d3 - this.cords.d3 * v1.cords.d2),
                            (this.cords.d3 * v1.cords.d1 - this.cords.d1 * v1.cords.d3), 
                            (this.cords.d1 * v1.cords.d2 - this.cords.d2 * v1.cords.d1));
    }

    /**
     * The function calculates the squared length of a vector in three-dimensional space.
     * 
     * @return The method `lengthSquared()` returns the sum of the squares of the three coordinates (`d1`,
     * `d2`, and `d3`).
     */
    public double lengthSquared() {
        return  ((this.cords.d1 * this.cords.d1) + 
                (this.cords.d2 * this.cords.d2) + 
                (this.cords.d3 * this.cords.d3));
    }     

    /**
     * The function calculates the length of a vector by taking the square root of the sum of its squared
     * components.
     * 
     * @return The method is returning the square root of the length squared.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * The equals() function checks if two objects are equal by comparing their coordinates.
     * 
     * @param obj The `obj` parameter is an object that we are comparing for equality with the current
     * object.
     * @return The method is returning a boolean value.
     */
    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof Vector other)
        && this.cords.equals(other.cords));
    }

    /**
     * The toString() function returns a string representation of the vector object.
     * 
     * @return The method is returning a string representation of the vector, which includes the word
     * "vector:" followed by the coordinates of the vector.
     */
    @Override
    public String toString() {
        return "vector:" + cords;   
    }

}
