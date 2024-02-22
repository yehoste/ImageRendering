package unittests.renderer;

import static java.awt.Color.BLUE;

import static java.awt.Color.WHITE;



import org.junit.jupiter.api.Test;


import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

public class GlossysurfacesAndBlurryGlassTests {

    private final Scene scene = new Scene("GlossysurfacesAndBlurryGlassTests");

        /** Camera builder of the tests */ 
    private final Camera.Builder camera     = Camera.getBuilder()
           .setDirection(new Vector(0, 0, -1), Vector.Y)
           .setLocation(new Point(0, 0, 1500)).setVpDistance(1000)
           .setVpSize(200, 200)
           .setAntiAlising(9, 9)
           .setRayTracer(new SimpleRayTracer(scene).setBlackboardSize(2));
    

    @Test
    public void GlossysurfacesTests() {
        
        scene.geometries.add(
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                              .setEmission(new Color(255,255,255))
                              .setMaterial(new Material().setKs(0.8).setkR(0.8).setShininess(200)), //
                           
                           new Sphere(new Point(0, 0, 80), 40d) //
                              .setEmission(new Color(80, 20, 50)) //
                              .setMaterial(new Material().setKd(0.6).setKs(0.8).setkT(0.6).setShininess(30)), //

                           new Sphere(new Point(0, 0, 80), 10d).setEmission(new Color(80, 20, 50)).setMaterial(new Material().setKd(0.6).setKs(0.8).setShininess(60))
      );

      //scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.5));

      //scene.setBackground(new Color(LIGHT_GRAY));

      scene.lights.add(
                       new SpotLight(new Color(127, 255, 125), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                          .setkL(4E-4).setkQ(2E-5).setNarrowBeam(5));

      camera
        .setImageWriter(new ImageWriter("GlossysurfacesTestsAA", 1000, 1000))
         .build()
         .renderImage()
         .writeToImage();
    }



    
}
