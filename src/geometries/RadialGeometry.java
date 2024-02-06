package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * A abstract class representing a Radial Geometry.
 */
public abstract class RadialGeometry extends Geometry {

    protected final double radius;

    /**
     * Constructs a new RadialGeometry with the specified radius.
     * 
     * @param radius the radius of the circle
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}

