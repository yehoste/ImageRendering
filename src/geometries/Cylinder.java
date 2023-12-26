package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{
    private final double height;

    public Cylinder(Ray axis, double radius, double height) {
        super(axis, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point ourPoint){
        return null;
    }
}
