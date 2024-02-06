package lighting;

import primitives.*;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC=0,kL=0,kQ=0;

    public PointLight(Color color,double kc,double kl,double kq, Point point){
        super(color);
        kC=kc;
        kL=kl;
        kQ=kq;
        position=point;
    }

    public void setkC(double kC) {
        this.kC = kC;
    }

    public void setkL(double kL) {
        this.kL = kL;
    }

    public void setkQ(double kQ) {
        this.kQ = kQ;
    }

    @Override
    public Color getIntensity(Point p) {

        return getIntensity().reduce(kC + kL * p.distance(position) + kQ * p.distanceSquared(position));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
}
