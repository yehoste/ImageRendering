package unittests.geometries;
import geometries.*;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PlaneTest {
	@Test
	void testConstructor(){
		// =============== Boundary Values Tests ==================
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(0, 0 ,3),new Point (0,0,3), new Point (1,3,3)),
				"ERROR: constructor does not throw error in illegal definition- 2 correlating points.");
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 1 ,1),new Point (2,2,2), new Point (0,0,0)),
				"ERROR: constructor does not throw error in illegal definition - all points on same line.");
	}

	@Test
	void getNormal() {
		Plane plane=new Plane(new Point(1,0,0), new Point(0,1,0), new Point(0,0,1) );
		Vector normal=plane.getNormal(new Point(2,-1,0));
		assertThrows(IllegalArgumentException.class,
				()->(new Vector(1,1,1)).crossProduct(normal),
				"ERROR: plane doesn't return normal in the correct direction.");

		assertEquals(1,normal.lengthSquared(),0.000001 , "ERROR: normal to plane isn't normalised." );



	}
}