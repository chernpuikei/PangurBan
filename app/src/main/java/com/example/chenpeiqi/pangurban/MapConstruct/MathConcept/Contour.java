package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

import java.util.ArrayList;

/**
 * Created on 2018/4/8.
 */
public abstract class Contour {

    ArrayList<Cord> pixels = new ArrayList<>();

    private boolean ordinal = false;

    void activateOrdinal() {
        ordinal = true;
    }

    boolean checkOridinal() {
        return ordinal;
    }

    public ArrayList<Cord> getPixels() {
        return pixels;
    }

}
