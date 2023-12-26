package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane {
        private Point q;
        private Vector normal;


        public Plane(Point point1,Point point2,Point point3){
            this.q=point1;
            this.normal=null;
        }

        public Plane(Point ourPoint, Vector thatNormal){
                this.q=ourPoint;
                normal=thatNormal.normalize();
        }


        public Vector getNormal(){
                return normal;
        }

        public Vector getNormal(Point ourPoint){//check if correct
                return normal;
        }
}
