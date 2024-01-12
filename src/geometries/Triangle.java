package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

import java.util.List;

/**
 * A triangle is a polygon with 3 sides and 3 angles.
 */
public class Triangle extends Polygon {

    /**
     * Creates a new triangle with the given points.
     *
     * @param point1 the first point of the triangle
     * @param point2 the second point of the triangle
     * @param point3 the third point of the triangle
     */
    public Triangle(Point point1, Point point2, Point point3) {
        super(point1, point2, point3);
    }

    @Override
    public Vector getNormal(Point p) {
        return super.getNormal(p);
    }

    /**
     * Finds the intersections of the ray with the triangle.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        // Check if the point inside the area of the triangle
        Vector v1 = this.vertices.get(0).subtract(ray.getHead());
        Vector v2 = this.vertices.get(1).subtract(ray.getHead());
        Vector v3 = this.vertices.get(2).subtract(ray.getHead());
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        if (ray.getDirection().dotProduct(n1) <= 0 ||
                ray.getDirection().dotProduct(n2) <= 0 ||
                ray.getDirection().dotProduct(n3) <= 0) {
            return null;
        }

        double numerator = this.plane.getNormal().dotProduct(this.plane.getq().subtract(ray.getHead()));
        double denominator = this.plane.getNormal().dotProduct(ray.getDirection());
        
        if (isZero(denominator)) {
            throw new IllegalArgumentException("denominator cannot be zero");
        }
        double t = alignZero(numerator / denominator);

        // The ray starts from the triangle
        if (t == 0) {
            return null;
        }

        if (t > 0) {
            return List.of(ray.getPoint(t));
        }

        return null;
    }
}

