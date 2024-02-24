package renderer;

import scene.Scene;

import static primitives.Util.alignZero;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import lighting.PointLight;
import primitives.*;

/**
 * hA simple ray tracer that uses the ray-sphere intersection algorithm to calculate the color of each pixel.
 */
public class SimpleRayTracer extends RayTracerBase {

    private static final Double3 INITIAL_K = Double3.ONE;

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    
    protected static final double MIN_CALC_COLOR_K = 0.001;

    private int GlossyAndBlurryBbSize = 0;

    private Blackboard  GlossyAndBlurryBlackBoard;

    private int SoftShadowBbSize = 0;

    private Blackboard SoftShadowBlackBoard;


    /**
     * Constructs a new SimpleRayTracer with the specified scene.
     * 
     * @param scene the scene to be traced
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }
    
    /**
     * Traces a ray through the scene and calculates the color based on the closest intersection point.
     * If there are no intersections, returns the background color of the scene.
     *
     * @param ray The ray to be traced.
     * @return The color calculated based on the closest intersection point.
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }

    protected GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * function that calculates the local effects by phong's model
     *
     * @param gp  - geometry point that its intensity needs to be calculated
     * @param ray - ray from camera through the pixel
     * @return pixel color
     */
    protected Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
    
        if (nv == 0) return color;
    
        Material material = gp.geometry.getMaterial();
    
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
    
            if (nl * nv > 0) {
                Double3 ktr = transparency(gp, lightSource, l, n);
    
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)
                                    .add(calcSpecular(material, n, l, nl, v))));
                }
            }
        }

        return color;
    }

    /**
     * 
     * 
     */

    public SimpleRayTracer setGlossyAndBlurryBbSize(int BlackboardSize) {
        this.GlossyAndBlurryBbSize = BlackboardSize;
        GlossyAndBlurryBlackBoard = new Blackboard(GlossyAndBlurryBbSize, null, null);
        return this;
    }

    public SimpleRayTracer setSoftShadowBbSize(int BlackboardSize) {
        this.SoftShadowBbSize = BlackboardSize;
        SoftShadowBlackBoard = new Blackboard(SoftShadowBbSize, null, null);
        return this;
    }

    /**
     * Calculates the diffuse reflection component of light interaction with a surface.
     *
     * @param material The material properties of the surface.
     * @param nl       The dot product of the surface normal and the light direction.
     * @return The diffuse reflection component as a Double3 color vector.
     */
    protected Double3 calcDiffusive(Material material, double nl) {
        return material.getKd().scale(Math.abs(nl));
    }

    /**
     * Calculates the specular reflection component of light interaction with a surface.
     *
     * @param material The material properties of the surface.
     * @param n        The normal vector of the surface.
     * @param l        The direction from the light source to the surface point.
     * @param nl       The dot product of the surface normal and the light direction.
     * @param v        The direction from the surface point to the viewer.
     * @return The specular reflection component as a Double3 color vector.
     */
    protected Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        return material.getKs().scale(Math.pow(Math.max(0, -r.dotProduct(v)), material.getnShininess()));
    }


    /**
     * Calculates the color at a specified point using ambient light intensity.
     *
     * @param gp The point for which the color is calculated.
     * @return The color at the specified point based on ambient light intensity.
     */
    protected Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp, ray), material.kT, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp, ray), material.kR, level, k));
    }

    private Ray constructRefractedRay(GeoPoint gp, Ray ray) {
        return new Ray(gp.point, ray.getDirection(), gp.geometry.getNormal(gp.point));
    }

    private Ray constructReflectedRay(GeoPoint gp, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(gp.point, r, n);
    }

    protected Color calcGlobalEffect(Ray reflectedRay, Double3 kx, int level, Double3 k) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(reflectedRay);

        if (gp == null) return scene.background;
        if(GlossyAndBlurryBbSize == 0) 
            return calcColor(gp, reflectedRay, level - 1, kkx).scale(kx);
        else {
            GlossyAndBlurryBlackBoard.setCenterPoint(gp.point);
            Vector n = gp.geometry.getNormal(gp.point);

            if (!n.equals(GlossyAndBlurryBlackBoard.getNormal())) GlossyAndBlurryBlackBoard.setAxis(n);

            Color Reflection = Color.BLACK;
            GlossyAndBlurryBlackBoard.generateJitterdPoint();
            for (Point point : GlossyAndBlurryBlackBoard.points) {
                Ray SSray = new Ray(reflectedRay.getHead(), point.subtract(reflectedRay.getHead()));
                Color sampleColor = calcColor(new GeoPoint(gp.geometry, point), SSray, level - 1, kkx);
                Reflection = Reflection.add(sampleColor);
            }

            // Average the colors from supersampling
            Reflection = Reflection.reduce(GlossyAndBlurryBbSize*GlossyAndBlurryBbSize).scale(kx);
        
            return Reflection;
        }
       /**/
    }


    protected Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Checks if a point is shaded by a light source.
     *
     * @param gp       The point to check.
     * @param light    The light source.
     * @param l        The direction from the point to the light source.
     * @param n        The normal vector at the point.
     * @return True if the point is unshaded, false otherwise.
     */
    protected Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray ray = new Ray(gp.point, lightDirection, n);
        
        Double3 ktr = Double3.ONE;
        if (SoftShadowBbSize != 0 && light instanceof PointLight PosLight){

            SoftShadowBlackBoard.setCenterPoint(PosLight.getPosition());
            SoftShadowBlackBoard.setAxis(lightDirection);
            SoftShadowBlackBoard.generateJitterdPoint(); 
            Double3 totalKtr = Double3.ZERO;
            for (Point point : SoftShadowBlackBoard.points) {
                ktr=Double3.ONE;
                Ray SSray = new Ray(ray.getHead(), point.subtract(ray.getHead()));
                List<GeoPoint> intersections = scene.geometries.findGeoIntersections(SSray, light.getDistance(gp.point));
                if (intersections == null) return Double3.ONE;
                for (GeoPoint intersection : intersections) {
                    ktr = ktr.product(intersection.geometry.getMaterial().kT);
                }
                totalKtr=totalKtr.add(ktr);
                //totalKtr=totalKtr.scale(1/2);

            }
            return totalKtr.scale(1d/(SoftShadowBbSize*SoftShadowBbSize));
        }

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, light.getDistance(gp.point));
        if (intersections == null) return Double3.ONE;
        for (GeoPoint intersection : intersections) {
            ktr = ktr.product(intersection.geometry.getMaterial().kT);
        }

        return ktr;
    }   

}