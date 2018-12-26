package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

import java.util.ArrayList;

/**
 * Created on 2018/4/8.
 */
public class Triangle extends Contour {

    private Cord top, left, right;

    public Triangle(Cord top,Cord left,Cord right) {
//        MyLog.info(1,"Triangle(1)>>");
//        MyLog.info(2,"top",top.toString());
//        MyLog.info(2,"left",left.toString());
//        MyLog.info(2,"right",right.toString());
        this.top = top; this.left = left; this.right = right;
    }

    //todo:从mount中点,width,height构造出triangle
    Triangle(Cord central,int width,int height) {
//        MyLog.info("Triangle(2)>>");
        int halfWidth = width/2, x = central.getX(), y = central.getY(),
              leftX = x-halfWidth, rightX = x+halfWidth, topY = y-height;
        left = new Cord(leftX,y);
        right = new Cord(rightX,y);
        top = new Cord(x,topY);
    }

    public ArrayList<Cord> getPixels() {
//        MyLog.info("Triangle.getPixels");
        ArrayList<Cord> pixels = new ArrayList<>();
        int staX = left.getX(), endX = right.getX(), middleX = top.getX(),
              topY = top.getY(), botY = left.getY();
        Line topLeft = new Line(left,top),
              topRight = new Line(right,top),
              bottom = new Line(left,right);
        for (int x = staX;x<=endX;x++) {
            Line chosen = x<middleX? topLeft: topRight;
            int selectedTopY = (int) chosen.getY(x);
            for (int y = botY;y<=selectedTopY;y++) {
                pixels.add(new Cord(x,y));
            }
        }
        return pixels;
    }

}
