package lighting;

import primitives.*;

/**
 * Ambient light for all objects in 3D space
 */
public class AmbientLight extends Light{

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     *
     * @param Ia Illumination light
     * @param Ka light factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
        //this.intensity = Ia.scale(Ka);
    }

    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
        //this.intensity = Ia.scale(Ka);
    }
}