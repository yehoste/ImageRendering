/**
 * A sphere is a three-dimensional shape with a definite radius and center.
 * It is defined by its center point and its radius.
 * 
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import static primitives.Util.*;

public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Constructs a new Sphere with the specified center and radius.
     * 
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     * */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the normal vector at the specified point on the sphere.
     * 
     * @param point the point on the sphere
     * @return the normal vector at the specified point on the sphere
     */
    public Vector getNormal(Point point) {
        Vector v = point.subtract(center);
        return v.normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        if (center.equals(p0)) return List.of(new GeoPoint(this, ray.getPoint(radius)));

        Vector u = center.subtract(p0);

        double tm = v.dotProduct(u);
        if ((tm < 0) && (u.length() > radius)) return null;

        double d = Math.sqrt(u.lengthSquared() - tm * tm);
        if (d >= radius) return null;

        double th = Math.sqrt(radius * radius - d * d);
        if (isZero(th)) return null;

        double t1 = tm + th, t2 = tm - th;
        if (isZero(t1)) return null;

        Point p1 = ray.getPoint(t1);
        if (!(alignZero(t2) > 0)) return List.of(new GeoPoint(this, p1));

        return List.of(new GeoPoint(this, p1), new GeoPoint(this, ray.getPoint(t2)));
    }
}