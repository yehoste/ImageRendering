package lighting;

import static primitives.Util.alignZero;

import primitives.*;

/**
 * A spot light emits light in all directions from a single point, 
 * like a torch. The light is more concentrated in a small area 
 * than a point light, and the light is more directional.
 * 
 * @author Tabnine
 */
public class SpotLight extends PointLight {

    private Vector direction;
    private double narrowBeam = 1;

    /**
     * Creates a new spot light with the specified color, position, and direction.
     * 
     * @param color the color of the light
     * @param point the position of the light
     * @param vector the direction of the light
     */
    public SpotLight(Color color, Point point, Vector vector) {
        super(color, point);
        direction = vector.normalize();
    }

    /**
     * Sets the color temperature of the light.
     * 
     * @param kC the color temperature
     * @return the spot light
     */
    @Override
    public SpotLight setkC(double kC) {
        return (SpotLight) super.setkC(kC);
    }

    /**
     * Sets the brightness of the light.
     * 
     * @param kL the brightness
     * @return the spot light
     */
    @Override
    public SpotLight setkL(double kL) {
        return (SpotLight) super.setkL(kL);
    }

    /**
     * Sets the light's quality.
     * 
     * @param kQ the quality
     * @return the spot light
     */
    @Override
    public SpotLight setkQ(double kQ) {
        return (SpotLight) super.setkQ(kQ);
    }

    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    public double getNarrowBeam() {
        return this.narrowBeam;
    }

    @Override
    public Color getIntensity(Point p) {
        double lDir = direction.dotProduct(getL(p));
        if (alignZero(lDir) <= 0) return Color.BLACK;
        Color color = super.getIntensity(p);
        return color.scale(Math.pow(lDir, narrowBeam));
        
    }
}