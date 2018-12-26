package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

/**
 * Created on 2018/3/15.
 */
public enum QUAD {

    ONE(true,false,1),TWO(true,true,2),THREE(false,true,3),FOUR(false,false,4);

    boolean hor, ver;
    int qii;

    QUAD(boolean hor,boolean ver,int intDescription) {
        //hor|ver for above average on hor|ver
        this.hor = hor; this.ver = ver; this.qii = intDescription;
    }

    public static QUAD switchQuad(QUAD curQUAD) {
        switch (curQUAD) {
            case ONE:
                return THREE;
            case TWO:
                return FOUR;
            case THREE:
                return ONE;
            case FOUR:
            default:
                return TWO;
        }
    }

    public static QUAD calQuad(Cord cord,Cord o) {//计算出cord以o为参照点的相对象限
        double x = cord.getX(), y = cord.getY(),
              ox = o.getX(), oy = o.getY();
        boolean hor = x>ox, ver = y>oy;
        return hor? ver? TWO: ONE: ver? THREE: FOUR;
    }

    public static QUAD calQuad(double angle) {//圆中位置
//        MyLog.info("calQuad>>.angle",angle);
        int check = (int) angle/90;
//        MyLog.info("angel/90",angle/90);
        switch (check) {
            case 0:
                return ONE;
            case 1:
                return TWO;
            case 2:
                return THREE;
            case 3:
            default:
                return FOUR;
        }
    }

    public static boolean aboveAverage(QUAD check,boolean hov) {
        boolean leftOrRight = check.equals(THREE) || check.equals(FOUR),
              upOrDown = check.equals(ONE) || check.equals(FOUR);
        return hov? leftOrRight: upOrDown;

    }

    //returns orientations meeting the crush need
    public ORIEN[] getORIEN(boolean crushOrNot) {
        ORIEN[] crushes = new ORIEN[]{
              hor? ORIEN.RIGHT: ORIEN.LEFT,
              ver? ORIEN.BOTTOM: ORIEN.TOP
        }, notCrushes = new ORIEN[]{
              hor? ORIEN.LEFT: ORIEN.RIGHT,
              ver? ORIEN.TOP: ORIEN.BOTTOM
        };
        return crushOrNot? crushes: notCrushes;
    }

    public QUAD next(ORIEN ORIEN) {
        boolean oriHOV = ORIEN.getHorOrVer(), oriPON = ORIEN.getMoreOrLess();
        boolean finalHor = oriHOV? same(hor,oriPON)? hor: !hor: hor,
              finalVer = oriHOV? ver: same(ver,oriPON)? ver: !ver;
        return reflects(finalHor,finalVer);
    }

    boolean same(boolean first,boolean second) {
        return (first && second) || (!first && !second);
    }

    static QUAD reflects(boolean hor,boolean ver) {
        return hor? ver? TWO: ONE: ver? THREE: FOUR;
    }

    public static QUAD trans(int num) {
        switch (num) {
            case 0:
                return ONE;
            case 1:
                return TWO;
            case 2:
                return THREE;
            default:
                return FOUR;
        }
    }//0,1,2,3 >> ONE,...,FOUR

    public boolean getHor() {
        return hor;
    }

    public boolean getVer() {
        return ver;
    }

    public int getQii() {
        return qii;
    }
}
