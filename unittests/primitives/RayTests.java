package unittests.primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.*;

import java.util.List;

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

    @Test
    void testFindClosestPoint() {

        Point p1 = new Point(1, 1, 2);
        Point p2 = new Point(1, 1, 3);
        Point p3 = new Point(1, 1, 4);

        // ============ Equivalence Partitions Tests ==============
        // TC1: The closest point is in the middle of the list
        assertEquals(p1, ray.findClosestPoint(List.of(p2, p1, p3)), "findClosestPoint() is incorrect");

        // =============== Boundary Values Tests ==================
        // TC2: Empty list of points
        assertNull(ray.findClosestPoint(List.of()), "findClosestPoint() is incorrect");
        // TC3: The closest point is the first point in the list
        assertEquals(p1, ray.findClosestPoint(List.of(p1, p2, p3)), "findClosestPoint() is incorrect");
        // TC4: The closest point is the last point in the list
        assertEquals(p1, ray.findClosestPoint(List.of(p2, p3, p1)), "findClosestPoint() is incorrect");

    }
}
