package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.isZero;


//TODO: JAVA DOC
public class Camera implements Cloneable {

    private Point position;

    private Vector Vr, Vu, Vt;

    private double Height = 0.0;
    private double Width = 0.0;
    private double Dist = 0.0;

    private Camera() {

    }

    public static class Builder{

        private final Camera camera;

        public Builder() {
            this.camera = new Camera();
        }

        public Builder(Object obj) {
            if(obj instanceof Camera cam) camera = cam;
            else throw new IllegalArgumentException("object is not instanceof camera");
        }

        public Builder setLocation(Point point){
            camera.position = point;
            return this;
        }
        public Builder setDirection(Vector Vto,Vector Vup){
            if (isZero(Vto.dotProduct(Vup))) {
                camera.Vu=(Vup.normalize());
                camera.Vt=(Vto.normalize());
            } else {
                throw new IllegalArgumentException("the vectors are not vertical to each other");
            }
            return this;
        }

        public Builder setVpSize(double width, double height){
            if (width < 0 || height < 0) {
                throw new IllegalArgumentException("the width or height are negative");
            }
            camera.Width = width;
            camera.Height = height;

            return this;
        }

        public Builder setVpDistance(double dist){
            if (dist < 0) {
                throw new IllegalArgumentException("the dist is negative");
            } 
            camera.Dist = dist;
            
            return this;
        }

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

    public static Builder getBuilder() {
        return new Builder();
    }

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

    public Point getPosition() {
        return position;
    }

    public Vector getVu() {
        return Vu;
    }

    public Vector getVt() {
        return Vt;
    }

    public Vector getVr() {
        return Vr;
    }


}
