package renderer;

import primitives.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.stream.IntStream;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * This class represents a camera.
 */
public class Camera implements Cloneable {

    private Point position;

    private Vector Vr;
    private Vector Vu;
    private Vector Vt;

    private double Height = 0.0;
    private double Width = 0.0;
    private double Dist = 0.0;

    private Point viewPlaneCenter;

    private ImageWriter imageWriter;

    private RayTracerBase rayTracer;

    private int AntiAlisingX = 1;
    private int AntiAlisingY = 1;
    private final int SPARE_THREADS = 2; //  Spare threads if trying to use all the cores
    private double printInterval = 0; //  printing progress percentage interval
    private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads, 1+ number of threadsprivate final int SPARE_THREADS = 2; // Spare threads if trying to use all the coresprivate double printInterval = 0; // printing progress percentage interval


    Blackboard blackboard;

    Point pixelCenter;


    /**
     * default constructor for camera.
     */
    private Camera() {}

    record Pixel(int row, int col) {
        private static int maxRows = 0;
        private static int maxCols = 0;
        private static long totalPixels = 0L;
        private static volatile int cRow = 0;
        private static volatile int cCol = -1;
        private static volatile long pixels = 0L;
        private static volatile int lastPrinted = 0;
        private static boolean print = false;
        private static long printInterval = 100l;
        private static final String PRINT_FORMAT = "%5.1f%%\r";
        private static Object mutexNext = new Object();
        private static Object mutexPixels = new Object();


        static void initialize(int maxRows, int maxCols, double interval) {
            Pixel.maxRows = maxRows;
            Pixel.maxCols = maxCols;
            Pixel.totalPixels = (long) maxRows * maxCols;
            printInterval = (int) (interval * 10);
            if (print = printInterval != 0) System.out.printf(PRINT_FORMAT, 0d);
        }

        static Pixel nextPixel() {
            synchronized (mutexNext) {
                if (cRow == maxRows) return null;
                ++cCol;
                if (cCol < maxCols) return new Pixel(cRow, cCol);
                cCol = 0;
                ++cRow;
                if (cRow < maxRows) return new Pixel(cRow, cCol);
            }
            return null;
        }
        static void pixelDone() {
            boolean flag = false;
            int percentage = 0;
            synchronized (mutexPixels) {
                ++pixels;
                if (print) {
                    percentage = (int) (1000l * pixels / totalPixels);
                    if (percentage - lastPrinted >= printInterval) {
                        lastPrinted = percentage;
                        flag = true;
                    }
                }
            }
            if (flag) System.out.printf(PRINT_FORMAT, percentage / 10d);
        }
    }

