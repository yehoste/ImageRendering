package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
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
        if (((p.subtract(axis.getHead())).dotProduct(axis.getDirection()) ) == 0) return axis.getDirection().scale(-1);

        // Vector from the base of the cylinder to the point
        Vector v = p.subtract(axis.getHead());

        // Projection of v onto the cylinder's axis
        double projScalar = v.dotProduct(axis.getDirection()) / axis.getDirection().lengthSquared();
        Vector vProjected = axis.getDirection().scale(projScalar);

        // if Point at the the top base
        if (vProjected.equals(v)) return axis.getDirection(); // center
        Point _p = axis.getHead().add(axis.getDirection().scale(height));
        if(_p.subtract(p).dotProduct(axis.getDirection()) == 0) {
            return axis.getDirection().scale(1);
        }

        // Vector from the point to the closest point on the axis
        Vector vToAxis = vProjected.subtract(v);
        return vToAxis.normalize();

    }


}