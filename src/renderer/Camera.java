package renderer;

import primitives.*;

import java.util.MissingResourceException;

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

    /**
     * default constructor for camera.
     */
    private Camera() {}

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

        return new Ray(position, pIJ.subtract(position));
    }

    /**
     * This inner class represents a Builder for the camera.
     */
    public static class Builder{

        private final Camera camera = new Camera();

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

            camera.Vr = camera.Vt.crossProduct(camera.Vu).normalize();
            camera.viewPlaneCenter = camera.position.add(this.camera.Vt.scale(camera.Dist));

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
