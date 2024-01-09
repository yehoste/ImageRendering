package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import geometries.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

class GeometriesTest {

    Plane plane = new Plane(
            new Point(3,0,0),
            new Point(0,4,0),
            new Point(0,0,0));
    Triangle triangle = new Triangle(
            new Point(0,0,7),
            new Point(5,0,0),
            new Point(0,4,0));

    Sphere sphere = new Sphere(new Point(0,0,0),1);

    Geometries geometries = new Geometries(plane, triangle, sphere);

    @Test
    void testFindIntersections() {

        Ray ray1 = new Ray(new Point(0,0,-3), new Vector(1,4,8));
        Ray ray2 = new Ray(new Point(0,0,-3), new Vector(1,-1,8));
        Ray ray3 = new Ray(new Point(0,0,-3), new Vector(1,1,8));
        Ray ray4 = new Ray(new Point(0,0,-3), new Vector(1,-5,8));
        Ray ray5 = new Ray(new Point(0,0,-3), new Vector(1,-5,-2));

        // ============ Equivalence Partitions Tests ==============
        // The ray intersects the shapes but not all of them
        // TC1: The ray intersects the plane and the triangle but not the sphere (2 points)
        assertEquals(2, geometries.findIntersections(ray1).size(), "findIntersections() is incorrect");
        // TC2: The ray intersects the plane and the sphere but not the triangle (3 points)
        assertEquals(3, geometries.findIntersections(ray2).size(), "findIntersections() is incorrect");

        // =============== Boundary Values Tests ==================
        // TC3: The ray intersects all shapes
        assertEquals(4, geometries.findIntersections(ray3).size(), "findIntersections() is incorrect");
        // TC4: The ray intersects one shapes
        assertEquals(1, geometries.findIntersections(ray4).size(), "findIntersections() is incorrect");
        // TC5: The ray does not intersect any shape
        assertNull(geometries.findIntersections(ray5), "findIntersections() is incorrect");

    }
}