package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

import com.example.chenpeiqi.pangurban.Helper.MyMath;

import java.util.ArrayList;

import static com.example.chenpeiqi.pangurban.Helper.MyLog.i;

/**
 * Created on 2018/3/9.
 */

public class Bezier extends Contour {

    private ArrayList<Cord> kps = new ArrayList<>();
    private ArrayList<Float> angels = new ArrayList<>();
    private ArrayList<Double> rate = new ArrayList<>();

    public Bezier(ArrayList<Cord> kps) {//
        this.kps = kps;
        initPixels(kps.get(0).getDis(kps.get(1)));
    }

    public Bezier(Cord start,Cord end) {
        staEnd2Bez(start,end,2,50);
    }

    public Bezier(Square square,int conCount) {//用于构造起始屏幕上的bezier
        int horSpan = square.getWidth(), verSpan = square.getHeight();
        double hw = horSpan/2, hh = verSpan/2;
        Cord cen = new Cord(hw,hh);
        int largestCZDis = (horSpan+verSpan)*2;
        int czDis = (int) (Math.random()*largestCZDis);
        Cord start = new Cord(czDis,horSpan,verSpan);
        int endCzDis = (czDis+horSpan+verSpan)%largestCZDis;
        Cord end = new Cord(endCzDis,horSpan,verSpan);
        staEnd2Bez(start,end,conCount,40);
    }

    public Bezier(Cycle island,int conCount,double pixDis) {//用于构造island上riv的bezier
        Cord start = island.randomOn(), end = island.getEndFromStart(start);
        staEnd2Bez(start,end,conCount,pixDis);
    }

    private void staEnd2Bez(Cord start,Cord end,int conCount,double pixDis) {
        kps.add(start);
        Line staEndLine = new Line(start,end);
        double kpDis = start.getDis(end)/(conCount+1);
        ArrayList<Cord> splitPoints = staEndLine.getSplCord(conCount);
        boolean whichSide = true;
        for (int i = 0;i<splitPoints.size();i++) {//从等分点逐个产生控制点
//            i(i+"/"+conCount+":");
            Cord crossPoint = splitPoints.get(i);
            Line verLine = new Line(crossPoint,staEndLine);
            Cycle cycle = new Cycle(crossPoint,kpDis*Math.random()*5);
            Cord[] crossPoints = MyMath.lineCrossCycle(verLine,cycle);
            Cord selected = verLine.randomOnRange(crossPoints,whichSide);
            whichSide = !whichSide;
            kps.add(selected);
//            MyLog.info(2,"控制点-"+info,selected.toString());
//            MyLog.info(2,"当前点到上一点距离",kps.get(curSize-1).getDis(kps.get(curSize-2)));
//            MyLog.info("");
        }
        kps.add(end);
        initPixels(pixDis);
//        MyLog.info(1,"终点到最后控制点的距离",end.getDis(kps.get(kps.size()-2)));
//        MyLog.info(1,"<<end");
    }

    private void initPixels(double pixDis) {
        //pixDis<<将相邻key point的连线划分成多少份进行采样,等同于贝塞尔曲线上点的数目
        Cord start = kps.get(0), end = kps.get(kps.size()-1);
        //todo:产生一条大概率不连贯的贝塞尔曲线
        ArrayList<Cord> tempPix = new ArrayList<>();
        for (int i = 0;i<pixDis;i++) {//使用贝塞尔曲线初始化像素
            tempPix.add(getProPoi(4,kps,i/pixDis));
        }
        tempPix.add(end);
        //todo:上述产生的不连贯的贝塞尔曲线点和点之间前后相连
        for (int i = 0;i<tempPix.size()-1;i++) {//扫描每个像素点，连接不相邻的点
            kps.add(tempPix.get(i));
            Cord before = tempPix.get(i),
                  after = tempPix.get(i+1);
            if (checkAround(before,after)) {
                continue;
            } else {
                Line line = new Line(before,after);
                super.pixels.addAll(line.getEachPoint(1));
            }
        }
        pixels.add(tempPix.get(tempPix.size()-1));
        initAngels();
    }

    private void initAngels() {//为pixels中的每个Cord添加角度描述
        for (int i = 0;i<pixels.size()-1;i++) {
            Cord p0 = pixels.get(i), p1 = pixels.get(i+1);
            float p0x = p0.getPreciousX(), p0y = p0.getPreciousY(),
                  p1x = p1.getPreciousX(), p1y = p1.getPreciousY();
            double delX = p1x-p0x, delY = p1y-p0y;
            //todo:判断象限
            int quad = delX!=0 && delY!=0?
                  p1x>p0x? p1y<p0y? 0: 1: p1y>p0y? 2: 3:
                  delX==0? delY<0? 0: 2: delX>0? 1: 3;
            double absDelX = Math.abs(delX), absDelY = Math.abs(delY);
            boolean which = quad==0 || quad==2;
            double biZhi = which? absDelX/absDelY: absDelY/absDelX;
            float baseAngel = (float) quad*90,
                  angel = (float) Math.toDegrees(Math.atan(biZhi));
            angels.add(baseAngel+angel);
//            i("i",i+"/"+pixels.size()+">>>>>>");
//            i("p0",p0); i("p1",p1);
//            i("delX",delX); i("delY",delY);
//            i("base",baseAngel);
//            i("tan比值",biZhi);
//            i("angel",angel);
//            i("最终角度",baseAngel+angel);
        }

    }

    private Cord getProPoi(int layer,ArrayList<Cord> key,double progress) {
        while (key.size()!=1) {
            Line[] lines = new Line[key.size()-1];//n个点组成n-1条线
            ArrayList<Cord> key_temp = new ArrayList();//n-1条线得出n-1个点
            for (int i = 0;i<lines.length;i++) {
                lines[i] = new Line(key.get(i),key.get(i+1));
                key_temp.add(lines[i].getPercentageCord(progress));
            }
            key = key_temp;
        }
        return key.get(0);//while到最后key只有一个元素
    }

    private boolean checkAround(Cord previous,Cord after) {
        int px = previous.getX(), py = previous.getY(),
              ax = after.getX(), ay = after.getY();
        return checkSibling(px,ax) && checkSibling(py,ay);
    }

    boolean checkSibling(int first,int second) {
        return first==second-1 || first==second || first==second+1;
    }

    public ArrayList<Cord> getKps() {
        return kps;
    }

    public Cord getStart() {
        return kps.get(0);
    }

    public Cord getEnd() {
        return kps.get(kps.size()-1);
    }

    public ArrayList<Float> getAngels() {
        return angels;
    }

    @Override
    public String toString() {
        String check = "";
        for (Cord cord : kps) {
            check += cord;
        }
        return check;
    }
}