package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere {
    private final Point center;
    private final double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Vector getNormal(Point ourPoint){
        return null;
    }
}
