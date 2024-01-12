package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface for all the objects that can be intersected
 */
public interface Intersectable {


    /**
     *  The function find all the intesections of the ray
     * @param ray
     * @return list of the intersection points
     */
    List<Point> findIntersections(Ray ray);
}
