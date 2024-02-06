package geometries;

import primitives.*;

/**
 * Interface for geometric objects.
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;

    /**
     * The function "getEmission" returns the color of the object.
     * 
     * @return The color of the object.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * The function "setEmission" sets the color of the object.
     * 
     * @param emission The color of the object.
     * @return this object.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * The function "getNormal" returns a vector representing the normal of a given point.
     * 
     * @param p The point for which we want to calculate the normal vector.
     * @return A vector is being returned.
     */
    public abstract Vector getNormal(Point p);

}
    

