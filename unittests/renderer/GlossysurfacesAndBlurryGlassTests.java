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
           .setAntiAlising(2, 2)
           .setRayTracer(new SimpleRayTracer(scene).setBlackboardSizeAndWidthHeight(0));
    

    @Test
    public void GlossysurfacesTests() {
        
        scene.geometries.add(
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                              .setEmission(new Color(10,220,136))
                              .setMaterial(new Material().setKs(0.8).setkR(0.8).setShininess(60)), //
                           
                           new Sphere(new Point(0, 0, 80), 30d) //
                              .setEmission(new Color(30, 50, 80)) //
                              .setMaterial(new Material().setKd(0.6).setKs(0.8).setkT(1).setShininess(30)) //
      );

      //scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.5));

      //scene.setBackground(new Color(LIGHT_GRAY));

      scene.lights.add(
                       new SpotLight(new Color(127, 255, 125), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                          .setkL(4E-4).setkQ(2E-5).setNarrowBeam(5));

      camera.setImageWriter(new ImageWriter("GlossysurfacesTestsAA", 500, 500))
         .build()
         .renderImage()
         .writeToImage();
    }



    
}
