package com.example.chenpeiqi.pangurban.MapConstruct.MapElement;

import com.example.chenpeiqi.pangurban.Helper.MyLog;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Cord;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Line;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.TuoYuan;

import java.util.ArrayList;

/**
 * Created on 2018/3/25.
 */
public class Vil extends PaintAble {

    private Cord rivCho, vilCen;

    public Vil(MyMap myMap,Riv riv) {//构造方法中仅初始化village的中心以及范围
        int ms = myMap.getMs();
        ArrayList<Cord> rivPix = riv.getBezier().getPixels();
        int rivSize = rivPix.size(), ranSelect = (int) (Math.random()*rivSize),
              vilSpan = (int) (ms*0.4);
        this.rivCho = rivPix.get(ranSelect);
        Line riv2Cen = new Line(rivCho,myMap.getCen());
        double ranSeed = 0.2+Math.random()*0.6;
        vilCen = riv2Cen.getPercentageCord(ranSeed);
        TuoYuan vilArea = new TuoYuan(vilCen,vilSpan);
        super.setContour(vilArea,ORIGIN);

    }

    private double randomPercent() {
        return Math.random()*0.6+0.2;
    }

    @Override
    public T paintWhat() {
        return T.brick;
    }

    @Override
    void writeToDataBase() {

    }

}

