package renderer;

import scene.Scene;

import static primitives.Util.alignZero;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;

/**
 * hA simple ray tracer that uses the ray-sphere intersection algorithm to calculate the color of each pixel.
 */
public class SimpleRayTracer extends RayTracerBase {

    private static final double DELTA = 0.1;

    /**
     * Constructs a new SimpleRayTracer with the specified scene.
     * 
     * @param scene the scene to be traced
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }


    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray ray = new Ray(point, lightDirection);
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) return true;
        double lightDistance = alignZero(light.getDistance(gp.point));
        for (Point p : intersections) {
            if (lightDistance >= alignZero(p.distance(gp.point))){
                return false;
            }
        }
        return true;
    }

    /**
     * Traces the specified ray and returns the color of the pixel it intersects with.
     * 
     * @param ray the ray to be traced
     * @return the color of the pixel the ray intersects with
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return this.scene.background;
        }
        return this.calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return this.scene.ambientLight.getIntensity().add(calcLocalEffects(geoPoint, ray));

    }

    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Color color = geoPoint.geometry.getEmission();
        Vector v = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return color;
        }
        Material material = geoPoint.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0 && unshaded(geoPoint, lightSource, l, n, nl)) { // sign(nl) == sign(nv)
                Color iL = lightSource.getIntensity(geoPoint.point);
                color = color.add(
                        iL.scale(calcDiffusive(material, nl))
                        .add(iL.scale(calcSpecular(material, n, l, nl, v))));
            }
        }
        return color;
    }

    private Double3 calcDiffusive(Material material, double nl) {
        return material.getKd().scale(Math.abs(nl));
    }

    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        double minusVR = -alignZero(r.dotProduct(v));
        if (minusVR <= 0) {
            return Double3.ZERO;
        }
        return material.getKs().scale(Math.pow(minusVR, material.getnShininess()));
    }

    

}