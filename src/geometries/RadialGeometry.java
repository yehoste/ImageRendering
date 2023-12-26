package geometries;

import primitives.Vector;
import primitives.Point;

public abstract class RadialGeometry implements Geometry {

    protected final double radius;

    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
