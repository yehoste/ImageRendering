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
public class Geometries implements Intersectable {

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
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;
        for (Intersectable geometry: this.geometriesList) {
            List<Point> geometryIntersections = geometry.findIntersections(ray);
            if (geometryIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections;
    }
}