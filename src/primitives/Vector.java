package primitives;

public class Vector extends Point {

    //throws IllegalArgumentException
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        //cords.equals(Double3.ZERO)
        if(cords == Double3.ZERO) throw new IllegalArgumentException("the zero vector not ok");
    }

    public Vector(Double3 ObjD3) {
        super(ObjD3.d1, ObjD3.d2, ObjD3.d3);
        if(cords == Double3.ZERO) throw new IllegalArgumentException("the zero vector not ok");
    }

    public Vector normalize(){
        Point flag=new Point(0,0,0);
        double normal=this.distance(flag);
        Vector temp=new Vector(this.cords.d1/normal,this.cords.d2/normal,this.cords.d3/normal);
        this.subtract(this);
        this.add(temp);
        return this;
    }

    public Vector add(Vector v1) {
        return new Vector(this.cords.add(v1.cords));
    }

    public Vector scale(double d1) {
        return new Vector(this.cords.scale(d1));
    }

    /**
     * Returns the dot product of this vector and another vector.
     * 
     * @param v1 the other vector
     * @return the dot product of this vector and the other vector
     */
    public double dotProduct(Vector v1) {
        return (this.cords.d1 * v1.cords.d1 + this.cords.d2 * v1.cords.d2 + this.cords.d3 * v1.cords.d3);
    }

    public Vector crossProduct(Vector v1) {
        return new Vector(  (this.cords.d2 * v1.cords.d3 - this.cords.d3 * v1.cords.d2),
                            (this.cords.d3 * v1.cords.d1 - this.cords.d1 * v1.cords.d3), 
                            (this.cords.d1 * v1.cords.d2 - this.cords.d2 * v1.cords.d1));
    }

    public double lengthSquared() {
        return  ((this.cords.d1 * this.cords.d1) + 
                (this.cords.d2 * this.cords.d2) + 
                (this.cords.d3 * this.cords.d3));
    }     

    public double length() {
        return Math.sqrt(this.lengthSquared());
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

}
