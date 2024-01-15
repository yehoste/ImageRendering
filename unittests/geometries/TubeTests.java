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
    void getNormal() {
        // Creating a tube with radius 1 and a ray starting from (0,0,-1) in the direction (0,0,1)
        Tube tube1 = new Tube( new Ray(new Point(0, 0, -1), new Vector(0, 0, 1)), 1);
        
        // Expected normal for a point (0,1,0) on the tube
        Vector expected1 = new Vector(0, 1, 0);
        
        // Calculating the actual normal for the given point
        Vector result1 = tube1.getNormal(new Point(0, 1, 0));
        
        // Checking if the actual normal matches the expected normal
        assertEquals(expected1, result1, "ERROR: incorrect normal to point for tube.");

        // =============== Boundary Values Tests ==================
        
        // Creating another tube with radius 1 and a ray starting from the origin (0,0,0) in the direction (0,0,1)
        Tube tube2 = new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1);
        
        // Expected normal for a point (0,1,0) on the tube in this case
        Vector expected2 = new Vector(0, 1, 0);
        
        // Calculating the actual normal for the given point
        Vector result2 = tube2.getNormal(new Point(0, 1, 0));
        
        // Checking if the actual normal matches the expected normal
        assertEquals(expected2, result2, "ERROR: incorrect normal to point for tube in the case that point is in front of the origin of the defining ray.");
    }

}
