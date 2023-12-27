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


    public Vector getNormal(Point ourPoint) {
        // TODO: Implement this method
        return null;
    }
}