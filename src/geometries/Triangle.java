package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}

