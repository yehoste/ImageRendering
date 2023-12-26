package geometries;

import primitives.Point;
import primitives.Vector;


/**
 * The Plane class represents a plane in three-dimensional space defined by a point and a normal
 * vector.
 */
public class Plane {
    private final Point q;
    private final Vector normal;

    public Plane(Point point1,Point point2,Point point3){
        this.q=point1;
        
        /* 
        // Calculate two vectors from the points
        Vector v1 = point2.subtract(point1);
        Vector v2 = point3.subtract(point1);
        // Calculate the cross product of the vectors to get the normal vector 
        // And normalize the normal vector to have a unit length
        this.normal = (v1.crossProduct(v2)).normalize();*/
        this.normal=null;
    }

    public Plane(Point ourPoint, Vector thatNormal){
        this.q=ourPoint;
        this.normal=thatNormal.normalize();
    }

    public Vector getNormal(){
        return normal;
    }

    public Vector getNormal(Point ourPoint){
        return normal;
    }
}
