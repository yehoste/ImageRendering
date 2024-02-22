package renderer;

import static primitives.Util.isZero;
import static primitives.Util.random;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import primitives.*;

public class Blackboard {

    private int Nx;

    private int Ny;

    private Point CenterOfBlackB;

    private Vector AxisX;

    private Vector AxisY;


    public List<Point> points = new LinkedList<>();
    private double width;
    private double height;
    
    public Blackboard(int NumX, int NumY, GeoPoint CenterPoint, Vector axisx, Vector axisy, double width, double height) {
        if(CenterPoint != null) this.CenterOfBlackB = CenterPoint.point;
        this.Nx = NumX;
        this.Ny = NumY;
        this.AxisX = axisx;
        this.AxisY = axisy;
        this.width = width;
        this.height = height;
    }

    public Blackboard(int NumXY, GeoPoint CenterPoint) {
        this(NumXY, NumXY, CenterPoint, null, null,NumXY+1, NumXY+1);    
        List<Vector> lv = new Plane(CenterPoint.point, CenterPoint.geometry.getNormal(CenterPoint.point)).findAxisForPlane();
        this.setAxis(lv.get(0), lv.get(1));
    }

    public Blackboard setCenterPoint(Point pt) {
        this.CenterOfBlackB = pt;
        return this;
    }

    public Blackboard generateJitterdPoint() {
        points = new LinkedList<>();
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
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
        double stepX = (width-1) / Nx;
        double stepY = (height-1) / Ny;
        
        double xj = ((j * stepX) - ((width-1) / 2)) + (stepX/2);

        double yi = ((-i * stepY) + ((height-1) / 2)) - (stepY/2);


        Point pIJ = this.CenterOfBlackB;
        if (!isZero(xj)) pIJ = pIJ.add(this.AxisX.scale(xj));
        if (!isZero(yi)) pIJ = pIJ.add(this.AxisY.scale(yi));
        
        return pIJ;
    }
    

    private Point jitterdPoint(int j, int i){
        Point centerOfMiniSqure = GridMethod(j, i);
        
        double randX = random(-((width-1) / Nx) /2 , ((width-1) / Nx)/2);
        double randY = random(-((height-1) / Ny) /2 , ((height-1) / Ny)/2);
    
        if (!isZero(randX)) centerOfMiniSqure = centerOfMiniSqure.add(this.AxisX.scale(randX));
        if (!isZero(randY)) centerOfMiniSqure = centerOfMiniSqure.add(this.AxisY.scale(randY));
    
        return centerOfMiniSqure;
    }
     

}
