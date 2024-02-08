package lighting;

import primitives.*;

/**
 * Abstract class that represents a light source in a 3D scene.
 */
public abstract class Light {

    protected Color intensity;

    /**
     * Creates a new light with the specified color
     * 
     * @param color the color of the light
     */
    protected Light(Color color) {
        intensity = color;
    }

    /**
     * Returns the color of the light
     * 
     * @return the color of the light
     */
    public Color getIntensity() {
        return intensity;
    }

}