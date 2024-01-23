package renderer;

import scene.Scene;
import primitives.*;

/**
 * A simple ray tracer that uses the ray-sphere intersection algorithm to calculate the color of each pixel.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructs a new SimpleRayTracer with the specified scene.
     * 
     * @param scene the scene to be traced
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces the specified ray and returns the color of the pixel it intersects with.
     * 
     * @param trace the ray to be traced
     * @return the color of the pixel the ray intersects with
     *
    @Override
    public Color traceRay(Ray trace) {
        return Color.BLACK; // TODO: implement
    }*/

}