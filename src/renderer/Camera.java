package renderer;

import primitives.*;

public class Camera implements Cloneable {

    private Point position;
    private Vector Vr, Vu, Vt;
    
    private Camera() {

    }

    public static Builder getBuilder() {
        // TODO: CHANGE IT 
        return null;
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }


}
