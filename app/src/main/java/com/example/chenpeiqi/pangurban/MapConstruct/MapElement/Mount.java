package com.example.chenpeiqi.pangurban.MapConstruct.MapElement;

import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Cord;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Point;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Square;

/**
 * Created on 2018/3/12.
 */
public class Mount extends PaintAble {

    private Cord ori;
    private int width, height;

    public Mount(MyMap myMap,Cord cord) {
        ori = cord;
        width = (int) (Math.random()/0.1);
        height = (int) (5+Math.random()*height);
        Point ori = new Point(cord);
        super.setContour(ori,ORIGIN);
//        Triangle triangle = new Triangle(cord,width,height);
//        super.setContour(triangle,COVERED);
        Square square = new Square(cord,width,height*2);
        super.setContour(square,OCCUPIED);
    }

    Cord getOri() {
        return ori;
    }

    @Override
    public T paintWhat() {
        return T.mount;
    }

    @Override
    void writeToDataBase() {

    }

}
