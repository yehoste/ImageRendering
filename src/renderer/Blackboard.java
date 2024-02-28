/**
 * The Blackboard class represents a virtual blackboard used for rendering purposes.
 * It facilitates generating jittered points on a grid and adjusting them for circular shape.
 */
package renderer;

import static primitives.Util.isZero;
import static primitives.Util.random;

import java.util.LinkedList;
import java.util.List;

import geometries.Plane;
import primitives.*;

/**
 * The Blackboard class represents a virtual blackboard used for rendering purposes.
 * It facilitates generating jittered points on a grid and adjusting them for circular shape.
 */
public class Blackboard {

    private int Nx; // Number of divisions along the X-axis
    private int Ny; // Number of divisions along the Y-axis
    private Point CenterOfBlackB; // Center point of the blackboard
    private Vector AxisX; // X-axis vector
    private Vector AxisY; // Y-axis vector
    private Vector normal; // Normal vector of the blackboard
    public List<Point> points = new LinkedList<>(); // List of points on the blackboard
    private double width; // Width of the blackboard
    private double height; // Height of the blackboard

    /**
     * Constructs a Blackboard object with specified parameters.
     *
     * @param NumX Number of divisions along the X-axis
     * @param NumY Number of divisions along the Y-axis
     * @param CenterPoint Center point of the blackboard
     * @param axisx X-axis vector
     * @param axisy Y-axis vector
     * @param width Width of the blackboard
     * @param height Height of the blackboard
     *
     */
    public Blackboard(int NumX, int NumY, Point CenterPoint, Vector axisx, Vector axisy, double width, double height) {
        if(CenterPoint != null) this.CenterOfBlackB = CenterPoint;
        this.Nx = NumX;
        this.Ny = NumY;
        this.AxisX = axisx;
        this.AxisY = axisy;
        this.width = width;
        this.height = height;
        if (axisx == null || axisy == null) this.normal = null;
        else this.normal = axisx.crossProduct(axisy).normalize();
    }

    /**
     * Constructs a Blackboard object with specified parameters, assuming equal divisions along both axes.
     *
     * @param NumXY Number of divisions along both axes
     * @param CenterPoint Center point of the blackboard
     * @param Vto Normal vector of the blackboard
     */
    public Blackboard(int NumXY, Point CenterPoint, Vector Vto) {
        this(NumXY, NumXY, CenterPoint, null, null, NumXY+1, NumXY+1);
        this.normal = Vto;
        if (Vto != null) this.setAxis(Vto);
    }

    /**
     * Sets the center point of the blackboard.
     *
     * @param pt Center point to set
     * @return The updated Blackboard object
     */
    public Blackboard setCenterPoint(Point pt) {
        this.CenterOfBlackB = pt;
        return this;
    }

    /**
     * Generates jittered points on the blackboard.
     *
     * @return The Blackboard object with generated jittered points
     */
    public Blackboard generateJitterdPoint() {
        points = new LinkedList<>();
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                points.add(jitterdPoint(i, j));
            }
        }
        return this;
    }

    /**
     * Sets the axis vectors of the blackboard based on the provided normal vector.
     *
     * @param n Normal vector of the blackboard
     */
    public void setAxis(Vector n) {
        this.normal = n;
        List<Vector> lv = Plane.findAxisForPlane(n);
        this.AxisX = lv.get(0);
        this.AxisY =  lv.get(1);
    }

    /**
     * Retrieves the normal vector of the blackboard.
     *
     * @return The normal vector of the blackboard
     */
    public Vector getNormal() {
        return this.normal;
    }

    /**
     * Retrieves the center point of the blackboard.
     *
     * @return The center point of the blackboard
     */
    public Point getCenterPoint() {
        return CenterOfBlackB;
    }

    /**
     * Calculates a point on the blackboard grid at specified indices.
     *
     * @param j Index along the X-axis
     * @param i Index along the Y-axis
     * @return The calculated point on the blackboard grid
     */
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

    /**
     * Generates a jittered point on the blackboard grid at specified indices.
     *
     * @param j Index along the X-axis
     * @param i Index along the Y-axis
     * @return The generated jittered point
     */
    private Point jitterdPoint(int j, int i){
        Point centerOfMiniSqure = GridMethod(j, i);

        double randX = random(-((width-1) / Nx) /2 , ((width-1) / Nx)/2);
        double randY = random(-((height-1) / Ny) /2 , ((height-1) / Ny)/2);

        if (!isZero(randX)) centerOfMiniSqure = centerOfMiniSqure.add(this.AxisX.scale(randX));
        if (!isZero(randY)) centerOfMiniSqure = centerOfMiniSqure.add(this.AxisY.scale(randY));

        return centerOfMiniSqure;
    }

    /**
     * Adjusts the generated points on the blackboard to fit within a circular shape.
     */
    public void adjustForCircle() {
        for (int i = points.size() - 1; i >= 0; i--) {
            if (CenterOfBlackB.distance(points.get(i)) > Ny / 2) {
                points.remove(i);
            }
        }
    }
}
