package primitives;

/**
 * The `Ray` class represents a ray in three-dimensional space, with a head point and a direction
 * vector.
 */
public class Ray {

    private final Point head;
    private final Vector direction;
        
    // The code snippet `public Ray(Point Head, Vector Direction) { head = Head; direction =
    // Direction.normalize(); }` is the constructor of the `Ray` class.
    public Ray(Point Head, Vector Direction) {
        head = Head;
        direction = Direction.normalize(); // Normalize the vector
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return ((obj instanceof Ray other)
        && this.head.cords.equals(other.head.cords)
        && this.direction.cords.equals(other.direction.cords));
    }

    public Point getHead() {
        return head;
    }

    public Vector getDirection() {
        return direction;
    }

    public Point getPoint(double t) {
        return this.getHead().add(this.getDirection().scale(t));
    }

    @Override
    public String toString()  {
        return "head: " +  head.cords +  "\ndirection: " + direction.cords;
    }
}
