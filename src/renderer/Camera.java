package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

public class Camera implements Cloneable {

    private Point position;
    private Vector Vr, Vu, Vt;

    double Height=0.0,Width=0.0,Dist=0.0;
    
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
            camera.position=point;
            return this;
        }
        public Builder setDirection(Vector V_t,Vector V_u){
            if (V_t.dotProduct(V_u)==1){
                camera.Vu=(V_u.normalize());
                camera.Vt=(V_t.normalize());
            }
            else {
                throw new IllegalArgumentException("the vectors are not vertical to each other");
            }
            return this;
        }

        public Builder setVpSize(double width, double height){
            if (width<0 || height<0){
                throw new IllegalArgumentException("the width or height are negative");
            }
            camera.Width=width;
            camera.Height=height;
            return this;
        }

        public Builder setVpDistance(double dist){
            if (dist<0){
                throw new IllegalArgumentException("the dist is negative");
            }
            camera.Dist=dist;
            return this;
        }

        public Camera build(){
            final String comment="there is no value in this argument" ;
            if (camera.position==Point.ZERO){
                throw new MissingResourceException(comment,"camera","position");
            }
            if(camera.Dist==0.0){
                throw new MissingResourceException(comment,"camera","Dist");
            }
            if(camera.Height==0.0){
                throw new MissingResourceException(comment,"camera","Height");
            }
            if(camera.Width==0.0){
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
        // TODO: CHANGE IT 
        return null;
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }


}
