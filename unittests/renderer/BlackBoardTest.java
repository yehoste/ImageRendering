package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Intersectable.GeoPoint;
import primitives.Point;
import primitives.Vector;
import renderer.Blackboard;


public class BlackBoardTest {

    @Test
    public void testGridMethod() {
        Blackboard board = new Blackboard(3, new GeoPoint(new Plane(Point.ZERO, Vector.Z), Point.ZERO), 2, 2);

        // Test center point
        Point bottomLeft = board.GridMethod(0, 0);
        assertEquals(new Point(-0.5, -0.5, 0), bottomLeft);

        // Test corner points
        Point topLeft = board.GridMethod(0, 1);
        assertEquals(new Point(-0.5, 0.5, 0), topLeft);

        Point topRight = board.GridMethod(1, 1);
        assertEquals(new Point(0.5, 0.5, 0), topRight);

        // Test points off center
        Point bottomRight = board.GridMethod(1, 0);
        assertEquals(new Point(0.5, -0.5, 0), bottomRight);
    }

    @Test
    public void testJitterdPoint() {
        
    }

}