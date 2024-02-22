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
        Blackboard board = new Blackboard(9, new GeoPoint(new Plane(Point.ZERO, Vector.Z), Point.ZERO));


        Point bottomLeft = board.GridMethod(0, 0);
        assertEquals(new Point(-4, -4, 0), bottomLeft);


        Point topLeft = board.GridMethod(0, 1);
        assertEquals(new Point(-3, -4, 0), topLeft);

        Point topRight = board.GridMethod(4, 4);
        assertEquals(new Point(0, 0, 0), topRight);


        Point bottomRight = board.GridMethod(8, 8);
        assertEquals(new Point(4, 4, 0), bottomRight);
    }

    @Test
    public void testJitterdPoint() {
        
    }

}