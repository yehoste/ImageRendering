package renderer;

import scene.Scene;

import java.util.List;

import primitives.*;

/**
 * hA simple ray tracer that uses the ray-sphere intersection algorithm to calculate the color of each pixel.
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
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = this.scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return this.scene.background;
        }
        return this.calcColor(ray.findClosestPoint(intersections));
    }

    private Color calcColor(Point point) {
        return this.scene.ambientLight.getIntensity();
    }

}