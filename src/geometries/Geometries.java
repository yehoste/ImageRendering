package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * This class represents a group of shapes in the space that represent a picture.
 * Composite class which includes components and composite geometries.
 */
public class Geometries extends Intersectable {

    /**
     * geometriesList - list of all components in the scene
     */

    public List<Intersectable> geometriesList = new LinkedList<>();

    public Geometries(Intersectable... geometriesList) {
        this.add(geometriesList);
        this.maxPoint = findMaxPoint(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        this.minPoint = findMinPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    private Point findMaxPoint(double x, double y, double z) {
        for (Intersectable intersectable : geometriesList) {
            if (intersectable.maxPoint == null) continue;
            
            if (intersectable.maxPoint.getX() > x) x = intersectable.maxPoint.getX();
            if (intersectable.maxPoint.getY() > y) y = intersectable.maxPoint.getY();
            if (intersectable.maxPoint.getZ() > z) z = intersectable.maxPoint.getZ();
        }
        return new Point(x, y, z);
    }

    private Point findMinPoint(double x, double y, double z) {
        for (Intersectable intersectable : geometriesList) {
            if (intersectable.minPoint == null) continue;
            
            if (intersectable.minPoint.getX() < x) x = intersectable.minPoint.getX();
            if (intersectable.minPoint.getY() < y) y = intersectable.minPoint.getY();
            if (intersectable.minPoint.getZ() < z) z = intersectable.minPoint.getZ();
        }
        return new Point(x, y, z);
    }

    public void add(Intersectable... newGeometriesList) {
        this.geometriesList.addAll(Arrays.asList(newGeometriesList));
    }

    /**
     * This method returns all intersection points with this group of shapes
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> points = null;
        // Iterate over each geometry in the collection
        for (Intersectable geometry : this.geometriesList) {
            if (!geometry.isRayIntersectingBoundingBox(ray, maxDistance)) continue;
            var geoPoints = geometry.findGeoIntersections(ray, maxDistance);
            // If there are intersections, add them to the list
            if (geoPoints != null) {
                if (points == null)
                    points = new LinkedList<>();
                // Add the first intersection point
                points.add(geoPoints.get(0));
                // If there is a second intersection point, add it as well
                if (geoPoints.size() > 1)
                    points.add(geoPoints.get(1));
            }
        }
        return points;
    }
}
