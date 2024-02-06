package lighting;

import primitives.*;

public class SpotLight extends PointLight{
    private Vector direction;

    public SpotLight(Color color,double kc,double kl,double kq, Point point,Vector vector){
        super(color,kc,kl,kq,point);
        direction=vector;
    }
    @Override
    public void setkC(double kC) {
        super.setkC(kC);
    }

    @Override
    public void setkL(double kL) {
        super.setkL(kL);
    }

    @Override
    public void setkQ(double kQ) {
        super.setkQ(kQ);
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
