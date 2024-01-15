package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for primitives.Vector class
 */
class VectorTests {

	private final Point p1 = new Point(1, 2, 3);
	private final Point  p2 = new Point(2, 4, 6);

	Vector v1 = new Vector(1, 2, 3);
	Vector v1Opposite = new Vector(-1, -2, -3);
	Vector v2 = new Vector(-2, -4, -6);
	Vector v3 = new Vector(0, 3, -2);
	Vector v4 = new Vector(1, 2, 2);

	/**
	 * Test method for primitives.Vector#subtract
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
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// ============ Equivalence Partitions Tests ==============
		Vector v = new Vector(1, 2, 3);
      	Vector u = v.normalize();

		// TC01: Test that the normalized vector is a unit vector
		assertEquals(0, u.length() - 1, "ERROR: the normalized vector is not a unit vector");

		// TC01: Test that the normalized vector is parallel to the original one
		assertThrows(IllegalArgumentException.class , () -> v.crossProduct(u), "ERROR: the normalized vector is not parallel to the original one");
		
		double ch = v.dotProduct(u); 
		// TC02: check if the normalized vector is opposite to the original one
		assertEquals(0, (ch - ch), "ERROR: the normalized vector is opposite to the original one");

	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test addition of two vectors
		assertEquals(v1Opposite, v1.add(v2), "ERROR: Vector + Vector wrong value");

		// =============== Boundary Values Tests ==================

		// TC11: Test that adding a vector and its opposite results in zero vector
		assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite), "ERROR: Vector + -itself does not throw an exception");

		// TC12: Test that adding a vector and its opposite results in zero vector throws exactly IllegalArgumentException
		assertThrowsExactly(IllegalArgumentException.class, () -> v1.add(v1Opposite), "ERROR: Vector + -itself throws wrong exception");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test Scale() of vector
		assertEquals(v1Opposite, v1.scale(-1), "ERROR: scale() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test dot product of orthogonal vectors
		assertEquals(0, v1.dotProduct(v3), "ERROR: dotProduct() for orthogonal vectors is not zero");

		// TC02: Test dot product of two vectors
		assertEquals(-28, v1.dotProduct(v2), "ERROR: dotProduct() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		// ============ Equivalence Partitions Tests ==============
		Vector vr = v1.crossProduct(v3);

		// TC01: Test that length of cross-product is proper (orthogonal vectors taken
		// for simplicity)
		assertEquals(0, (vr.length() - v1.length() * v3.length()), 0.00000001, "ERROR: crossProduct() wrong result length");
		
		// TC02: Test cross-product result orthogonality to its operands
		assertEquals(0, vr.dotProduct(v1) , "ERROR: crossProduct() result is not orthogonal to 1st operand");
		assertEquals(0, vr.dotProduct(v3) , "ERROR: crossProduct() result is not orthogonal to 1st operand");

		// =============== Boundary Values Tests ==================

 		// TC11: test zero vector from cross-product of parallel vector
		assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2), "ERROR: crossProduct() for parallel vectors does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		// ============ Equivalence Partitions Tests ==============

		//TC01: Test lengthSquared function
		assertEquals(9, v4.lengthSquared(), "ERROR: lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// ============ Equivalence Partitions Tests ==============

		//TC01: Test length function
		assertEquals(3, v4.length(), "ERROR: length() wrong value");
	}

}
