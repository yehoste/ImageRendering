package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (plane.findGeoIntersections(ray, maxDistance) == null)
            return null;
        Point intersection = plane.findIntersections(ray,maxDistance).get(0);
        Point p0 = ray.getHead();
        // Calculate vectors from the intersection point to each vertex of the triangle
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        // Calculate normal vectors of the three triangles formed by the intersection point
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();
        Vector [] n ={n1,n2,n3};

        double nv;
        boolean tmp = false;
        boolean sign = false;
        Vector v = ray.getDirection();

        for(int i=0; i<3; i++) {
            nv = v.dotProduct(n[i]);
            // Check if the dot product is close to zero, meaning the ray is nearly parallel to the triangle
            if (isZero(nv)) return null;
            if (i == 0) {
                // if it's the first dot product, set the sign
                sign = !(nv < 0);
                tmp = sign;
            }
            else {
                // check if the sign is consistent
                if((nv < 0 && tmp) || (nv > 0 && !tmp))
                    return null;
            }
        }
        // Return the intersection point as a list
            return List.of(new GeoPoint(this, intersection));

    }
}