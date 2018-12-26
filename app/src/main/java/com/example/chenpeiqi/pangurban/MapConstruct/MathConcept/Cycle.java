package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

/**
 * Created on 2018/3/14.
 */
public class Cycle extends Contour {

    private Cord heart;
    private double radius;

    public Cycle(Cord heart,double radius) {//用于计算圆上数值的场合
        this.heart = heart; this.radius = radius;
    }

    public Cord getHeart() {
        return heart;
    }

    public double getRadius() {
        return radius;
    }

    public Cord[] pointedOneGetOthers(boolean xoy,double pointedAs) {
        double heartX = heart.getX(), heartY = heart.getY(),
              chosen = xoy? heartX: heartY, r = getRadius(),
              delta = pointedAs-(xoy? heartX: heartY),
              pfg = Math.sqrt(r*r-delta),//平方根
              result0 = chosen+pfg, result1 = chosen-pfg;
        return xoy? new Cord[]{new Cord(pointedAs,result0),new Cord(pointedAs,result1)}:
              new Cord[]{new Cord(result0,pointedAs),new Cord(result1,pointedAs)};

    }

    public Cord randomIn() {//圆内产生一个随机点
        int rx = (int) (heart.getX()-radius+radius*2*Math.random()),
              ry = (int) (heart.getY()-radius+radius*Math.random());
        return new Cord(rx,ry);
    }

    public Cord randomOn() {//圆弧上产生随机点
        int rx = (int) (heart.getX()-radius+radius*Math.random());
        Cord[] couldBes = pointedOneGetOthers(true,rx);
        return couldBes[(int) (Math.random()/0.5)];
    }

    public Cord getEndFromStart(Cord start) {
        QUAD staQUAD = QUAD.calQuad(start,heart), endQUAD = QUAD.switchQuad(staQUAD);
        double finalX = heart.getX()+
              (QUAD.aboveAverage(staQUAD,true)? 1: -1)*(Math.random()*getRadius());
        return pointedOneGetOthers(true,finalX)[(QUAD.aboveAverage(endQUAD,false)? 0: 1)];
    }

}