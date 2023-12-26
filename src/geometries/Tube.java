package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;


public class Tube {
    private Ray axis;
    private double radius;

    public Tube(Ray axis, double radius) {
        this.axis = axis;
        this.radius = radius;
    }

    public Vector getNormal(Point ourPoint){
        return null;
    }

}
