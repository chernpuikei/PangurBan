package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

import com.example.chenpeiqi.pangurban.MapConstruct.MapElement.MyMap;

/**
 * Created on 2018/3/12.
 */
public class Cord {

    double x, y, stageDis, angel;

    public Cord(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public Cord(double x,double y) {
        this.x = x;
        this.y = y;
    }

    public Cord(float[] cord) {
        this.x = (int) cord[0];
        this.y = (int) cord[1];
    }

    public Cord(Cord project,Cord cen) {//用于生成project在由cen产生的坐标轴逆时针方向的投影
        QUAD quad = QUAD.calQuad(project,cen);
        switch (quad) {
            case ONE:
            case THREE:
                this.x = cen.getX();
                this.y = project.getY();
                break;
            case TWO:
            case FOUR:
                this.x = project.getX();
                this.y = cen.getY();
                break;
        }
//        MyLog.info(10,"Cord>>(project)");
//        MyLog.info(12,"projected",x0+"/"+y0);
//        MyLog.info(10,"<<");
    }

    public Cord(int czDis,int width,int height) {//在屏幕边缘用线长构筑坐标

        //逐步构筑所有stageDis，和czDis比对，确定当前czDis所处位置
        int hw = width/2, hh = height/2;
        int[] pool = new int[]{hw,hh};
        int picker = 0, status;
        for (status = 0;status<8;status++) {//遍历每个stage

            boolean switcher = status%2==0;//逢2更换序号
            if (switcher) picker = 1-picker;//更换后的序号
            stageDis += pool[picker];//根据序号计算出当前stage的czDis

            if (czDis<stageDis) {
                stageDis -= pool[picker];
                break;
            }
        }

        switch (status) {
            case 0:
            case 1:
                this.x = 0;
                this.y = czDis;
                break;
            case 2:
            case 3:
                this.x = czDis-height;
                this.y = height;
                break;
            case 4:
            case 5:
                this.x = width;
                this.y = height-(czDis-width-height);
                break;
            default:
                this.x = czDis-height*2-width;
                this.y = 0;
        }

    }

    public Cord(QUAD quad,ORIEN ORIEN,int width,int height) {
        boolean posHov = ORIEN.getHorOrVer(), posMol = ORIEN.getMoreOrLess();
        int hw = width/2, hh = height/2,
              baseX = quad.getHor()? hw: 0, baseY = quad.getVer()? hh: 0,
              advanX = posHov? posMol? hw: 0: (int) (hw/4+hw/2*Math.random()),
              advanY = posHov? (int) (hh/4+hh/2*Math.random()): posMol? hh: 0;
        this.x = baseX+advanX; this.y = baseY+advanY;
    }

    public Cord(Cycle cycle,double shrinkFrom) {

//        MyLog.info("Cord>>(shrink)");
        Cord heart = cycle.getHeart();
        double r = cycle.getRadius();
//        MyLog.info(2,"圆心",heart.toString()); MyLog.info(2,"半径",r);
        double shrunk = (shrinkFrom-60+Math.random()*120)%360;//随机出来的全局旋转角
//        MyLog.info("偏移后的全局旋转角",shrunk);
//        MyLog.info(2,"随机摇摆出角度",shrunk);
        QUAD rotateQUAD = QUAD.calQuad(shrunk);//根据角度判断所处象限
//        MyLog.info("偏移后的全局旋转角所处象限",rotateQUAD);

//        MyLog.info(2,"相对圆心所处象限",rotateQUAD);
        double angleBetween = 0;//和X轴的夹角
        switch (rotateQUAD) {
            case ONE:
            case THREE:
                angleBetween = 90*rotateQUAD.getQii()-shrunk;
                break;
            case TWO:
            case FOUR:
                angleBetween = shrunk%90;
                break;
        }
//        MyLog.info("旋转后的连线和x轴的夹角",angleBetween);
        int fuHao = rotateQUAD.equals(QUAD.ONE) || rotateQUAD.equals(QUAD.TWO)? 1: -1;
//        MyLog.info("equals(ONE)||equals(TWO)",fuHao);
        double deltaX = r*Math.cos(Math.toRadians(angleBetween)),
              x = heart.getX()+fuHao*deltaX;
//        MyLog.info("deltaX&X",deltaX+"/"+x);
        Cord[] cordsOnX = cycle.pointedOneGetOthers(true,x);
//        MyLog.info("圆心",cycle.getHeart().toString()); MyLog.info("半径",cycle.getRadius());
//        MyLog.info("第一个交点",cordsOnX[0].toString());
//        MyLog.info("第二个交点",cordsOnX[1].toString());
//        MyLog.info("第一个交点所处象限",cordsOnX[0].calQuad(heart));
//        MyLog.info("第二个交点所处象限",cordsOnX[1].calQuad(heart));
        boolean first = cordsOnX[0].getQuad(heart).equals(rotateQUAD);
        Cord chosen = cordsOnX[first? 0: 1];
        this.x = chosen.getX();
        this.y = chosen.getY();
    }//从总体旋转角度得出圆上交点

    public int getX() {
        return (int) this.x;
    }

    public int getY() {
        return (int) this.y;
    }

    public float getPreciousX() {
        return (float) this.x;
    }

    public float getPreciousY() {
        return (float) this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getDis(Cord target) {
        double deltaX = x-target.getX(), deltaY = y-target.getY();
        return Math.sqrt(deltaX*deltaX+deltaY*deltaY);
    }

    public QUAD getQuad(Cord compare) {
        return QUAD.calQuad(this,compare);
    }

    public double getQuadAngle(QUAD quad) {
        switch (quad) {
            case ONE:
            default:
                return 0;
            case TWO:
                return 90;
            case THREE:
                return 180;
            case FOUR:
                return 270;
        }
    }

    public Cord getInner(MyMap myMap) {
        double x = getX(), y = getY();
        switch (getPOS(myMap)) {
            case TOP:
                y++;
                break;
            case LEFT:
                x++;
                break;
            case RIGHT:
                x--;
                break;
            case BOTTOM:
                y--;
                break;
        }
        return new Cord(x,y);
    }

    public boolean equals(Cord compare) {
        return this.x==compare.getX() && this.y==compare.getY();
    }

    public ORIEN getPOS(MyMap myMap) {
        if (y==x || y==-x+myMap.getMs()) {
            return ORIEN.CORNER;
        } else {
            boolean positive = y>x, negative = y>-x+myMap.getMs();
            return positive? negative? ORIEN.BOTTOM: ORIEN.LEFT:
                  negative? ORIEN.RIGHT: ORIEN.TOP;
        }
    }

    public void setAngel(double angel) {
        this.angel = angel;
    }

    public float getAngel() {
        return (float) this.angel;
    }

    public String toString() {
        return "("+x+","+y+")";
    }

    public boolean onEdge(int ms) {
        return onLeftEdge() || onRightEdge(ms) || onTopEdge() || onDownEdge(ms);
    }

    private boolean onLeftEdge() {
        return x==0;
    }

    private boolean onRightEdge(int ms) {
        return x==ms-1;
    }

    private boolean onTopEdge() {
        return y==0;
    }

    private boolean onDownEdge(int ms) {
        return y==ms-1;
    }

    private boolean theSame(Cord compareWith) {
        return this.x==compareWith.getX() && this.y==compareWith.getY();
    }

}
