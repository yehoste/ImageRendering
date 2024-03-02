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
        this.maxPoint = this.center.add(Vector.X.scale(radius)).add(Vector.Y.scale(radius)).add(Vector.Z.scale(radius));
        this.minPoint = this.center.add(Vector.X.scale(-radius)).add(Vector.Y.scale(-radius)).add(Vector.Z.scale(-radius));
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (!isRayIntersectingBoundingBox(ray, maxDistance)) {
            return null;
        }
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        // Check if the ray starts at the center of the sphere
        if (this.center.equals(p0))
            return List.of(new GeoPoint(this, ray.getPoint(radius)));

        // Calculate vector u from the ray's head to the center of the sphere
        Vector u = this.center.subtract(p0);

        // Calculate tm, the projection of u onto the ray direction
        double tm = alignZero(v.dotProduct(u));

        // Calculate the distance between the center of the sphere and the ray
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

        // Check if the ray misses the sphere
        if (d >= this.radius) {
            return null;
        }

        // Calculate th, the distance from the point of intersection to the sphere's surface
        double th = alignZero(Math.sqrt(this.radius * this.radius - d * d));

        // Calculate the two possible intersection points
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);

        boolean distanceT1 = alignZero(t1 - maxDistance) <= 0;
        boolean distanceT2 = alignZero(t2 - maxDistance) <= 0;
        // Return the appropriate intersection points based on their validity
        if (t1 > 0 && t2 > 0 && distanceT1 && distanceT2)
            return List.of(new GeoPoint(this, ray.getPoint(t2)), new GeoPoint(this, ray.getPoint(t1)));

        if (t1 > 0 && distanceT1)
            return List.of(new GeoPoint(this, ray.getPoint(t1)));

        if (t2 > 0 && distanceT2)
            return List.of(new GeoPoint(this, ray.getPoint(t2)));

        return null;
    }
}