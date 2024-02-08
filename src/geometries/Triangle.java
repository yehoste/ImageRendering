package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static java.util.Objects.isNull;
import static primitives.Util.compareSign;
import static primitives.Util.isZero;

import java.util.List;

/**
 * A class that represents a triangle in three-dimensional space defined by three vertices.
 * The class inherits from the Polygon class.
 */
public class Triangle extends Polygon{
    /**
     * Constructs a new Triangle with three specified vertices.
     *
     * @param point1 The first vertex of the triangle.
     * @param point2 The second vertex of the triangle.
     * @param point3 The third vertex of the triangle.
     */
    public Triangle(Point point1, Point point2, Point point3){
        super(point1, point2, point3);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Point> intersections = plane.findIntersections(ray);
        if (isNull(intersections)) return null;

        Point p0 = ray.getHead();

        Point p1 = vertices.get(0);
        if (p1.equals(p0)) return null;
        Vector v1 = p1.subtract(p0);

        Point p2 = vertices.get(1);
        if (p2.equals(p0)) return null;
        Vector v2 = p2.subtract(p0);

        Point p3 = vertices.get(2);
        if (p3.equals(p0)) return null;
        Vector v3 = p3.subtract(p0);

        Vector v = ray.getDirection();

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double vn1 = v.dotProduct(n1);
        if (isZero(vn1)) return null;

        double vn2 = v.dotProduct(n2);
        if (isZero(vn2)) return null;

        double vn3 = v.dotProduct(n3);
        if (isZero(vn3)) return null;

        Point intersection = intersections.get(0);
        if (compareSign(vn1, vn2) && compareSign(vn2, vn3)) return List.of(new GeoPoint(this, intersection));

        return null;
    }
}