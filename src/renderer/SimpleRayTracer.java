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

    private static final Double3 INITIAL_K = Double3.ONE;

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    
    private static final double MIN_CALC_COLOR_K = 0.001;


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
     * Traces a given ray and returns the color of the hit object
     *
     * @param ray the ray to trace
     * @return the color of the hit object
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? this.scene.background : calcColor(closestPoint, ray);
    }

    /**
     * calculate color using recursion
     *
     * @param geoPoint the closest intersection point between the ray and the object
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K);
    }

    /**
     * A method that returns the color of the intersection point between a ray and an object.
     * If there is no intersection point the method returns the ambient light color.
     *
     * @param geoPoint the closest intersection point between the ray and the object
     * @param level    number of iterations in the recursion
     * @param k        coefficient for recursive calculations of reflection and refraction
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * Method to find the closest intersection point to the ray's origin
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }

    
    
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcColorGLobalEffect(  constructReflectedRay(gp, ray), material.kR , level, k)
            .add(calcColorGLobalEffect(constructReflectedRay(gp, ray), material.kT , level, k));
    }


    private Color calcColorGLobalEffect(Ray ray, Double3 k, int level, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level -1, kkx)).scale(kx);
    }

    /**
     * Method to construct a new ray that reflected from the hit point where the main ray hits
     *
     * @param point hit's point of the main ray on the surface of the geometry
     */
    private Ray constructReflectedRay(GeoPoint point, Ray ray) {
        return null;
    }

    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
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