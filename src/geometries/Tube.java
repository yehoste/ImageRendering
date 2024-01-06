package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Tube extends RadialGeometry {
    protected final Ray axis;

    /**
     * Constructs a new Tube with the specified axis and radius.
     * 
     * @param axis the axis of the tube
     * @param radius the radius of the tube
     */
    public Tube(Ray axis, double radius) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point ourPoint) {
        //the "shade" of the action point-head on the main axis
        double t = axis.getDirection().dotProduct(ourPoint.subtract(axis.getHead()));
        //border case
        if(t == 0){
            return ourPoint.subtract(axis.getHead());
        }
        //multiply the direction vector of the axis by t, and you have the point on the axis infront of the point
        return ourPoint.subtract(axis.getHead().add(axis.getDirection().scale(t))).normalize();
    }
}
