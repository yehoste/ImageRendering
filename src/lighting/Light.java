package lighting;

import primitives.*;

public abstract class Light {
    protected Color intensity;

    protected Light(Color color){
        intensity=color;
    }

    public Color getIntensity() {
        return intensity;
    }

}
