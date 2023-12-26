package primitives;

public class Vector extends Point {

    //throws IllegalArgumentException
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        //cords.equals(Double3.ZERO)
        if(cords == Double3.ZERO) throw new IllegalArgumentException("the zero vector not ok");
    }

    protected Vector(Double3 ObjD3) {
        super(ObjD3.d1, ObjD3.d2, ObjD3.d3);
        if(cords == Double3.ZERO) throw new IllegalArgumentException("the zero vector not ok");
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof Vector other)
        && this.cords ==  other.cords);
    }

    @Override
    public String toString() {
        return "vector:" + cords;   
    }

    
    /*
     +toString(): String
    +lengthSquared (): double
    +length(): double
    +add(Vector): Vector
    +scale(double): Vector
    +dotProduct (Vector): Vector
    +crossProduct (Vector): Vector
    +normalize(): Vector
    */
    

}
