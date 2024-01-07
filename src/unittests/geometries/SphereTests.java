package unittests.geometries;

import geometries.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

class SphereTest {

	@Test
	void getNormal() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test if getnormal work
		Sphere sphere = new Sphere(new Point(0,0,0), 1);
		assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point(1,0,0)));
	}

	@Test
	void findIntsersections() {
		Ray ray=new Ray(new Point(-6,5,0),new Vector(1,0,0));
		Sphere sphere=new Sphere(new Point(0,5,0),5);
		List<Point> p = sphere.findIntsersections(ray);
		//assertArrayEquals(p,new Point(-5,5,0), new Point(5,5,0)));
	}



}