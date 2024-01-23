package primitives;

/**
 * The Vector class represents a vector in three-dimensional space and provides methods for vector
 * operations such as normalization, addition, scaling, dot product, cross product, and calculating the
 * length of the vector.
 */
public class Vector extends Point {

    public static final Vector Y = new Vector(0,1,0);
    /**
     * Creates a new Vector object with the specified coordinates.
     *
     * @param d1 the x-coordinate
     * @param d2 the y-coordinate
     * @param d3 the z-coordinate
     * @throws IllegalArgumentException if the zero vector is specified
     */
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        //cords.equals(Double3.ZERO)
        if(cords.equals(Double3.ZERO)) throw new IllegalArgumentException("the zero vector not ok");
    }

   /**
     * Creates a new Vector object with the specified coordinates.
     *
     * @param ObjD3 the Double3 object containing the coordinates
     */
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
        double normal = this.distance(Point.ZERO); // find the len of the vector
        // Dividing all the cords by the len, and return the vector 
        return new Vector(this.cords.reduce(normal)); 
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
    public Vector scale(double d1) { // privet for ex 2+
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
     * It using the equation for cross product of two vectors:
     * (a₂b₃ - a₃b₂) is the x-component
     * (a₃b₁ - a₁b₃) is the y-component
     * (a₁b₂ - a₂b₁) is the z-component
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
     * It from the mathematical property: v * v = v^2 => |v|^2 (length Squared)
     * 
     * @return The method `lengthSquared()` returns the sum of the squares of the three coordinates (`d1`,
     * `d2`, and `d3`).
     */
    public double lengthSquared() {
        return ((this.cords.d1 * this.cords.d1) + 
                (this.cords.d2 * this.cords.d2) + 
                (this.cords.d3 * this.cords.d3));
    }     

    /**
     * The function calculates the length of a vector by taking the square root of the sum of its squared
     * components.
     * 
     * @return The method is returning the *square root of the length squared*.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return ((obj instanceof Vector other)
        && this.cords.equals(other.cords));
    }

    @Override
    public String toString() {
        return "vector:" + super.toString();   
    }

}
