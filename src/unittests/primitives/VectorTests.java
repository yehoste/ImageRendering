package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Vector;

/**
 * Unit tests for primitives.Vector class
 */
class VectorTests {


	Vector v1 = new Vector(1, 2, 3);
	Vector v1Opposite = new Vector(-1, -2, -3);
	Vector v2 = new Vector(-2, -4, -6);
	Vector v3 = new Vector(0, 3, -2);
	Vector v4 = new Vector(1, 2, 2);
	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		Vector v = new Vector(1, 2, 3);
      	Vector u = v.normalize();

		assertEquals(0, u.length() - 1, "ERROR: the normalized vector is not a unit vector");
		assertThrows(IllegalArgumentException.class , () -> v.crossProduct(u), "ERROR: the normalized vector is not parallel to the original one");
		
		double ch = v.dotProduct(u); //(v.dotProduct(u) < 0)
		assertEquals(0, (ch - ch), "ERROR: the normalized vector is opposite to the original one");

	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {

		assertEquals(v1Opposite, v1.add(v2), "ERROR: Vector + Vector wrong value");

		assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite), "ERROR: Vector + -itself does not throw an exception");
		assertThrowsExactly(IllegalArgumentException.class, () -> v1.add(v1Opposite), "ERROR: Vector + -itself throws wrong exception");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		assertEquals(0, v1.dotProduct(v3), "ERROR: dotProduct() for orthogonal vectors is not zero");

		assertEquals(0, v1.dotProduct(v2) + 28, "ERROR: dotProduct() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2), "ERROR: crossProduct() for parallel vectors does not throw an exception");

		Vector vr = v1.crossProduct(v3);

		assertEquals(0, (vr.length() - v1.length() * v3.length()), "ERROR: crossProduct() wrong result length");

		assertEquals(0, vr.dotProduct(v1) , "ERROR: crossProduct() result is not orthogonal to its operands");
		assertEquals(0, vr.dotProduct(v3) , "ERROR: crossProduct() result is not orthogonal to its operands");

	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		assertEquals(0, v4.lengthSquared()-9, "ERROR: lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		assertEquals(0, v4.length()-3, "ERROR: length() wrong value");
	}

}
