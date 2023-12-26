package primitives;

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

    public Vector subtract(Point ourPoint){
        return new Vector(this.cords.subtract(ourPoint.cords));
    }

    public Point add(Vector v1) {
        return new Point(v1.cords.add(this.cords));
    }








    @Override
    public boolean equals(Object Check){
        Point CheckIfEqual=(Point) Check;
        return ((CheckIfEqual.cords.d1==this.cords.d1) &&
                (CheckIfEqual.cords.d2==this.cords.d2) &&
                (CheckIfEqual.cords.d3==this.cords.d3));
    }


}
