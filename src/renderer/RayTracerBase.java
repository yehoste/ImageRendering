package renderer;

import primitives.*;
import scene.*;

/**
 * Base class for ray tracer implementations.
 */
public abstract class RayTracerBase {

    protected Scene scene;
    
    /**
     * Creates a new instance of the {@link RayTracerBase} class with the specified {@link Scene}.
     * 
     * @param scene the scene to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }
    /**
     * Abstract method that is used to trace a ray and calculate the color of the intersection point.
     * 
     * @param trace the ray to be traced
     * @return the color of the intersection point
     */
    public abstract Color traceRay(Ray trace);


}
