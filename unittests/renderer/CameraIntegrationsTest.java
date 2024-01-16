package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;
import renderer.*;

import java.util.List;

/**
 * This class tests the camera integrations.
 */
public class CameraIntegrationsTest {

    //List<Point> allPoints = null;

    private final Camera.Builder camera1 = Camera.getBuilder()
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1)
            .setVpSize(3, 3);

    private final Camera.Builder camera2 = Camera.getBuilder()
        .setLocation(new Point(0, 0, 0.5))
        .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
        .setVpDistance(1)
        .setVpSize(3, 3);

    
    /**
     * Calculates the number of intersection points between a camera and an intersectable shape.
     * 
     * @param camera the camera
     * @param shape the intersectable shape
     * @param nX the number of pixels in the X direction
     * @param nY the number of pixels in the Y direction
     * @return the number of intersection points
     */
    private int intersectionsNum(Camera camera, Intersectable shape, int nX, int nY) {
        int sumOfIntersectionPoints = 0;
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                List<Point> lst = shape.findIntersections(camera.constructRay(nX, nY, j, i));
                if (lst != null) {
                    sumOfIntersectionPoints += lst.size();
                }
            }
        }
        return sumOfIntersectionPoints;
    }


    /**
     * test sphere with camera
     */
    @Test
    void testSphereWithCamera() {

        // TC1: Ray through one pixel intersects the sphere one time
        Sphere sphere1 = new Sphere(new Point(0, 0, -3), 1);
        assertEquals(2, this.intersectionsNum(camera1.build(), sphere1,3,3), "Integration of sphere is incorrect");

        // TC2: Ray through 9 pixels intersects the sphere 18 times
        Sphere sphere2 = new Sphere(new Point(0, 0, -2.5), 2.5);
        assertEquals(18, this.intersectionsNum(camera2.build(), sphere2,3,3), "Integration of sphere is incorrect");

        // TC3: Ray through 5 pixels intersects the sphere 10 times
        Sphere sphere3 = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, this.intersectionsNum(camera2.build(), sphere3,3,3), "Integration of sphere is incorrect");

        // TC4: Ray through 9 pixels intersects the sphere 9 times
        Sphere sphere4 = new Sphere(new Point(0, 0, -2.5), 50);
        assertEquals(9, this.intersectionsNum(camera2.build(), sphere4,3,3), "Integration of sphere is incorrect");

        // TC5: Ray does not intersect the sphere
        Sphere sphere5 = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(0, this.intersectionsNum(camera2.build(), sphere5,3,3), "Integration of sphere is incorrect");
    }

    /**
     * test plane with camera
     */
    @Test
    void testPlaneWithCamera() {

        // TC1: Ray through 9 pixels intersects the plane 9 times
        Plane plane1 = new Plane(new Point(0,0,-5), new Point(1,1,-5), new Point(-2,-6,-5));
        assertEquals(9, this.intersectionsNum(camera1.build(), plane1,3,3), "Integration of plane is incorrect");

        // TC2: Ray through 9 pixels intersects the plane 9 times
        Plane plane2 = new Plane(new Point(0,1.5,-1), new Point(0,0,-2), new Point(1,0,-2));
        assertEquals(9, this.intersectionsNum(camera1.build(), plane2,3,3), "Integration of plane is incorrect");

        // TC3: Ray through 6 pixels intersects the plane 6 times
        Plane plane3 = new Plane(new Point(0,0.5,-1), new Point(0,0,-2), new Point(1,0,-2));
        assertEquals(6, this.intersectionsNum(camera1.build(), plane3,3,3), "Integration of plane is incorrect");

    }

    /**
     * test triangle with camera 
     */
    @Test
    void testTriangleWithCamera() {

        // TC1: Ray through one pixel intersects the triangle one time
        Triangle triangle1 = new Triangle(new Point(0,1,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
        assertEquals(1, this.intersectionsNum(camera1.build(), triangle1,3,3), "Integration of plane is incorrect");

        // TC2: Ray through two pixels intersects the triangle two times
        Triangle triangle2 = new Triangle(new Point(0,20,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
        assertEquals(2, this.intersectionsNum(camera1.build(), triangle2,3,3), "Integration of plane is incorrect");

    }


}