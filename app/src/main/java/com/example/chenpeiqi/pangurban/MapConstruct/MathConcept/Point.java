package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

import java.util.ArrayList;

/**
 * Created on 2018/7/27.
 */
public class Point extends Contour {

    ArrayList<Cord> pixels = new ArrayList<>();

    public Point(Cord central) {
        pixels.add(central);
    }

    @Override
    public ArrayList<Cord> getPixels() {
        return pixels;
    }
}
