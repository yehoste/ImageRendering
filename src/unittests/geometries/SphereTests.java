package unittests.geometries;

import geometries.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

class SphereTest {

	@Test
	void getNormal() {
		Sphere sphere = new Sphere(new Point(0,0,0), 1);

		assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point(1,0,0)));
	}
}