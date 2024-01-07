package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Cylinder;
import primitives.*;

class CylinderTests {

    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============

        Ray axis = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));
        double radius = 1;
        double height = 5;
        Cylinder cylinder = new Cylinder(axis, radius, height);

        // TC01: Point on the side surface of the cylinder
        Point pSide = new Point(1, 2, 0);  // On the side surface
        Vector expectedNormalSide = new Vector(1, 0, 0);  // Perpendicular to axis, pointing away from surface
        assertEquals(expectedNormalSide, cylinder.getNormal(pSide));

        // TC02: Point on the top base of the cylinder
        Point pTopBase = new Point(0, height, 1);
        Vector expectedNormalTop = new Vector(0, 1, 0);  // Parallel to axis, pointing upwards
        assertEquals(expectedNormalTop, cylinder.getNormal(pTopBase));

        // TC03: Point on the bottom base of the cylinde
        Point pBottomBase = new Point(0.5, 0, 0.5);  // On the bottom base, slightly offset from the center
        Vector expectedNormalBottom = new Vector(0, -1, 0);  // Parallel to axis, pointing downwards
        assertEquals(expectedNormalBottom, cylinder.getNormal(pBottomBase));

        // =============== Boundary Values Tests ==================

        // TC04: Point at the center of the top base
        Point pTopCenter = new Point(0, height, 0);
        assertEquals(expectedNormalTop, cylinder.getNormal(pTopCenter));

        // TC05: Point at the center of the bottom base
        Point pBottomCenter = new Point(0, 0, 0);
        assertEquals(expectedNormalBottom, cylinder.getNormal(pBottomCenter));
    }
}

