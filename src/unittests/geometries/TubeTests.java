package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.Tube;
/**
 * Unit tests for geometries.Tube class
 */
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		
		Ray axis = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Tube tube = new Tube(axis, 1);

        // TC01: Test that the get normal of tube work
        assertEquals(new Vector(0, 0, -1), tube.getNormal(new Point(1, 1, 1)));

		// =============== Boundary Values Tests ==================

        // TC11: Test that the get normal of point in front of tube head work
        assertEquals(new Vector(1, 0, 0), tube.getNormal(new Point(1, 0, 0)));
	}

}
