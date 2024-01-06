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
        
        if (p.equals(axis.getHead())) return (axis.getDirection().scale(-1));
        
        // Vector from the base of the cylinder to the point
        Vector v = p.subtract(axis.getHead());
    
        // Projection of v onto the cylinder's axis
        double projScalar = v.dotProduct(axis.getDirection()) / axis.getDirection().lengthSquared();
        if (projScalar == 0) return (axis.getDirection().scale(-1));
        Vector vProjected = axis.getDirection().scale(projScalar);

        // Vector from the point to the closest point on the axis
        if (vProjected.equals(v)) return axis.getDirection();
        Vector vToAxis = vProjected.subtract(v);
    
        // Handle points on the bases:
        if (Math.abs(vToAxis.dotProduct(axis.getDirection())) >= height / 2) {
            // Point is on a base, normal is parallel to the axis
            return axis.getDirection().scale(vToAxis.dotProduct(axis.getDirection()) > 0 ? 1 : -1);
        } else {
            // Point is on the side surface, normal is perpendicular to the axis
            return vToAxis.normalize();
        }
    }
    
    
}