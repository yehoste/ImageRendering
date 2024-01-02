package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Tube extends RadialGeometry {
    private final Ray axis;

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

    @Override
    public Vector getNormal(Point ourPoint) {
        // TODO: Implement this method
        return null;
    }
}
