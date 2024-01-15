package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import geometries.Triangle;
/**
 * Unit tests for geometries.Triangle class
 */
class TriangleTests {
	private final Point point1 = new Point(0, 0, 0);
	private final Point point2 = new Point(1, 0, 0);
	private final Point point3 = new Point(0, 1, 0);

	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============

		// Create three points representing the vertices of the triangle

		// Create a triangle with the three points
		Triangle triangle = new Triangle(point1, point2, point3);

		// Choose one of the vertices as the parameter for getNormal
		Point testPoint = point1;

		// Define the expected normal vector
		Vector expectedNormal = new Vector(0, 0, 1);

		// Get the actual normal vector from the getNormal function
		Vector actualNormal = triangle.getNormal(testPoint);

		// TC01: Assert that the actual normal vector is equal to the expected normal vector
		assertEquals(expectedNormal, actualNormal,"The calculated normal vector is not as expected");
	}

	/**
	 * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		Triangle triangle = new Triangle(
				new Point(1, 0, 0),
				new Point(0, 1, 0),
				new Point(0, 0, 1)
		);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray intersects inside the triangle (1 points)
		var result = triangle.findIntersections(new Ray(new Point(-1, -1, -2), new Vector(1, 1, 2)));
		assertEquals(1, result.size(), "Wrong number of points");
		assertEquals(new Point(0.25, 0.25, 0.5), result.get(0), "Ray intersects inside the triangle");

		// TC02: Ray intersects outside the triangle against an edge (0 points)
		assertNull(triangle.findIntersections(new Ray(new Point(-1, -2, -2), new Vector(1, 1, 2))), "Ray intersects outside the triangle against an edge");
		// TC03: Ray intersects outside the triangle against a vertex (0 points)
		assertNull(triangle.findIntersections(new Ray(new Point(-2, -2, -2), new Vector(1, 1, 2))), "Ray intersects outside the triangle against a vertex");
	
		// =============== Boundary Values Tests ==================
		// TC11: intersection on the edge of the triangle (0 points)
		assertNull(triangle.findIntersections(new Ray(new Point(0.5, 0.5, 0), new Vector(0, 0, 1))), "intersection on the edge of the triangle");
        // TC12: intersection on the vertex of the triangle (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 0, 1))), "intersection on the vertex of the triangle");
        // TC13: intersection on the continue line of the triangle (0 points)
		assertNull(triangle.findIntersections(new Ray(new Point(0, 2, 0), new Vector(0, 1, 0))), "intersection on the continue line of the triangle");
	}

}
