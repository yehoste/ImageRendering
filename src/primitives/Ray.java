package primitives;

public class Ray {

    private Point head;
    private Vector direction;
    
    public Ray(Point Head, Vector Direction) {
        head = Head;
        direction = Direction.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof Ray other)
        && this.head.cords.equals(other.head.cords)
        && this.direction.cords.equals(other.direction.cords));
    }

    @Override
    public String toString()  {
        return "head: " +  head.cords +  "\ndirection: " + direction.cords;
    }
}
