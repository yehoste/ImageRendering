package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;
import renderer.Blackboard;


public class BlackBoardTest {

    @Test
    public void testGridMethod() {
        Blackboard board = new Blackboard(9, Point.ZERO, Vector.Z);


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