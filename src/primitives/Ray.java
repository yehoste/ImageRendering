package primitives;

/**
 * The `Ray` class represents a ray in three-dimensional space, with a head point and a direction
 * vector.
 */
public class Ray {

    private Point head;
    private Vector direction;
        
    // The code snippet `public Ray(Point Head, Vector Direction) { head = Head; direction =
    // Direction.normalize(); }` is the constructor of the `Ray` class.
    public Ray(Point Head, Vector Direction) {
        head = Head;
        direction = Direction.normalize();
    }

    /**
     * The equals() function checks if two Ray objects have the same head and direction coordinates.
     * 
     * @param obj The `obj` parameter is the object that we are comparing with the current object for
     * equality.
     * @return The method is returning a boolean value.
     */
    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof Ray other)
        && this.head.cords.equals(other.head.cords)
        && this.direction.cords.equals(other.direction.cords));
    }

    /**
     * The toString() function returns a string representation of the head and direction coordinates.
     * 
     * @return The method is returning a string representation of the head and direction coordinates.
     */
    @Override
    public String toString()  {
        return "head: " +  head.cords +  "\ndirection: " + direction.cords;
    }
}
