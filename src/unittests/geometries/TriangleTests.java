package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
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
		// Create three points representing the vertices of the triangle

		// Create a triangle with the three points
		Triangle triangle = new Triangle(point1, point2, point3);

		// Choose one of the vertices as the parameter for getNormal
		Point testPoint = point1;

		// Define the expected normal vector
		Vector expectedNormal = new Vector(0, 0, 1);

		// Get the actual normal vector from the getNormal function
		Vector actualNormal = triangle.getNormal(testPoint);

		// Assert that the actual normal vector is equal to the expected normal vector
		assertEquals(expectedNormal, actualNormal,"The calculated normal vector is not as expected");
	}

}
