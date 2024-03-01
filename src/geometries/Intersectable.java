package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
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
        // Check for intersection with each bounding box plane (6 planes in total)
        double tmin = Double.MIN_VALUE;
        double tmax = Double.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            double t1 = intersectPlane(ray, minPoint.get(i), maxPoint.get(i), ray.getDirection().get(i));
            double t2 = intersectPlane(ray, maxPoint.get(i), minPoint.get(i), ray.getDirection().get(i));

            tmin = Math.max(tmin, Math.min(t1, t2));
            tmax = Math.min(tmax, Math.max(t1, t2));
        }

        // If no intersection or negative t values, return false
        return (tmin <= tmax) && (tmin >= 0.0) && (tmax <= maxDistance);
    }

    private double intersectPlane(Ray ray, double p1, double p2, double dir) {
        // Calculate t value for intersection with plane defined by a single axis
        double t = (p1 - ray.getHead().get(ray.getDirection().getAxis())) / dir;
        return t;
    }

}
