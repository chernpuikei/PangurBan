package com.example.chenpeiqi.pangurban.MapConstruct.MapElement;

import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Bezier;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.ThickenBezier;

/**
 * Created on 2018/7/29.
 */
public class Coast extends PaintAble {

    public Coast(Riv river) {
        Bezier rivBez = river.getBezier();
        ThickenBezier sand = new ThickenBezier(rivBez,4);
        setContour(sand,ORIGIN);
        setContour(sand,OCCUPIED);
    }

    @Override
    public T paintWhat() {
        return T.sand;
    }

    @Override
    void writeToDataBase() {

    }
}
