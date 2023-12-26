package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;


public class Tube extends RadialGeometry{
    private final Ray axis;

    public Tube(Ray axis, double radius) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point ourPoint){
        return null;
    }

}
