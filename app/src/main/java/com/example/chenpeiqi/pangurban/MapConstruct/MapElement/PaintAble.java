package com.example.chenpeiqi.pangurban.MapConstruct.MapElement;

import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Contour;

/**
 * Created on 2018/3/17.
 * 解决两个问题：1.用什么画(MyLog);2.画在哪里(Contour)
 */
public abstract class PaintAble {

    int width, deep, height;

    PaintAble() {}

    public enum T {

        water("w",true),ground("g",true),tree("t",false),mount("m",false),
        building("l",false),grass("g",false),brick("b",true),sand("s",true);

        String Symbol;
        boolean onBasic;

        T(String Symbol,boolean onBase) {
            this.Symbol = Symbol;
            this.onBasic = onBase;
        }

        public String getSymbol() {
            return this.Symbol;
        }

        public boolean paintOn() {
            return this.onBasic;
        }
    }

    public abstract T paintWhat();

    public static final int ORIGIN = 0;
    static final int OCCUPIED = 1;
    static final int DEBUG = 2;

    private Contour[] contours = new Contour[3];

    void setContour(Contour contour,int setAs) {
        contours[setAs] = contour;
    }

    public Contour getContour(int shallGet) {
        return contours[shallGet];
    }

    abstract void writeToDataBase();

}
