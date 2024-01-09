package unittests.geometries;
import geometries.*;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PlaneTest {
	@Test
	void testConstructor(){
		// =============== Boundary Values Tests ==================

		// TC01: Tests that the constructor throws an IllegalArgumentException when given two points that are collinear.
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(0, 0 ,3),new Point (0,0,3), new Point (1,3,3)),
				"ERROR: constructor does not throw error in illegal definition- 2 correlating points.");

		// TC02: Tests that the constructor throws an IllegalArgumentException when given three points that lie on the same line.
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 1 ,1),new Point (2,2,2), new Point (0,0,0)),
				"ERROR: constructor does not throw error in illegal definition - all points on same line.");
	}

	@Test
	void getNormal() {
		// ============ Equivalence Partitions Tests ==============

		Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
		Vector normal = plane.getNormal(new Point(2, -1, 0));

		// TC01: Test that normal to plane is normalised
		// Test that plane return normal in the correct direction.
		assertThrows(IllegalArgumentException.class,
				() -> (new Vector(1, 1, 1)).crossProduct(normal),
				"ERROR: plane doesn't return normal in the correct direction.");

		// TC01: that plane normal lengthSquared = 1
		assertEquals(1, normal.lengthSquared(), 0.000001, "ERROR: normal to plane isn't normalised.");
	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		Plane plane = new Plane(
				new Point(1, 0, 0),
				new Point(0, 1, 0),
				new Point(0, 0, 1)
		);

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray intersects the plane (1 points)
		var result=plane.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, 0, -1)));
		assertEquals(1, result.size(), "Wrong number of points");
		assertEquals(new Point(0, 1, 0), result.get(0), "Ray intersects the plane");

		// TC02: Ray doesn't intersects the plane (0 points)
		assertNull(plane.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, 0, 1))), "Ray doesn't intersects the plane");

		// =============== Boundary Values Tests ==================

		// **** Group: Ray is parallel to the plane (all tests 0 points)
		// TC03: Ray is included in the plane
		assertNull(plane.findIntersections(new Ray(new Point(0, 0, 1), new Vector(-1, -1, 2))), "Ray is included in the plane");

		// TC04: Ray isn't included in the plane
		assertNull(plane.findIntersections(new Ray(new Point(0, 0, 2), new Vector(-1, -1, 2))), "Ray is parallel to the plane and not included in the plane");

		// **** Group: Ray is orthogonal to the plane
		// TC05: Ray starts before the plane (1 points)
		result = plane.findIntersections(new Ray(new Point(-1, -1, -1), new Vector(1, 1, 1)));
		assertEquals(1, result.size(), "Wrong number of points");
		double third = 1d / 3;
		assertEquals(new Point(third, third, third), result.get(0), "Ray starts before the plane and is orthogonal to the plane");

		// TC06: Ray starts inside the plane (0 points)
		assertNull(plane.findIntersections(new Ray(new Point(0, 0, 1), new Vector(1, 1, 1))), "Ray starts inside the plane and is orthogonal to the plane");

		// TC07: Ray starts after the plane (0 points)
		assertNull(plane.findIntersections(new Ray(new Point(2, 2, 2), new Vector(1, 1, 1))), "Ray starts after the plane and is orthogonal to the plane");

		// **** Group: Special cases
		// TC08: Ray begins in the plane's referenced point (0 points)
		assertNull(plane.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0))), "Ray begins in the plane's referenced point");
	}

}
