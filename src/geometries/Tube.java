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

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        /*if (this.axis.getDirection().isParallel(ray.getDirection())) {
            return null;
        }*/
        return null;
    }
}
