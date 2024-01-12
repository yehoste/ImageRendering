package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

import static primitives.Util.isZero;

/*
 * Class Tube represents a tube with a specified axis and radius.
 */
public class Tube extends RadialGeometry {
    protected final Ray axis;

    /**
     * Constructs a new Tube with the specified axis and radius.
     * 
     * @param axis the axis of the tube
     * @param radius the radius of the tube
     */
    public Tube(Ray axis, double radius) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Returns the normal vector at the given point on the object.
     * 
     * @param ourPoint the point on the object
     * @return the normal vector at the given point
     */
    @Override
    public Vector getNormal(Point ourPoint) {
        // the "shade" of the action point-head on the main axis
        double t = axis.getDirection().dotProduct(ourPoint.subtract(axis.getHead()));
        // border case
        if (isZero(t)) {
            return ourPoint.subtract(axis.getHead());
        }
        // multiply the direction vector of the axis by t, and you have the point on the axis infront of the point
        return ourPoint.subtract(axis.getPoint(t)).normalize();
    }

    /**
     * Returns all points of intersection between this object and the given ray.
     * 
     * @param ray the ray to test for intersection with this object
     * @return a list of all points of intersection between the ray and this object, or null if there are no intersections
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
