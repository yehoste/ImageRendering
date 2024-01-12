package primitives;

import static primitives.Util.isZero;

/**
 * The `Ray` class represents a ray in three-dimensional space, with a head point and a direction
 * vector.
 */
public class Ray {

    private final Point head;
    private final Vector direction;
        
    /**
     * constructor for ray.
     * @param Head
     * @param Direction
     */
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

    /**
     * Returns a new point that is the sum of the current point and the given vector scaled by the given
     * parameter. It the equation P(t) = Head ± t * V.
     * 
     * @param t the scalar value to be multiplied with the direction vector
     * @return a new point that is the sum of the current point and the given vector scaled by the given
     *         parameter
     * @throws IllegalArgumentException if the given parameter is negative
     */
    public Point getPoint(double t) {
        if (t < 0) throw new IllegalArgumentException("Negitive is not ok");
        if(isZero(t)) return this.getHead();
        return this.getHead().add(this.getDirection().scale(t));
    }

    @Override
    public String toString()  {
        return "head: " +  head.cords +  "\ndirection: " + direction.cords;
    }
}
