package com.example.chenpeiqi.pangurban.Helper;

import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Cord;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Cycle;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Line;
import com.example.chenpeiqi.pangurban.MapConstruct.MapElement.MyMap;

/**
 * Created on 2018/1/31.
 */
public class MyMath {

    static double[] calMP(double[] start,double[] end,double progress) {
        double deltaX = end[0]-start[0], deltaY = end[1]-start[1];
        return new double[]{start[0]+deltaX*progress,start[1]+progress*deltaY};
    }

    //todo:点X线X距得出结果
    //Cord At Appoint Distance
    static double[] caaD(double[] fl,double[] point,double dis) {
        double f_vse_a = fl[0], f_vse_b = fl[1], cenX = point[0], cenY = point[1];
        double qf_a = f_vse_a*f_vse_a+1, qf_b = 2*((f_vse_b-cenY)*f_vse_a-cenX),
              qf_c = (f_vse_b-cenY)*(f_vse_b-cenY)+cenX*cenX-dis*dis;

        return rQF(qf_a,qf_b,qf_c);
    }

    static double[] rQF(double a,double b,double c) {//2a分之负b加减根号b方减4ac
        //todo:根据一条二次方多项式的a,b,c算出此多项式的两个解
        double temp = Math.sqrt(b*b-4*a*c),
              preRes1 = (-b+temp)/(a*2), preRes2 = (-b-temp)/(a*2);
        return new double[]{preRes1,preRes2};
    }

    public static Cord[] lineCrossCycle(Line line,Cycle ch) {
        Cord cycle = ch.getHeart();
        double la = line.getA(), lb = line.getB(), r = ch.getRadius(),
              cx = cycle.getX(), cy = cycle.getY();
        double a = la*la+1, b = 2*(la*(lb-cy)-cx), c = cx*cx+(lb-cy)*(lb-cy)-r*r;
        double[] xs = rQF(a,b,c);
        Cord[] cords = new Cord[2];
        for (int i = 0;i<2;i++) {
            double y = line.getY(xs[i]);
            cords[i] = new Cord(xs[i],y);
        }
        return cords;

    }

    static boolean within(Cord b1,Cord b2,Cord cord) {
        //cal top bottom left right
        Cord[] cords = new Cord[]{b1,b2};
        int horMin = MyMap.ms, horMax = 0, verMin = MyMap.ms, verMax = 0,
              x = cord.getX(), y = cord.getY();
        for (int i = 0;i<cords.length;i++) {
            int curX = cords[i].getX(), curY = cords[i].getY();
            if (horMin>curX) horMin = curX;
            if (horMax<curX) horMax = curX;
            if (verMin>curY) verMin = curY;
            if (verMax<curY) verMax = curY;
        }
        return horMin<x && x<horMax && verMin<y && y<verMax;

    }

    double[] resortArray(double[] array) {
        double[] resorted = new double[2];
        resorted[0] = Math.min(array[0],array[1]);
        resorted[1] = Math.max(array[0],array[1]);
        return resorted;
    }



}
