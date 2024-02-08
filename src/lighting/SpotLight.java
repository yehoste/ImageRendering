package lighting;

import primitives.*;

public class SpotLight extends PointLight{
    private Vector direction;

    public SpotLight(Color color,double kc,double kl,double kq, Point point,Vector vector){
        super(color,kc,kl,kq,point);
        direction=vector;
    }
    @Override
    public SpotLight setkC(double kC) {
        return ((SpotLight) (super.setkC(kC)));
    }

    @Override
    public SpotLight setkL(double kL) {
        return ((SpotLight) (super.setkL(kL)));
    }

    @Override
    public SpotLight setkQ(double kQ) {
        return ((SpotLight) (super.setkQ(kQ)));
    }

    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }
}
