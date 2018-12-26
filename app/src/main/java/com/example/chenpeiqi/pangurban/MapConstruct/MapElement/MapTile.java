package com.example.chenpeiqi.pangurban.MapConstruct.MapElement;

import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Cord;

/**
 * Created on 2018/3/12.
 */
public class MapTile {

    private Cord cord;
    private PaintAble.T advance;
    private PaintAble.T basic;
    private boolean hasAdvance, occupation;
    static final int BASIC = 0, ADVANCE = 1, COVERED = 2, OCCUPIED = 3;
    private int mark = -1;

    MapTile(Cord cord) {
        this.cord = cord;
    }

    void setBasic(PaintAble.T basic) {
        this.basic = basic;
    }

    PaintAble.T getBasic() {
        return basic;
    }

    void addAdvance(PaintAble.T advance) {
        this.advance = advance;
        hasAdvance = true;
    }

    PaintAble.T getAdvance() {
        return advance;
    }

    void setOccupation() {
        this.occupation = true;
    }

    boolean getOccupation() {
        return occupation;
    }

    void setMarked(int counter) {
        this.mark = counter;
    }

    String getMark() {
        return this.mark+"";
    }

    boolean beingMarked() {
        return this.mark!=-1;
    }

    boolean onTheGround() {
        return basic.equals(PaintAble.T.ground);
    }

    public String getOutLook() {
        String stage1 = hasAdvance? advance.getSymbol(): this.basic.getSymbol(),
              stage2 = getOccupation()? stage1.toUpperCase(): stage1,
              stage3 = beingMarked()? getMark(): stage1;
        return stage3;
    }

    Cord getCord() {
        return this.cord;
    }

}
