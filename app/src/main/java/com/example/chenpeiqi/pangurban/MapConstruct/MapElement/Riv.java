package com.example.chenpeiqi.pangurban.MapConstruct.MapElement;

import com.example.chenpeiqi.pangurban.Helper.MyLog;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Bezier;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Cycle;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.ThickenBezier;

/**
 * Created on 2018/3/12.
 */
public class Riv extends PaintAble {

    private Bezier curBezier;
    private T paint;

    public Riv(MyMap map) {
        Cycle island = new Cycle(map.getCen(),map.getIr());
        curBezier = new Bezier(island,map.getMs()/5,2);//origin
        ThickenBezier riv = new ThickenBezier(curBezier,3);
        setContour(riv,ORIGIN);
        setContour(riv,OCCUPIED);
//        DirectContour check = new DirectContour(curBezier.getKps());
//        setContour(check,DEBUG);
    }

    @Override
    public T paintWhat() {
        return T.water;
    }

    @Override
    void writeToDataBase() {

    }

    public Bezier getBezier() {
        return curBezier;
    }

}
