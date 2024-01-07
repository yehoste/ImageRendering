package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public interface Intersectable {


    /**
     *  The function find all the intesections of the ray
     * @param ray
     * @return list of the intersection points
     */
    List<Point> findIntsersections(Ray ray);
}
