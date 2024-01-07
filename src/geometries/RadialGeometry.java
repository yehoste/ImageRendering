package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

public abstract class RadialGeometry implements Geometry {

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

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}

