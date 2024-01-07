package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;
/**
 * A cylinder is a tube with a circular cross-section that extends infinitely in one direction.
 * It is defined by an axis, a radius, and a height.
 */
public class Cylinder extends Tube {
    private final double height;

    /**
     * Constructs a new cylinder with the specified axis, radius, and height.
     *
     * @param axis    the axis of the cylinder
     * @param radius  the radius of the cylinder
     * @param height  the height of the cylinder
     */
    public Cylinder(Ray axis, double radius, double height) {
        super(axis, radius);
        this.height = height;
    }


    public Vector getNormal(Point p) {

        // if the point is on the bottom base
        if (p.equals(axis.getHead())) return (axis.getDirection().scale(-1)); // center
        if (isZero((p.subtract(axis.getHead())).dotProduct(axis.getDirection()) )) return axis.getDirection().scale(-1);

        // if Point at the the top base
        Point _p = axis.getHead().add(axis.getDirection().scale(height));
        if (_p.equals(p)) return axis.getDirection(); // center
        if(isZero(_p.subtract(p).dotProduct(axis.getDirection()))) {
            return axis.getDirection().scale(1);
        }

        // Vector from the point to the closest point on the axis
        return super.getNormal(p);

    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }


}