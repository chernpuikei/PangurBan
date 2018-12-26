package com.example.chenpeiqi.pangurban.MapConstruct.MapElement;

import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Cord;

/**
 * Created on 2018/3/31.
 */
abstract class Area {

    private int top, bottom, left, right;

    void initBound(Cord central,int horSpan,int verSpan) {//确定当前area的边界
        int halfHS = horSpan/2, halfVS = verSpan/2,
              x = central.getX(), y = central.getY();
        left = x-halfHS; right = x+halfHS; top = y-halfVS; bottom = y+halfVS;
    }

    void iterates(MyMap myMap) {
        this.iterates(myMap,0,true);
    }

    void iterates(MyMap myMap,int sd) {
        iterates(myMap,sd,true);
    }

    void iterates(MyMap myMap,boolean whichWay) {
        iterates(myMap,0,whichWay);
    }

    void iterates(MyMap myMap,int sd,boolean whichWay) {//对当前area里每个方格执行操作
        for (int i = left-sd;i<right+sd;i++) {
            for (int j = top-sd;j<bottom+sd;j++) {
                Cord curCord = new Cord(i,j);
                if (whichWay) iterateBody1(myMap,curCord);
                else iterateBody2(myMap,curCord);
            }
        }
    }

    abstract void iterateBody1(MyMap myMap,Cord curCord);

    abstract void iterateBody2(MyMap myMap,Cord curCord);

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }
}
