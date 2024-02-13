package lighting;

import primitives.*;
/**
 * An interface for light sources in a 3D space.
 */
public interface LightSource {
    
    /**
     * Returns the intensity of the light at a given point.
     * 
     * @param p the point in space
     * @return the intensity of the light at the given point
     */
    public Color getIntensity(Point p);
    
    /**
     * Returns the direction of the light at a given point.
     * 
     * @param p the point in space
     * @return the direction of the light at the given point
     */
    public Vector getL(Point p);

    public double getDistance(Point point);
}
