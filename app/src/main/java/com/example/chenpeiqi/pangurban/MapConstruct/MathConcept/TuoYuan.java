package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

import java.util.ArrayList;

/**
 * Created on 2018/4/8.
 */
public class TuoYuan extends Contour {

    private int x0, y0, horSpan, verSpan;

    public TuoYuan(Cord central,int rOnHor) {
//        MyLog.info(4,"TuoYuan()>>");
        this.x0 = central.getX(); this.y0 = central.getY();
        this.horSpan = rOnHor; verSpan = rOnHor/2;
        initPixels();
    }

    private int[] getYsOnX(int x) {
        double a = horSpan/2, b = verSpan/2;
        int root = siSeWuRu(Math.sqrt(b*b*(1-(x-x0)*(x-x0)/(a*a))));
        return new int[]{y0-root,y0+root};
    }

    int siSeWuRu(double check) {
        boolean hello = check%1>0.5;
        return (int) check+(hello? 1: 0);
    }

    public void initPixels() {
        ArrayList<Cord> pixels = new ArrayList<>();
        int halfHorSpan = horSpan/2, staX = x0-halfHorSpan, endX = x0+halfHorSpan;
        for (int x = staX;x<=endX;x++) {
//            MyLog.info("x@TuoYuan/[sta,end]",x+"/["+staX+","+endX+"]");
            int[] curYs = getYsOnX(x);
            for (int curY = curYs[0];curY<=curYs[1];curY++) {
//                MyLog.info(2,"length of y",curYs[1]-curYs[0]);
                pixels.add(new Cord(x,curY));
            }
        }
    }

}
