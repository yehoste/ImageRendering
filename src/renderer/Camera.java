package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.isZero;


/**
 * This class represents a camera.
 */
public class Camera implements Cloneable {

    private Point position;

    private Vector Vr, Vu, Vt;

    private double Height = 0.0;
    private double Width = 0.0;
    private double Dist = 0.0;

    /**
     * default constructor for camera.
     */
    private Camera() {}

    /**
     * This inner class represents a Builder for the camera.
     */
    public static class Builder{

        private final Camera camera;

        /**
         * constructor for the builder. 
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * copy constructor for the builder.
         * @param obj
         */
        public Builder(Object obj) {
            if(obj instanceof Camera cam) camera = cam;
            else throw new IllegalArgumentException("object is not instanceof camera");
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

        /**
         * set the size of the camera.
         * @param width the width of the camera.
         * @param height the height of the camera.
         * @return the builder (this).
         */
        public Builder setVpSize(double width, double height){
            if (width < 0 || height < 0) {
                throw new IllegalArgumentException("the width or height are negative");
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
            if (dist < 0) {
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
            final String comment="there is no value in this argument" ;
            if (camera.position == null){
                throw new MissingResourceException(comment,"camera","position");
            }
            if(isZero(camera.Dist)){
                throw new MissingResourceException(comment,"camera","Dist");
            }
            if(isZero(camera.Height)){
                throw new MissingResourceException(comment,"camera","Height");
            }
            if(isZero(camera.Width)){
                throw new MissingResourceException(comment,"camera","Width");
            }
            camera.Vr=camera.Vu.subtract(camera.position).crossProduct(camera.Vt.subtract(camera.position)).normalize();
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * get the builder for the camera.
     * @return a new builder object.
     */
    public static Builder getBuilder() {
        return new Builder();
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
        // pCenter is the point in the center of the view plane
        Point pCenter = this.position.add(this.Vt.scale(this.Dist));

        // pixels size
        double ratioX = this.Width / nX;
        double ratioY = this.Height / nY;

        // the center of P[i,j] pixel
        Point pIJ = pCenter;                            // In case that pCenter is exactly P[i,j] pixel
        double yI = -(i - (nY-1) / 2d) * ratioY;        // The distance from pCenter to p[i,j] pixel's center in the y-axis
        double xJ = (j - (nX-1) / 2d) * ratioX;         // The distance from pCenter to p[i,j] pixel's center in the x-axis


        if (!isZero(xJ)) {
            pIJ = pIJ.add(this.Vr.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(this.Vu.scale(yI));
        }

        Vector vIJ = pIJ.subtract(this.position); // vector to the center of the pixel

        return new Ray(this.position, vIJ);
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
