package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * The Plane class represents a plane in three-dimensional space defined by a point and a normal
 * vector.
 */
public class Plane extends Geometry {
    private final Point q;
    private final Vector normal;

    /**
     * Constracts a new plane with the given three points.
     * @param point1
     * @param point2
     * @param point3
     */
    public Plane(Point point1,Point point2,Point point3){
        this.q=point1;
        
        //Calculate two vectors from the points
        Vector v1 = point2.subtract(point1);
        Vector v2 = point3.subtract(point1);
        // Calculate the cross product of the vectors to get the normal vector 
        // And normalize the normal vector to have a unit length
        this.normal = (v1.crossProduct(v2)).normalize();
    }

    /**
     * Creates a new plane with the given point as the origin and the given normal vector as the plane's normal.
     * 
     * @param ourPoint the point that serves as the origin of the plane
     * @param thatNormal the normal vector of the plane
     */
    public Plane(Point ourPoint, Vector thatNormal) {
        this.q = ourPoint;
        this.normal = thatNormal.normalize();
    }

    public Point getq() {
        return this.q;
    }

    public Vector getNormal(){
        return normal;
    }

    public Vector getNormal(Point ourPoint){
        return normal;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        // The ray is contained in the plane
        if (isZero(ray.getDirection().dotProduct(this.normal))) {
            return null;
        }

        // Ray origin is the head of the normal
        if (ray.getHead().equals(this.q)) {
            return null;
        }

        double numerator = this.normal.dotProduct(this.q.subtract(ray.getHead()));
        double denominator = this.normal.dotProduct(ray.getDirection());
        if (isZero(denominator)) {
            throw new IllegalArgumentException("denominator cannot be zero");
        }
        double t = alignZero(numerator / denominator);

        // The ray starts from the plane
        if (t == 0) {
            return null;
        }

        if (t > 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }

        return null;
    }

}
