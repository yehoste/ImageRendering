package lighting;

import primitives.*;

/**
 * A point light source emits light in all directions from a single point in space.
 */
public class PointLight extends Light implements LightSource {

    private Point position;
    private double kC = 1, kL = 0, kQ = 0;

    /**
     * Creates a new point light with the specified color and position.
     *
     * @param color the color of the light
     * @param position the position of the light
     */
    public PointLight(Color color, Point position) {
        super(color);
        this.position = position;
    }

    /**
     * Sets the constant attenuation coefficient of the light.
     *
     * @param kC the constant attenuation coefficient
     * @return the PointLight object
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation coefficient of the light.
     *
     * @param kL the linear attenuation coefficient
     * @return the PointLight object
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation coefficient of the light.
     *
     * @param kQ the quadratic attenuation coefficient
     * @return the PointLight object
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    public double getDistance(Point point){
        return point.distance(position);
    }

    @Override
    public Color getIntensity(Point p) {
        Color Ic = this.getIntensity();
        double distance = p.distance(this.position);
        double factor = this.kC + this.kL * distance + this.kQ * distance * distance;
        return Ic.scale(1 / factor);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
}
