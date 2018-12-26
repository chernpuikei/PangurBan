package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

import java.util.ArrayList;

/**
 * Created on 2018/3/14.
 */
public class Line {

    private double a, b, angel, dx, dy;
    private Cord start, end;//当要取的是线段而不是直线的时候p0、p1是线段的两个端点
    private int staX, staY, endX, endY;

    public Line(Cord start,Cord end) {//两点连线
        this.start = start;
        this.end = end;
        staX = start.getX(); staY = start.getY();
        endX = end.getX(); endY = end.getY();
        dx = end.getX()-start.getX();
        dy = end.getY()-start.getY();
        a = (double) (end.getY()-start.getY())/(end.getX()-start.getX());
        b = end.getY()-a*end.getX();
        angel = Math.toDegrees(Math.atan(a));
//        MyLog.info("line>>");MyLog.info(2,"start",start.toString()); MyLog.info(2,"end",end.toString());
//        MyLog.info("p1y-p0y",end.getCyHeartY()-start.getCyHeartY()); MyLog.info("p1x-p0x",end.getCyHeartX()-start.getCyHeartX());
//        MyLog.info(2,"a",a); MyLog.info(2,"b",b);
    }

    Line(Cord start,Line line) {//过点p0作line的垂线
        a = -1/line.getA();
        b = start.getY()-a*start.getX();
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getAngel() {
        return angel;
    }

    double getX(double y) {
        return (y-b)/a;
    }

    public double getY(double x) {
        return a*x+b;
    }

    double getLineDis() {
        int sx = start.getX(), sy = start.getY(), ex = end.getX(), ey = end.getY();
        double delHor = Math.abs(sx-ex), delVer = Math.abs(sy-ey);
        return Math.sqrt(delHor*delHor+delVer*delVer);
    }

    public Cord getPercentageCord(double progress) {
        double x = start.getX()+dx*progress, y = start.getY()+dy*progress;
//        MyLog.info(3,"getPercentageCord>>");
//        MyLog.info(4,"line.start",start.toString());
//        MyLog.info(4,"line.end",end.toString());
//        MyLog.info(4,"progress",progress);
//        MyLog.info(4,"dx/dy",dx+"/"+dy);
//        MyLog.info(4,"final cord",x+"/"+y);
        return new Cord(x,y);
    }

    ArrayList<Cord> getSplCord(int splitCount) {
        ArrayList<Cord> result = new ArrayList<>();
        double percentage = (double) 1/(splitCount+1);
        for (int i = 1;i<=splitCount;i++) {
            result.add(getPercentageCord(percentage*i));
        }
        return result;
    }

    public String toString() {
        return "a:"+a+",b:"+b;
    }

    ArrayList<Cord> getEachPoint(int pointDis) {
        ArrayList<Cord> result = new ArrayList<>();
        double lineDis = getLineDis();
        for (int i = 0;i<lineDis;i += pointDis) {
            result.add(getPercentageCord(i/lineDis));
        }
        return result;
    }

    Cord randomOnRange(Cord[] cords,boolean whichSide) {
        Cord first = cords[0], second = cords[1];
        int x1 = first.getX(), x2 = second.getX(),
              y1 = first.getY(), y2 = second.getY(),
              avX = (x1+x2)/2, avY = (y1+y2)/2,
              minX = x1>x2? x2: x1, delX = Math.abs(x2-x1),
              minY = y1>y2? y2: y1, delY = Math.abs(y2-y1),
              halfDX = delX/2, halfDY = delY/2;
        boolean[] gx = new boolean[]{x1<avX,y1<avY},
              ngx = whichSide? gx: new boolean[]{!gx[0],!gx[1]};
        Cord rs = new Cord(avX+(int) (Math.random()*halfDX)*(ngx[0]? -1: 1)
              ,avY+(int) (Math.random()*halfDY)*(ngx[1]? -1: 1));

        return rs;

    }


}
