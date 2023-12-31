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
	Point  p1 = new Point(1, 2, 3);
	Point  p2 = new Point(2, 4, 6);
	Point  p3 = new Point(2, 4, 5);

	Vector v1 = new Vector(1, 2, 3);
	Vector v1Opposite = new Vector(-1, -2, -3);
	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		assertEquals(v1, p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");
		assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), //
				"Error does no throw correct exeption or does not throw exeption at all");
	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		assertEquals(p2, p1.add(v1), "ERROR: (point + vector) = other point does not work correctly");
		assertEquals(p1.add(v1Opposite),Point.ZERO,"ERROR: (point + vector) = center of coordinates does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {

		assertEquals(0,p1.distanceSquared(p1),"ERROR: point squared distance to itself is not zero");
		assertEquals(0,p1.distanceSquared(p3)-9,"ERROR: squared distance between points is wrong");

	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {

		assertEquals(0,p1.distance(p1),"ERROR: point distance to itself is not zero");
		assertEquals(0,p1.distance(p3) - 3,"ERROR: distance between points to itself is wrong");

	}

}
