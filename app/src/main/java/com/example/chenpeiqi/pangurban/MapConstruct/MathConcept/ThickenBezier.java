package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

import com.example.chenpeiqi.pangurban.Helper.MyLog;

import java.util.ArrayList;

/**
 * Created on 2018/7/14.
 */
public class ThickenBezier extends Contour {

    private Bezier bezier;
    private ArrayList<Cord> pixels = new ArrayList<>();

    public ThickenBezier(Bezier bezier,int thickenCount) {
        this.bezier = bezier;
        initPixels(thickenCount);
    }

    void initPixels(int thickenCount) {
//        MyLog.info("getPixels>>");
        ArrayList<Cord> origin = bezier.getPixels();
        for (Cord current : origin) {
//            MyLog.info("");
//            MyLog.info(1,"cur",current);
            int curX = current.getX(), curY = current.getY();
            for (int hor = curX-thickenCount;hor<=curX+thickenCount;hor++) {
                for (int ver = curY-thickenCount;ver<=curY+thickenCount;ver++) {
                    pixels.add(new Cord(hor,curY));
                }
            }
//            MyLog.info("");
        }
//        MyLog.info(1,"before size",origin.size());
//        MyLog.info(1,"after size",pixels.size());
//        MyLog.info(1,"times",pixels.size()/origin.size());
//        MyLog.info("<<getPixels");
    }

}
