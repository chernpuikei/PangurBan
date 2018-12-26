package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

/**
 * Created on 2018/8/14.
 */
public enum ORIEN {
    TOP(false,false),BOTTOM(false,true),
    LEFT(true,false),RIGHT(true,true),CORNER(false,false);

    boolean horOrVer, moreOrLess;

    ORIEN(boolean horOrVer,boolean moreOrLess) {
        this.horOrVer = horOrVer;
        this.moreOrLess = moreOrLess;
    }

    public boolean getHorOrVer() {
        return horOrVer;
    }

    public boolean getMoreOrLess() {
        return moreOrLess;
    }

    public static ORIEN separate(ORIEN[] ORIENS,ORIEN ORIEN) {
        return ORIENS[ORIENS[0].equals(ORIEN)? 1: 0];
    }

    public ORIEN opposite() {
        switch (this) {
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case TOP:
                return BOTTOM;
            default:
                return TOP;
        }

    }
}
