package com.example.chenpeiqi.pangurban.MapConstruct.MapElement;


import com.example.chenpeiqi.pangurban.Helper.MyLog;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.*;

import java.io.Serializable;
import java.util.ArrayList;

import static com.example.chenpeiqi.pangurban.Helper.MyLog.i;

/**
 * Created on 2017/11/22.
 */
public class MyMap implements Serializable {
    
    public static final int ms = 101;
    private MapTile[][] mapTiles = new MapTile[ms][ms];
    
    public MyMap() {
        
        //init mapTiles
        for (int ver = 0; ver < ms; ver++) {
            for (int hor = 0; hor < ms; hor++) {
                Cord curCord = new Cord(hor, ver);
                MapTile curTile = new MapTile(curCord);
                boolean which = curCord.getDis(getCen()) < getIr();//true的时候表示陆地
                curTile.setBasic(which ? PaintAble.T.ground : PaintAble.T.water);
                mapTiles[hor][ver] = curTile;
            }
        }
        
        //river
        Riv curRiv = new Riv(this); init(curRiv);
        
        Coast coast = new Coast(curRiv); init(coast);
        Vil vil = new Vil(this, curRiv); init(vil);
        initBuildingAndPaint(vil);
        mntAndTree();
        MyLog.total(this);
    }
    
    private void initBuildingAndPaint(Vil vil) {
        ArrayList<Cord> vilOri = vil.getContour(PaintAble.ORIGIN).getPixels();
        for (Cord curPix : vilOri) {
            if (!getMapTile(curPix).getOccupation() && Math.random() > 0.99) {
                Building building = new Building(curPix);
                init(building);
            }
        }
    }
    
    private void mntAndTree() {
        for (int ver = 0; ver < ms; ver++) {
            for (int hor = 0; hor < ms; hor++) {
                Cord cur = new Cord(hor, ver);
                MapTile mt = getMapTile(cur);
                if (mt.onTheGround() && !mt.getOccupation()) {
                    double check = Math.random();
                    if (check > 0.999) {//产生mount
                        Mount mount = new Mount(this, cur);
                        init(mount);
                    } else if (check > 0.99) {
                        MyTree tree = new MyTree(cur);
                        init(tree);
                    }
                }
            }
        }
    }
    
    public MapTile getMapTile(Cord cord) {
        return mapTiles[cord.getX()][cord.getY()];
    }
    
    public MapTile getMapTile(int x, int y) {
        return mapTiles[x][y];
    }
    
    void init(PaintAble paintAble) {
        PaintAble.T identity = paintAble.paintWhat();
        for (int layer = 0; layer < 3; layer++) {
            //ORIGIN>>OCCUPATION>>DEBUG
            try {
                Contour contour = paintAble.getContour(layer);
                ArrayList<Cord> pixels = contour.getPixels();
                for (int pixOrd = 0; pixOrd < pixels.size(); pixOrd++) {
                    Cord curCord = pixels.get(pixOrd);
                    if (!getMapTile(curCord).getOccupation())
                        subInit(curCord, layer, pixOrd, identity);
                }
            } catch (NullPointerException e) {
//                MyLog.info(2,"layer null");
            }
        }
    }
    
    void subInit(Cord cur, int layer, int pixOrd, PaintAble.T toPaint) {
//        MyLog.info("subInit>>");
//        MyLog.info(1,"cur cord to init on",cur.toString());
        MapTile mt = getMapTile(cur);
        switch (layer) {
            case 0:
//                MyLog.info(2,"ORIGIN>>");
                if (toPaint.paintOn()) mt.setBasic(toPaint);
                else mt.addAdvance(toPaint);
                break;
            case 1:
//                MyLog.info(2,"OCCUPATION>>");
                mt.setOccupation(); break;
            case 2:
//                MyLog.info(2,"MARK");
                mt.setMarked(pixOrd % 10); break;
            default:
                break;
        }
    }
    
    public Cord getCen() {
        return new Cord(ms / 2, ms / 2);
    }
    
    public int getIr() {
        return (int) (ms * 0.4);
    }
    
    public int getMs() {
        return ms;
    }
    
}