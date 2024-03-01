package geometries;

import java.util.List;

import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan
 */
public class Polygon extends Geometry {
   /** List of polygon's vertices */
   protected final List<Point> vertices;
   /** Associated plane in which the polygon lays */
   protected final Plane       plane;
   /** The size of the polygon - the amount of the vertices in the polygon */
   private final int           size;
   /** Minimum and maximum points of the bounding box */
   

   /**
    * Polygon constructor based on vertices list. The list must be ordered by edge
    * path. The polygon must be convex.
    * @param  vertices                 list of vertices according to their order by
    *                                  edge path
    * @throws IllegalArgumentException in any case of illegal combination of
    *                                  vertices:
    *                                  <ul>
    *                                  <li>Less than 3 vertices</li>
    *                                  <li>Consequent vertices are in the same
    *                                  point
    *                                  <li>The vertices are not in the same
    *                                  plane</li>
    *                                  <li>The order of vertices is not according
    *                                  to edge path</li>
    *                                  <li>Three consequent vertices lay in the
    *                                  same line (180&#176; angle between two
    *                                  consequent edges)
    *                                  <li>The polygon is concave (not convex)</li>
    *                                  </ul>
    */
   public Polygon(Point... vertices) {
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size          = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane         = new Plane(vertices[0], vertices[1], vertices[2]);
      // Calculate the bounding box
      minPoint = findMinPoint(vertices);
      maxPoint = findMaxPoint(vertices);

      if (size == 3) return; // no need for more tests for a Triangle

      Vector  n        = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException
      // because of Zero Vector if they are in the same point
      Vector  edge1    = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector  edge2    = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is hold by the sign of its dot product
      // with the normal. If all the rest consequent edges will generate the same sign
      // - the polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      
  }
}

  private Point findMinPoint(Point[] vertices) {
      double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, minZ = Double.MAX_VALUE;
      for (Point point : vertices) {
          minX = Math.min(minX, point.getX());
          minY = Math.min(minY, point.getY());
          minZ = Math.min(minZ, point.getZ());
      }
      return new Point(minX, minY, minZ);
  }

  private Point findMaxPoint(Point[] vertices) {
      double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE, maxZ = Double.MIN_VALUE;
      for (Point point : vertices) {
          maxX = Math.max(maxX, point.getX());
          maxY = Math.max(maxY, point.getY());
          maxZ = Math.max(maxZ, point.getZ());
      }
      return new Point(maxX, maxY, maxZ);
  }
   /**
    * Returns the normal vector of the polygon at the given point.
    *
    * @param point the point for which to return the normal vector
    * @return the normal vector of the polygon at the given point
    */
   @Override
   public Vector getNormal(Point point) {
      return plane.getNormal();
   }

   @Override
   protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
      // Check if the ray intersects the bounding box before proceeding with further calculations
      if (!isRayIntersectingBoundingBox(ray, maxDistance)) {
         return null;
      }
      Plane plane = this.plane;
      if (plane.findIntersections(ray) == null)
         return null;
      Point intersection = plane.findIntersections(ray).getFirst();
      Point p0 = ray.getHead();
      // Calculate vectors from the intersection point to each vertex of the polygon
      Vector [] v = new Vector[this.size];
      for(int i=0; i<this.size; i++) {
         v[i] = vertices.get(i).subtract(p0);
      }
      Vector [] n = new Vector[this.size];
      // Calculate normal vectors of the  polygon formed by the intersection point
      for(int i=0; i<this.size; i++) {
         if(i == this.size-1) {
            n[i] = v[i].crossProduct(v[0]).normalize();
            break;
         }
         n[i] = v[i].crossProduct(v[i+1]).normalize();
      }
      Vector v0 = ray.getDirection();
      double nv = v0.dotProduct(n[0]);
      if (isZero(nv)) return null;
      // in the first dot product, set the sign
      boolean sign = !(nv < 0);

      for(int i=1; i<this.size; i++) {
         nv = v0.dotProduct(n[i]);
         // Check if the dot product is close to zero, meaning the ray is nearly parallel to the polygon
         if (isZero(nv)) return null;
         // check if the sign is consistent
         if((nv < 0 && sign) || (nv > 0 && !sign))
            return null;
      }
      // Return the intersection point as a list
      return List.of(new GeoPoint(this, intersection));
   }
}
