package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface for geometric objects.
 */
public interface Geometry {

    /**
     * Returns the normal vector at the given point in space.
     * 
     * @param p the point in space
     * @return the normal vector at the given point
     */
    Vector getNormal(Point p);

}
    

