package geometries;

import java.util.Arrays;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries implements Intersectable{

    private final List<Intersectable> ListOfIntersectable = List.of();

    public Geometries() {

    }

    public Geometries(Intersectable... geometries) {
        this.add(geometries);
    }

    public void add(Intersectable... geometries) {
        ListOfIntersectable.addAll(Arrays.asList(geometries));
    }


    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