    /**
     * get the builder for the camera.
     * @return a new builder object.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private void castRay(int nx, int ny, int i, int j) {

        Ray ray = this.constructRay(nx, ny, i, j);
        Color pixelColor = Color.BLACK;
        
        if (AntiAlisingX != 1 && AntiAlisingY != 1) {
            Point h = ray.getHead();
            blackboard.setCenterPoint(this.pixelCenter);
            blackboard.generateJitterdPoint();
            
            for (Point point : blackboard.points) {
                pixelColor = pixelColor.add(this.rayTracer.traceRay(new Ray(h, point.subtract(h))));
            }
            pixelColor = pixelColor.reduce(AntiAlisingX*AntiAlisingY);

            
        } else {
            pixelColor = this.rayTracer.traceRay(ray);
        }

        
        this.imageWriter.writePixel(i, j, pixelColor);
        Pixel.pixelDone();
    }


    public Camera renderImage() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        Pixel.initialize(nY, nX, printInterval);
        if (threadsCount == 0) {
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i);
        } else if (threadsCount == -1) {
            IntStream.range(0, nY).parallel() //
                    .forEach(i -> IntStream.range(0, nX).parallel() //
                            .forEach(j -> castRay(nX, nY, j, i)));
       } else {
            var threads = new LinkedList<Thread>();
            while (threadsCount-- > 0)
                threads.add(new Thread(() -> {
                    Pixel pixel;
                    while ((pixel = Pixel.nextPixel()) != null)
                        castRay(nX, nY, pixel.col(), pixel.row());
                }));
            for (var thread : threads) thread.start();
            try { for (var thread : threads) thread.join(); } catch (InterruptedException ignore) {}}
            return this;
        }


    /**
     * This method is used to draw a grid on the image.
     * 
     * @param interval the interval between each grid line
     * @param color    the color of the grid lines
     */
    public Camera printGrid(int interval, Color color){
        if (this.imageWriter == null) {
            throw new MissingResourceException("missing resource value", ImageWriter.class.getName(), "");
        }

        for (int i = 0; i < this.imageWriter.getNy(); i++) {
            for (int j = 0; j < this.imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
        return this;
    }

    /**
     * Writes the image data to the file.
     *
     * @throws IOException if an I/O error occurs
     */
    public void writeToImage() {
        if (this.imageWriter == null) {
            throw new MissingResourceException("missing resource value", ImageWriter.class.getName(), "");
        }
        this.imageWriter.writeToImage();
    }

    /**
     Camera.* This method constructs a ray that originates from the camera's position and points towards the center of the pixel at the given row and column indices.
    * 
    * @param nX the number of pixels in the x-direction
    * @param nY the number of pixels in the y-direction
    * @param j the row index of the pixel
    * @param i the column index of the pixel
    * @return a new ray that originates from the camera's position and points towards the center of the pixel at the given row and column indices
    */
    public Ray constructRay(int nX, int nY, int j, int i) {
        double xj = (j - ((double) (nX - 1) / 2)) * (Width / nX);
        double yi = -(i - ((double) (nY - 1) / 2)) * (Height / nY);

        Point pIJ = this.viewPlaneCenter;
        if (!isZero(xj)) pIJ = pIJ.add(this.Vr.scale(xj));
        if (!isZero(yi)) pIJ = pIJ.add(this.Vu.scale(yi));

        this.pixelCenter = pIJ;
        return new Ray(position, pIJ.subtract(position));
    }

    /**
     * This inner class represents a Builder for the camera.
     */
    public static class Builder{

        private final Camera camera = new Camera();

        public Builder setMultithreading(int threads) {
            if (threads < -2) throw new IllegalArgumentException("Multithreading must be -2 or higher");if (threads >= -1) this.camera.threadsCount = threads;
            else { // == -2
                int cores = Runtime.getRuntime().availableProcessors() - this.camera.SPARE_THREADS;
                this.camera.threadsCount = cores <= 2 ? 1 : cores;
            }
            return this;
        }
        public Builder setDebugPrint(double interval) {
            this.camera.printInterval = interval;
            return this;
        }

        /**
         * set the position of the camera.
         * @param point the position of the camera.
         * @return the builder (this).
         */
        public Builder setLocation(Point point){
            camera.position = point;
            return this;
        }

        /**
         * set the direction of the camera.
         * @param Vto the vector to the front of the camera.
         * @param Vup the vector of the up direction.
         * @return the builder (this).
         */
        public Builder setDirection(Vector Vto,Vector Vup){
            if (isZero(Vto.dotProduct(Vup))) {
                camera.Vu=(Vup.normalize());
                camera.Vt=(Vto.normalize());
            } else {
                throw new IllegalArgumentException("the vectors are not vertical to each other");
            }
            return this;
        }

        public Builder setAntiAlising(int x, int y) {
            this.camera.AntiAlisingX = x;
            this.camera.AntiAlisingY = y;
            return this;
        }

        /**
         * set the size of the camera.
         * @param width the width of the camera.
         * @param height the height of the camera.
         * @return the builder (this).
         */
        public Builder setVpSize(double width, double height){
            if (!(alignZero(width) > 0) || !(alignZero(height) > 0)) {
                throw new IllegalArgumentException("Width and height must be positive values.");
            }
            camera.Width = width;
            camera.Height = height;

            return this;
        }

        /**
         * set the distance of the camera fron the viewPlane.
         * @param dist the distance of the camera fron the viewPlane.
         * @return the builder (this).
         */
        public Builder setVpDistance(double dist){
            if (!(alignZero(dist) > 0)) {
                throw new IllegalArgumentException("the dist is negative");
            } 
            camera.Dist = dist;
            
            return this;
        }

        public Builder setRayTracer(SimpleRayTracer srt){
            camera.rayTracer = srt;
            return this;
        }

        public Builder setImageWriter(ImageWriter iw){
            camera.imageWriter=iw;
            return this;
        }

        /**
         * a build method for the Builder that build camera.
         * @return a new camera object.
         */
        public Camera build(){
            String fMsg = "missing render value(s)", cMsg = "Camera";

            if (camera.position == null) {
                throw new MissingResourceException(fMsg, cMsg, "position");
            }

            if (camera.Vt == null) {
                throw new MissingResourceException(fMsg, cMsg, "vto");
            }

            if (camera.Vu == null) {
                throw new MissingResourceException(fMsg, cMsg, "vup");
            }

            if (isZero(camera.Width)) {
                throw new MissingResourceException(fMsg, cMsg, "vpWidth");
            }

            if (isZero(camera.Height)) {
                throw new MissingResourceException(fMsg, cMsg, "vpHeight");
            }

            if (isZero(camera.Dist)) {
                throw new MissingResourceException(fMsg, cMsg, "vpDistance");
            }
            
            if (camera.rayTracer == null) {
                throw new MissingResourceException(fMsg, cMsg, "rayTracer");
            }

            if (camera.imageWriter == null) {
                throw new MissingResourceException(fMsg, cMsg, "imageWriter");
            }

            camera.Vr = camera.Vt.crossProduct(camera.Vu).normalize();
            camera.viewPlaneCenter = camera.position.add(this.camera.Vt.scale(camera.Dist));
            this.camera.blackboard = new Blackboard(this.camera.AntiAlisingX, this.camera.AntiAlisingY, null, this.camera.Vr, this.camera.Vu, this.camera.Width/ this.camera.imageWriter.getNx(), this.camera.Height/this.camera.imageWriter.getNx());

            return (Camera) camera.clone();
        }

    }

    /**
     * Returns the position of the camera.
     *
     * @return the position of the camera
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Returns the up direction of the camera.
     *
     * @return the up direction of the camera
     */
    public Vector getVu() {
        return Vu;
    }

    /**
     * Returns the right direction of the camera.
     *
     * @return the right direction of the camera
     */
    public Vector getVr() {
        return Vr;
    }


}
