package renderer;

import primitives.*;

import java.util.MissingResourceException;

public class Camera implements Cloneable {

    private Point position;
    private Vector Vr, Vu, Vt;
    
    private Camera() {

    }
    public static class Builder{
        private final Camera camera= new Camera();

        public Builder() {
            this.camera= new Camera();
        }

        public Builder(Object obj) {
            if(obj instanceof Camera cam) camera = cam;
            else throw new IllegalArgumentException("object is not instanceof camera")
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
            return this;
        }

        public Builder setVpDistance(double dist){
            return this;
        }

        public Camera build(){
            final String comment="there is no value in this argument" ;
            if (camera.position==Point.ZERO){
                throw new MissingResourceException(comment,"camera","position");
            }
            if (camera.Vu==)
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
