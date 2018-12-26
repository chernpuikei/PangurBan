package com.example.chenpeiqi.pangurban.MapConstruct.MapElement;

import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.*;

/**
 * Created on 2018/5/13.
 */
public class MyTree extends PaintAble {

    public MyTree(Cord cen) {
        int bodyRadius = (int) (Math.random()/0.5)+1,
              bodyHeight = (int) (Math.random()/0.33)+3,
              crownWidth = (int) (Math.random()/0.25+3),
              crownHeight = (int) (crownWidth*1.5),
              cenX = cen.getX(), cenY = cen.getY(),
              crownTopX = cenX, crownTopY = cenY-bodyHeight-crownHeight,
              crownLeftX = cenX-crownWidth/2, crownRightX = cenX+crownWidth/2,
              crownBotY = cenY-bodyHeight;
        Cord crownTop = new Cord(crownTopX,crownTopY),
              crownLeft = new Cord(crownLeftX,crownBotY),
              crownRight = new Cord(crownRightX,crownBotY);
        TuoYuan bottom = new TuoYuan(cen,2);
        Square treeBody = new Square(cen,bodyRadius*2,bodyHeight);
        Triangle treeCrown = new Triangle(crownTop,crownLeft,crownRight);
//        DirectContour tree = new DirectContour(treeBody,treeCrown);
//        super.setContour(tree,COVERED);//遮挡
        TuoYuan area = new TuoYuan(cen,4);
        Point ori = new Point(cen);
        setContour(ori,ORIGIN); setContour(ori,OCCUPIED);
    }

    @Override
    public T paintWhat() {
        return T.tree;
    }

    @Override
    void writeToDataBase() {

    }

}
