package unittests.primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.*;

/**
 * unit test class for Ray
 */
class RayTests {

    Ray ray = new Ray(new Point(0,0,0), new Vector(0,0,1));

    /*
     * Test Method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetPoint() {
        // ============ Equivalence Partitions Tests ==============

        //TC01 checks if positive
        assertEquals(new Point(0,0,1), ray.getPoint(1), "getPoint() is incorrect");

        //TC02 checks if throws the if negiteve
        assertThrowsExactly(IllegalArgumentException.class, () -> ray.getPoint(-1),"ERROR: does no throw exeption if negetive");

        // =============== Boundary Values Tests ==================
        //TC11 checks if work on zero
        assertEquals(ray.getHead(), ray.getPoint(0), "Not work on Zero");
    }
}
