package unittests.renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;

import geometries.Triangle;
import lighting.PointLight;
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
			.setLocation(new Point(0, 0, 2000)).setVpDistance(1000)
			.setVpSize(200, 200)
			.setRayTracer(new SimpleRayTracer(scene).setSoftShadowBbSize(17));

	/** The sphere in the tests */
	private final Intersectable sphere     = new Sphere(new Point(0, 0, -200), 60d)
			.setEmission(new Color(BLUE))
			.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
	/** The material of the triangles in the tests */
	private final Material       trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);
	@Test
	public void blurryTest(){
		scene.geometries.add(
				new Sphere( new Point(0, 0, 0),50d).setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10))
						.setEmission(new Color(0,123,123)),
				new Triangle(new Point(-10, 0, 70), new Point(0, 90, 40), new Point(80, 0, 50))
						.setMaterial(new Material().setkT(0.3).setBlurriness(0)).setEmission(new Color(GRAY)),
				new Triangle(new Point(-10, 0, 70), new Point(0, 90, 40), new Point(-80, 0, 50))
						.setMaterial(new Material().setkT(0.3).setBlurriness(9)).setEmission(new Color(GRAY)),
				new Triangle(new Point(-10, 0, 70), new Point(0,-90, 40), new Point(-80, 0, 50))
						.setMaterial(new Material().setkT(0.3).setBlurriness(40)).setEmission(new Color(GRAY)),
				new Triangle(new Point(-10, 0, 70), new Point(0,-90, 40), new Point(80, 0, 50))
						.setMaterial(new Material().setkT(0.3).setBlurriness(81)).setEmission(new Color(GRAY))
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
				new Sphere( new Point(0, 0, 0), 50d).setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10))
						.setEmission(new Color(130, 80, 0)),
				new Plane(new Point(70, 0, -140), new Vector(-0.35, 0, 1))
						.setMaterial(new Material().setGlossiness(80).setkR(0.9)).setEmission(new Color(0, 30, 50)));

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