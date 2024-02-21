package renderer;

import static primitives.Util.isZero;
import static primitives.Util.random;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import primitives.*;

public class Blackboard {

    private int Nxy;

    private GeoPoint CenterOfBlackB;

    private Vector AxisX;

    private Vector AxisY;


    public List<Point> points = new LinkedList<>();
    private double width;
    private double height;
    
    public Blackboard(int NumXY, GeoPoint CenterPoint, Vector axisx, Vector axisy, double width, double height) {
        this.CenterOfBlackB = CenterPoint;
        this.Nxy = NumXY;
        this.AxisX = axisx;
        this.AxisY = axisy;
        this.width = width;
        this.height = height;
    }

    public Blackboard(int NumXY, GeoPoint CenterPoint, double width, double height) {
        this(NumXY, CenterPoint, null, null,width, height);    
        List<Vector> lv = new Plane(CenterPoint.point, CenterPoint.geometry.getNormal(CenterPoint.point)).findAxisForPlane();
        this.setAxis(lv.get(0), lv.get(1));
    }

    public Blackboard generateJitterdPoint() {
        for (int i = 0; i < Nxy; i++) {
            for (int j = 0; j < Nxy; j++) {
                points.add(jitterdPoint(i, j));
            }
        }
        return this;
    }

    void setAxis(Vector axisx, Vector axisy) {
        this.AxisX = axisx;
        this.AxisY = axisy;
    }

    public Point GridMethod(int j, int i) {
        double stepX = width / Nxy;
        double stepY = height / Nxy;
        
        double xj = j * stepX - width / 2;
        double yi = -i * stepY + height / 2;
    
        Point pIJ = this.CenterOfBlackB.point;
        if (!isZero(xj)) pIJ = pIJ.add(this.AxisX.scale(xj));
        if (!isZero(yi)) pIJ = pIJ.add(this.AxisY.scale(yi));
        
        return pIJ;
    }
    

    private Point jitterdPoint(int j, int i){
        Point centerOfMiniSqure = GridMethod(j, i);
        
        double randX = random(-(width / Nxy) /2 , (width / Nxy)/2);
        double randY = random(-(height / Nxy) /2 , (height / Nxy)/2);
    
        if (!isZero(randX)) centerOfMiniSqure = centerOfMiniSqure.add(this.AxisX.scale(randX));
        if (!isZero(randY)) centerOfMiniSqure = centerOfMiniSqure.add(this.AxisY.scale(randY));
    
        return centerOfMiniSqure;
    }
     

}
