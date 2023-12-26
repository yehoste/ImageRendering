package primitives;
import java.lang.Math;

public class Point {
    protected Double3 cords;
    /*#xyz: Double3
+equals (Object): boolean
+toString(): String
+add (Vector): Point
+subtract (Point): Vector
+distance(Point): double
+distanceSquared (Point): double*/

    public Point(double x,double y,double z){
        cords = new Double3(x,y,z);
    }

    public Point(Double3 cord){//check if works
        cords= cord;
    }


    public double distanceSquared(Point thatPoint){
        return(((this.cords.d1-thatPoint.cords.d1)*(this.cords.d1-thatPoint.cords.d1))+
                ((this.cords.d2-thatPoint.cords.d2)*(this.cords.d2-thatPoint.cords.d2))+
                ((this.cords.d3-thatPoint.cords.d3)*(this.cords.d3-thatPoint.cords.d3))
        );
    }

    public double distance(Point thatPoint){
        return Math.sqrt(this.distanceSquared(thatPoint));
    }



    @Override
    public boolean equals(Object Check){
        Point CheckIfEqual=(Point) Check;
        return ((CheckIfEqual.cords.d1==this.cords.d1) &&
                (CheckIfEqual.cords.d2==this.cords.d2) &&
                (CheckIfEqual.cords.d3==this.cords.d3));
    }


}
