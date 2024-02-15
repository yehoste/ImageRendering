package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Testing basic shadows
 * @author Dan */
public class ShadowTests {
   /** Scene of the tests */
   private final Scene          scene      = new Scene("Test scene");
   /** Camera builder of the tests */
   private final Camera.Builder camera     = Camera.getBuilder()
      .setDirection(new Vector(0, 0, -1), Vector.Y)
      .setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
      .setVpSize(200, 200)
      .setRayTracer(new SimpleRayTracer(scene));

   /** The sphere in the tests */
   private final Intersectable  sphere     = new Sphere(new Point(0, 0, -200), 60d)
      .setEmission(new Color(BLUE))
      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
   /** The material of the triangles in the tests */
   private final Material       trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);

   /** Helper function for the tests in this module
    * @param pictName     the name of the picture generated by a test
    * @param triangle     the triangle in the test
    * @param spotLocation the spotlight location in the test */
   private void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
      scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
      scene.lights.add( //
                       new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
                          .setkL(1E-5).setkQ(1.5E-7));
      camera.setImageWriter(new ImageWriter(pictName, 400, 400))
         .build()
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere and triangle with point light and shade */
   @Test
   public void sphereTriangleInitial() {
      sphereTriangleHelper("shadowSphereTriangleInitial", //
                           new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                           new Point(-100, -100, 200));
   }

   /** Sphere-Triangle shading - move triangle up-right */
   @Test
   public void sphereTriangleMove1() {
      sphereTriangleHelper("shadowSphereTriangleMove2", //
                           new Triangle(new Point(-49, -19, 0), new Point(-19, -49, 0), new Point(-47, -47, -4)), //
                           new Point(-100, -100, 200));
   }

   /** Sphere-Triangle shading - move triangle upper-righter */
   @Test
   public void sphereTriangleMove2() {
      sphereTriangleHelper("shadowSphereTriangleMove1", //
                           new Triangle(new Point(-62, -32, 0), new Point(-32, -62, 0), new Point(-60, -60, -4)), //
                           new Point(-100, -100, 200));
   }

   /** Sphere-Triangle shading - move spot closer */
   @Test
   public void sphereTriangleSpot1() {
      sphereTriangleHelper("shadowSphereTriangleSpot1", //
                           new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                           new Point(-100, -100, 200));
   }

   /** Sphere-Triangle shading - move spot even more close */
   @Test
   public void sphereTriangleSpot2() {
      sphereTriangleHelper("shadowSphereTriangleSpot2", //
                           new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                           new Point(-88, -88, 120));
   }

   /** Produce a picture of a two triangles lighted by a spot light with a Sphere
    * producing a shading */
   @Test
   public void trianglesSphere() {
      scene.geometries.add(
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                           new Sphere(new Point(0, 0, -11), 30d) //
                              .setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
      );
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.lights.add(
                       new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                          .setkL(4E-4).setkQ(2E-5));

      camera.setImageWriter(new ImageWriter("shadowTrianglesSphere", 1, 1))
         .build()
         .renderImage()
         .writeToImage();
   }

}