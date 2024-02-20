package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

public class MultiRayTracer extends SimpleRayTracer {
    
    int BlackboardSize;
    int BbWidth;
    int BbHeight;

    public MultiRayTracer(Scene scene, int BbSize,int BbWidth,int BbHeight) {
        super(scene);
        this.BlackboardSize = BbSize;
        this.BbWidth = BbWidth;
        this.BbHeight = BbHeight;
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);

        Vector n = closestPoint.geometry.getNormal(closestPoint.point);

        Blackboard Bb = new Blackboard(BlackboardSize, closestPoint, null, null, this.BbWidth, this.BbHeight);
        
        
        
        //somecode

        
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }

}
