package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

import java.util.ArrayList;

/**
 * Created on 2018/5/13.
 */
public class DirectContour extends Contour {//传入pixels，返回pixels

    private ArrayList<Cord> pixels = new ArrayList<>();

    DirectContour(ArrayList<Cord> kpsOfBezier) {
        pixels.addAll(kpsOfBezier);
        activateOrdinal();//用于显示bezier.kps，需显示顺序
    }

    DirectContour(ArrayList<Cord> first,ArrayList<Cord> second) {
        pixels.addAll(first); pixels.addAll(second);
    }

    public ArrayList<Cord> getPixels() {
        return pixels;
    }

}
