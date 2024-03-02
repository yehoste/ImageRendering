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
        this.minPoint = null;
        this.maxPoint = null;
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

    public List<Point> findIntersections(Ray ray) {
        // Calculate the dot product between the plane's normal and the ray's direction vector
        double nv = alignZero(this.normal.dotProduct(ray.getDirection()));

        // Check if the ray is parallel to the plane
        if (isZero(nv))
            return null;

        // Check if the ray starts on the plane, if true, return null (no intersections)
        if (this.q.equals(ray.getHead()))
            return null;

        // Calculate the parameter 't' for the intersection point
        double t = alignZero(this.normal.dotProduct(q.subtract(ray.getHead()))) / nv;

        // Check if the intersection point is in the direction of the ray
        if (t <= 0)
            return null;

        // Return the intersection point as a list
        return List.of(ray.getPoint(t));
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // Calculate the dot product between the plane's normal and the ray's direction vector
        double nv = alignZero(this.normal.dotProduct(ray.getDirection()));

        // Check if the ray is parallel to the plane
        if (isZero(nv))
            return null;

        // Check if the ray starts on the plane, if true, return null (no intersections)
        if (this.q.equals(ray.getHead()))
            return null;

        // Calculate the parameter 't' for the intersection point
        double t = alignZero(this.normal.dotProduct(q.subtract(ray.getHead()))) / nv;

        // Check if the intersection point is in the direction of the ray
        if (t <= 0)
            return null;

        if (alignZero(t - maxDistance) <= 0)
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        return null;
    }

    public static List<Vector> findAxisForPlane(Vector normal) {
        double A = normal.getX();
        double B = normal.getY();
        double C = normal.getZ();
        
        // Generate a random vector within the plane defined by the normal vector
        if (A == -3 && B == 0 && C == -1) A = -4; // avoid create zero vector
        Vector AxisXVector = normal.crossProduct(new Vector(A+3, B*(A+5), C+1)).normalize(); // B*(A+5) שינוי מקדם התלות הלינארי של האיקסים
        Vector AxisYVector = normal.crossProduct(AxisXVector).normalize();
        return List.of(AxisXVector,AxisYVector);
    } 
    
    @Override
    public boolean isRayIntersectingBoundingBox(Ray ray,double maxDistance) {
        if(!isZero(this.normal.dotProduct(ray.getDirection()))) return true;
        return false;
    }

}
        
        
        
        
        

