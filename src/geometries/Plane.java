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
public class Plane implements Geometry {
    private final Point q;
    private final Vector normal;

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
    public List<Point> findIntersections(Ray ray) {
        if(ray.getHead().equals(q)){
            return null;
        }
        if(isZero(this.normal.dotProduct(q.subtract(ray.getHead())))){
            return null;
        }
        if (isZero(normal.dotProduct(ray.getDirection()))){
            return null;
        }
        double t = alignZero(this.normal.dotProduct(q.subtract(ray.getHead())))/(this.normal.dotProduct(ray.getDirection()));
        if (t <=0) return null;
        Point p = ray.getPoint(t);
        if (p.subtract(Point.ZERO).equals(Point.ZERO)) return null; 
        return List.of(p);
    }

}
