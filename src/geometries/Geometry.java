package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface for geometric objects.
 */
public interface Geometry extends Intersectable {

    /**
     * The function "getNormal" returns a vector representing the normal of a given point.
     * 
     * @param p The point for which we want to calculate the normal vector.
     * @return A vector is being returned.
     */
    Vector getNormal(Point p);

}
    

