package unittests.renderer;

import static java.awt.Color.WHITE;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

public class GlossysurfacesAndBlurryGlassTests {

    private final Scene scene = new Scene("GlossysurfacesAndBlurryGlassTests");

    private final Camera.Builder camera = Camera.getBuilder()
        .setLocation(new Point(0, 200, 100))
        .setDirection(Vector.Y, Vector.Z)
        .setVpDistance(50)
        .setVpSize(1000, 700);

    @Test
    public void GlossysurfacesTests() {
        scene.geometries.add( 
        
        new Sphere((Point.ZERO), 50).setEmission(new Color(255, 0, 127))
        .setMaterial(new Material().setShininess(40).setKd(0.2).setKs(0.2)
        
            
        ), 
        new Plane(new Point(0, 0, -80), Vector.Z).setEmission(new Color(102,102,255))
        .setMaterial(new Material().setkT(0.2).setkR(0.7).setShininess(10))
        
        );

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.5))
        .setBackground(new Color(51,255,255));
        scene.lights = List.of(
            new SpotLight(new Color(255, 255, 0), new Point(0, 0, 300), new Vector(0, 0, -1))
                          .setkL(4E-4).setkQ(2E-5)
        );

        camera
         .setRayTracer(new SimpleRayTracer(scene).setBlackboardSizeAndWidthHeight(9))
         .setImageWriter(new ImageWriter("GlossysurfacesAndBlurryGlassTests", 3000, 3000))
         .build()
         .renderImage()
         .writeToImage();
        

    }



    
}
