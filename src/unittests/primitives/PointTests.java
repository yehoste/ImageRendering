package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for primitives.Point class
 */
class PointTests {
	private final Point  p1 = new Point(1, 2, 3);
	private final Point  p2 = new Point(2, 4, 6);
	private final Point  p3 = new Point(2, 4, 5);

	private final Vector v1 = new Vector(1, 2, 3);
	private final Vector v1Opposite = new Vector(-1, -2, -3);




	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        //TC01 checks if subtract works as intended
		assertEquals(v1, p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");
        // =============== Boundary Values Tests ==================
        //TC11 checks if throws exeption when getting 0 vector
		assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), //
				"Error does not throw exeption at all");
        //TC12 checks if throws the correct exeption
        assertThrowsExactly(IllegalArgumentException.class, () -> p1.subtract(p1),"ERROR: does no throw correct exeption");
	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        //TC01 checks if point + vector works correctly
		assertEquals(p2, p1.add(v1), "ERROR: (point + vector) = other point does not work correctly");
        //TC02 checks if center of coordinates works correctly
		assertEquals(p1.add(v1Opposite),Point.ZERO,"ERROR: (point + vector) = center of coordinates does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC01 checks if point squared distance to itself is not zero
		assertEquals(0,p1.distanceSquared(p1),"ERROR: point squared distance to itself is not zero");
        //TC02 checks if squared distance between points is wrong
		assertEquals(0,p1.distanceSquared(p3)-9,"ERROR: squared distance between points is wrong");

	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        //TC01 checks if point distance to itself is not zero
		assertEquals(0,p1.distance(p1),"ERROR: point distance to itself is not zero");
        //TC02 checks if distance between points to itself is wrong
		assertEquals(0,p1.distance(p3) - 3,"ERROR: distance between points to itself is wrong");

	}

}
