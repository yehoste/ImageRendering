package lighting;

import primitives.*;

/**
 * Ambient light for all objects in 3D space
 */
public class AmbientLight {

    // The intensity of ambient light
    private final Color intensity;

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     *
     * @param Ia Illumination light
     * @param Ka light factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }

    public AmbientLight(Color Ia, double Ka) {
        this.intensity = Ia.scale(Ka);
    }

    public Color getIntensity() {
        return intensity;
    }
}