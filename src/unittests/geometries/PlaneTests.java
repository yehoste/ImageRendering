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

		// TC01: creates a new Plane object with the given points
		Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));

		// TC02: creates a new Vector object with the given coordinates
		Vector normal = plane.getNormal(new Point(2, -1, 0));

		// TC03: Test that plane return normal in the correct direction.
		assertThrows(IllegalArgumentException.class,
				() -> (new Vector(1, 1, 1)).crossProduct(normal),
				"ERROR: plane doesn't return normal in the correct direction.");

		// TC04: Test that normal to plane is normalised.
		assertEquals(1, normal.lengthSquared(), 0.000001, "ERROR: normal to plane isn't normalised.");
	}

}
