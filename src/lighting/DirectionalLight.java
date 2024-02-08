package lighting;

import primitives.*;
/**
 * A directional light emits light in all directions.
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * Creates a new directional light with the specified color and direction.
     * 
     * @param color the color of the light
     * @param direction the direction in which the light is emitted
     */
    public DirectionalLight(Color color, Vector direction) {
        super(color);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
