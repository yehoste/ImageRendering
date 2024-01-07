package unittests.geometries;

import geometries.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

class SphereTest {

	@Test
	void getNormal() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test if getnormal work
		Sphere sphere = new Sphere(new Point(0,0,0), 1);
		assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point(1,0,0)));
	}

	
	private final Point p001 = new Point(0, 0, 1);
	private final Point p100 = new Point(1, 0, 0);
	private final Vector v001 = new Vector(0, 0, 1);
	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
	Sphere sphere = new Sphere(p100, 1d);
	final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
	final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
	final var exp = List.of(gp1, gp2);
	final Vector v310 = new Vector(3, 1, 0);
	final Vector v110 = new Vector(1, 1, 0);
	final Point p01 = new Point(-1, 0, 0);

	// ============ Equivalence Partitions Tests ==============
	// TC01: Ray's line is outside the sphere (0 points)
	assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");
	
	// TC02: Ray starts before and crosses the sphere (2 points)
	final var comparator = Comparator.comparingDouble(p -> ((Point) p).distance(p01));
	final var result1 = sphere.findIntersections(new Ray(p01, v310))
            .stream()
            .sorted(comparator)
            .toList();

	assertEquals(2, result1.size(), "Wrong number of points");
	assertEquals(exp, result1, "Ray crosses sphere");
	// TC03: Ray starts inside the sphere (1 point)
        Point insidePoint = new Point(0.5, 0, 0); // Point inside the sphere
        List<Point> insideResult = sphere.findIntersections(new Ray(insidePoint, v310));
        assertEquals(1, insideResult.size(), "Wrong number of points");

        // TC04: Ray starts after the sphere (0 points)
        Point afterPoint = new Point(2, 0, 0); // Point after the sphere
        assertNull(sphere.findIntersections(new Ray(afterPoint, v310)), "Ray starts after sphere");

	// =============== Boundary Values Tests ==================
	// **** Group: Ray's line crosses the sphere (but not the center)
	// TC11: Ray starts at sphere and goes inside (1 points)
	// TC12: Ray starts at sphere and goes outside (0 points)
	// **** Group: Ray's line goes through the center
	// TC13: Ray starts before the sphere (2 points)
	// TC14: Ray starts at sphere and goes inside (1 points)
	// TC15: Ray starts inside (1 points)
	// TC16: Ray starts at the center (1 points)
	// TC17: Ray starts at sphere and goes outside (0 points)
	// TC18: Ray starts after sphere (0 points)
	// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
	// TC19: Ray starts before the tangent point
	// TC20: Ray starts at the tangent point
	// TC21: Ray starts after the tangent point
	// **** Group: Special cases
	// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
	}
	
}