package com.example.chenpeiqi.pangurban.MapConstruct.MapElement;

import com.example.chenpeiqi.pangurban.Helper.MyLog;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Cord;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Point;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Square;

/**
 * Created on 2018/3/29.
 */
public class Building extends PaintAble {

    private Cord cord;
    private int height;

    public Building(Cord cord) {
        this.cord = cord;
        height = 2+(int) (Math.random()*5);
        Point point = new Point(this.cord);
        setContour(point,ORIGIN);
        Square occupation = new Square(this.cord,1,height);
        setContour(occupation,OCCUPIED);
    }

    @Override
    public T paintWhat() {
        return T.building;
    }

    @Override
    void writeToDataBase() {

    }

}
