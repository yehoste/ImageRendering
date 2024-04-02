package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface for all the objects that can be intersected
 */
public abstract class Intersectable {

    //protected List<Point> pointsOfBoundryBox = new LinkedList<>();
    protected Point minPoint, maxPoint;

    /**
     * A class that represents a point in space and the geometry it belongs to.
     */
    public static class GeoPoint {
        /**
         * The geometry the point belongs to.
         */
        public Geometry geometry;
        /**
         * The point in space.
         */
        public Point point;

        /**
         * Constructs a new GeoPoint.
         * @param geometry the geometry the point belongs to.
         * @param point the point in space.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;

            return ((obj instanceof GeoPoint other) &&
                    (this.geometry.equals(other.geometry)) &&
                    (this.point.equals(other.point)));
            
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

    }

    public Point getMinPoint()
    {
        return minPoint;
    }

    public Point getMaxPoint()
    {
        return maxPoint;
    }

    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    public List<Point> findIntersections(Ray ray, double maxDistance) {
        var geoList = findGeoIntersections(ray, maxDistance);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
       
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    protected boolean isRayIntersectingBoundingBox(Ray ray, double maxDistance) {
        // Check for axis-wise intersection with the AABB
        for (int i = 0; i < 3; i++) {
            double tmin = (minPoint.get(i) - ray.getHead().get(i)) / ray.getDirection().get(i);  // Nearest plane intersection on x-axis
            double tmax = (maxPoint.get(i) - ray.getHead().get(i)) / ray.getDirection().get(i);  // Farthest plane intersection on x-axis
        
            // Swap tmin and tmax if necessary (ensure tmin < tmax)
            if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
            }
        
            // Check if the intersection falls outside the ray's parameter range
            if (tmin > maxDistance || tmax < 0) {
            return false;
            }    
        }
        return true;
    }
}
