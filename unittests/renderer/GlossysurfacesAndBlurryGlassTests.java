package unittests.renderer;

import geometries.Plane;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Test rendering an image
 *
 * @author Dan
 */
public class GlossysurfacesAndBlurryGlassTests{

	private final Scene          scene      = new Scene("Test scene");
	/** Camera builder of the tests */
	private final Camera.Builder camera     = Camera.getBuilder()
			.setDirection(new Vector(0, 0, -1), Vector.Y)
			.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
			.setVpSize(200, 200)
			.setRayTracer(new SimpleRayTracer(scene));

	@Test
	public void blurryTest(){
		scene.geometries.add(
				new Sphere( new Point(0, 0, -200),50d).setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(30))
						.setEmission(new Color(0, 255, 0)),
				new Triangle(new Point(0, 0, 0), new Point(0, 90, 0), new Point(-90, 0, 0))
						.setMaterial(new Material().setkT(0.3).setBlurriness(0)).setEmission(new Color(252, 150, 240)),
				new Triangle(new Point(0, 0, 0), new Point(0, -90, 0), new Point(-90, 0, 0))
						.setMaterial(new Material().setkT(0.3).setBlurriness(3)).setEmission(new Color(252, 150, 240)),
				new Triangle(new Point(0, 0, 0), new Point(0,-90, 0), new Point(90, 0, 0))
						.setMaterial(new Material().setkT(0.3).setBlurriness(6)).setEmission(new Color(252, 150, 240)),
				new Triangle(new Point(0, 0, 0), new Point(0,90, 0), new Point(90, 0, 0))
						.setMaterial(new Material().setkT(0.3).setBlurriness(9)).setEmission(new Color(252, 150, 240))
		);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.setBackground(new Color(30, 10, 0));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));

		camera.setImageWriter(new ImageWriter("blurry", 500, 500))
				.build()
				.renderImage() //
				.writeToImage();
	}

	@Test
	public void glossyTest() {
		scene.geometries.add(
				new Sphere( new Point(0, 0, 0), 50d).setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(40))
						.setEmission(new Color(RED)),
				new Plane(new Point(70, 0, -140), new Vector(-0.35, 0, 1))
						.setMaterial(new Material().setGlossiness(6).setkR(0.9)).setEmission(new Color(0, 0, 207)));

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.setBackground(new Color(30, 10, 0));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));

		camera.setImageWriter(new ImageWriter("glossy", 500, 500))
				.build()
				.renderImage() //
				.writeToImage();
	}
}