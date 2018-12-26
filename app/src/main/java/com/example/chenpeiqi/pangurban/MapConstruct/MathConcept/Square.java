package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

import java.util.ArrayList;

/**
 * Created on 2018/4/8.
 */
public class Square extends Contour {

    private int staX, endX, topY, botY;

    public Square(Cord central, int horSpan, int verSpan) {
        int cx = central.getX(), cy = central.getY(),
                halfHorSpan = horSpan / 2, halfVerSpan = verSpan / 2;
        staX = cx - halfHorSpan;
        endX = cx + halfHorSpan;
        topY = cy - halfVerSpan;
        botY = cy + halfVerSpan;
    }

    public Square(int top, int bot, int left, int right) {
        staX = left;
        endX = right;
        topY = top;
        botY = bot;
    }

    public int getWidth() {
        return endX - staX;
    }

    public int getHeight() {
        return botY - topY;
    }

    public ArrayList<Cord> getPixels() {
        ArrayList<Cord> pixels = new ArrayList<>();
        for (int x = staX; x <= endX; x++) {
            for (int y = topY; y <= botY; y++) {
                pixels.add(new Cord(x, y));
            }
        }
        return pixels;
    }

    @Override
    public String toString() {
        return "square: x:[" + staX + "," + endX + "],y:[" + topY + "," + botY + "]";
    }
}
