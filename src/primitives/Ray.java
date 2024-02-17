package primitives;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import geometries.Intersectable.GeoPoint;

/**
 * The `Ray` class represents a ray in three-dimensional space, with a head point and a direction
 * vector.
 */
public class Ray {
    
    /**
     * The starting point of the ray.
     */
    private final Point head;

    /**
     * The direction vector of the ray.
     */
    private final Vector direction;

    private static final double DELTA = 0.1;

        
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

    public Ray(Point head, Vector direction, Vector normal) {
        double nv = alignZero(direction.dotProduct(normal));
        if (nv > 0)
            this.head = head.add(normal.scale(DELTA));
        else if (nv < 0)
            this.head = head.add((normal.scale(-DELTA)));
        else
            this.head = head;
        this.direction = direction.normalize();
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


    /**
     * return the point which is the closset point to the ray head
     * @param ClusterPoints
     * @return
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
        : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }
       

    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null ||points.isEmpty()) {
            return null;
        }
        GeoPoint closestPoint = points.get(0);
        double closestPointDistance = this.head.distance(points.get(0).point);
        for (int i = 1; i < points.size(); i++) {
            if (this.head.distance(points.get(i).point) < closestPointDistance) {
                closestPoint = points.get(i);
                closestPointDistance = this.head.distance(points.get(i).point);
            }
        }
        return closestPoint;
    }
}
