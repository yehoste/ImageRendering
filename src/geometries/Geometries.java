package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
