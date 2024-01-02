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
		Sphere sphere= new Sphere(new Point(0,0,0), 2);

		assertEquals(new Vector(0.577, 0.577, 0.577), sphere.getNormal(new Point(2,3,4)));


	}
}