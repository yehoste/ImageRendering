package scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import geometries.Geometries;
import geometries.Intersectable;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import primitives.Point;

public class Scene {

    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    public List<LightSource> lights = new LinkedList<LightSource>();

    private int kSize;

    public Scene(String name) {
        this.name = name;

    }

    public Scene setKMeans(int kSize) {
        this.kSize = kSize;
        this.geometries.setBvh();
        return this;
    }

    private static class KMeans {
        private List<List<Integer>> cluster(List<Point> points, int k) {
            List<List<Integer>> clusters = new ArrayList<>();

            // Step 1: Initialize centroids randomly
            List<Point> centroids = initializeCentroids(points, k);

            // Iterate until convergence
            boolean converged = false;
            while (!converged) {
                // Step 2: Assign each point to the nearest centroid
                List<List<Integer>> newClusters = assignPointsToCentroids(points, centroids);

                // Step 3: Update centroids based on the mean of the points in each cluster
                List<Point> newCentroids = updateCentroids(points, newClusters);

                // Step 4: Check for convergence
                converged = centroids.equals(newCentroids);

                centroids = newCentroids;
                clusters = newClusters;
            }

            return clusters;
        }

        private List<Point> initializeCentroids(List<Point> points, int k) {
            // Randomly select k points as initial centroids
            List<Point> centroids = new ArrayList<>();
            Random rand = new Random();
            for (int i = 0; i < k; i++) {
                int index = rand.nextInt(points.size());
                centroids.add(points.get(index));
            }
            return centroids;
        }

        private List<List<Integer>> assignPointsToCentroids(List<Point> points, List<Point> centroids) {
            // Assign each point to the nearest centroid
            List<List<Integer>> clusters = new ArrayList<>();
            for (int i = 0; i < centroids.size(); i++) {
                clusters.add(new ArrayList<>());
            }
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                double minDistance = Double.POSITIVE_INFINITY;
                int clusterIndex = -1;
                for (int j = 0; j < centroids.size(); j++) {
                    double distance = point.distance(centroids.get(j));
                    if (distance < minDistance) {
                        minDistance = distance;
                        clusterIndex = j;
                    }
                }
                clusters.get(clusterIndex).add(i);
            }
            return clusters;
        }

        private List<Point> updateCentroids(List<Point> points, List<List<Integer>> clusters) {
            // Update centroids based on the mean of the points in each cluster
            List<Point> centroids = new ArrayList<>();
            for (List<Integer> cluster : clusters) {
                if (cluster.isEmpty()) {
                    // If a cluster is empty, keep the centroid unchanged
                    centroids.add(new Point(0, 0, 0)); // Add dummy point, not used in distance calculation
                    continue;
                }
                double sumX = 0, sumY = 0, sumZ = 0;
                for (int index : cluster) {
                    Point point = points.get(index);
                    sumX += point.getX();
                    sumY += point.getY();
                    sumZ += point.getZ();
                }
                double meanX = sumX / cluster.size();
                double meanY = sumY / cluster.size();
                double meanZ = sumZ / cluster.size();
                centroids.add(new Point(meanX, meanY, meanZ));
            }
            return centroids;
        }
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries The geometries to be set.
     * @return The Scene object with the updated geometries.
     */

    public Scene setGeometries(Geometries geometries) {
        if (this.geometries.getBvh() == false) {
            this.geometries = geometries;
            return this;
        }

        List<Intersectable> geometriesList = geometries.geometriesList;

        // calculate centers of objects
        List<Point> centers = new ArrayList<>();
        for (Intersectable i : geometriesList) {
            Point avg = new Point((i.getMinPoint().getX() + i.getMaxPoint().getX()) / 2,
                                  (i.getMinPoint().getY() + i.getMaxPoint().getY()) / 2,
                                  (i.getMinPoint().getZ() + i.getMaxPoint().getZ()) / 2);
            centers.add(avg);
        }

        KMeans kMeans = new KMeans();

        // Perform k-means clustering
        List<List<Integer>> clusters = kMeans.cluster(centers, kSize);

        // Build hierarchy based on clusters
        Geometries hierarchies = new Geometries();
        for (List<Integer> cluster : clusters) {
            Geometries clusterObjects = new Geometries();
            for (int index : cluster) {
                clusterObjects.add(geometriesList.get(index));
            }
            if (clusterObjects.geometriesList.size() == 1) {
                hierarchies.add(new Geometries(clusterObjects.geometriesList.get(0)));
            } else {
                hierarchies.add(new Geometries(clusterObjects.geometriesList.toArray(new Intersectable[0])));
            }
        }
        this.geometries = hierarchies;
        return this;
    }

    /**
     * Sets the list of light sources in the scene.
     * 
     * @param lights the list of light sources
     * @return the current scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

}