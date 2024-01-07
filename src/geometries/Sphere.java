/**
 * A sphere is a three-dimensional shape with a definite radius and center.
 * It is defined by its center point and its radius.
 * 
 * @author your name
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Constructs a new Sphere with the specified center and radius.
     * 
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     * */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector v= point.subtract(center);
        return v.normalize();
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}