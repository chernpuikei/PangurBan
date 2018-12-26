package com.example.chenpeiqi.pangurban.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.chenpeiqi.pangurban.MapConstruct.MapElement.MyMap;
import com.example.chenpeiqi.pangurban.MapConstruct.MapElement.PaintAble;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.Cord;

import java.util.ArrayList;

/**
 * Created on 2018/1/5.
 */
public class MyLog<T> {

    public void array(int tabCount,String header,T[] array,int cpg) {
        int groupCount = array.length/cpg;
        String tab = tab(tabCount);
        String finalPrint = tab+"+"+header+"+\n";
        for (int i = 0;i<groupCount;i++) {
            //一次循环就是一行
            String currentLine = tab+"|"+i+"(";
            for (int j = 0;j<cpg;j++) {
                currentLine += array[i*cpg+j]+(j==cpg-1? ")": ",");
            }
            finalPrint += currentLine+"\n";
        }
        finalPrint += tab+"+\n";
        Log.i("default",finalPrint);
    }

    public static void ar_Cord(int tabs,String header,Cord[] check) {
        String total = tab(tabs)+"+"+header+"\n";
        for (int i = 0;i<check.length;i++) {
            total += tab(tabs)+"+"+check[i].toString()+"\n";
        }
        Log.i("default",total);
    }

    public static void ar_Cord(String header,Cord[] c) {
        MyLog.ar_Cord(0,header,c);
    }

    public void arrayList(int tc,String header,ArrayList<T[]> check) {
        String shown = tab(tc)+"+"+header+"\n";
        for (int i = 0;i<check.size();i++) {
            String curLineToShown = tab(tc)+"+(";
            T[] cur = check.get(i);
            for (int j = 0;j<cur.length;j++) {
                curLineToShown += cur[j]+(j==0? ",": ")\n");
            }
            shown += curLineToShown;
        }
        Log.i("default",shown+"\n");
    }

    public static void al_Cord(int tc,String header,ArrayList<Cord> check) {
        String totalPrint = tab(tc)+"+"+header+"\n";
        int currentLine = 0;
        for (int i = 0;i<check.size();i++) {
            totalPrint += tab(tc+2)+currentLine+++"+"+check.get(i).toString()+"\n";
        }
        Log.i("default",totalPrint);
    }

    public static void al_Cord(String header,ArrayList<Cord> check) {
        MyLog.al_Cord(0,header,check);
    }

    public static void info(int tabs,String key,Object value) {
        Log.i("default",tab(tabs*4)+key+":"+value+"\n");
    }

    public static void info(String message) {
        MyLog.info(0,message,"");
    }

    public static void info(String key,Object value) {
        MyLog.info(0,key,value);
    }

    public static void info(int tab,String message) {
        MyLog.info(tab,message,"");
    }

    public static String tab(int count) {
        String check = "";
        for (int i = 0;i<count;i++) {
            check += "     ";
        }
        return check;
    }

    //todo:log出map
    public static void total(MyMap myMap) {
        String temp = "";
        for (int i = 0;i<MyMap.ms;i++) {
            temp += i%10;
        }
        Log.i("map",temp);
        int verticalCounter = 0;
        for (int ver = 0;ver<MyMap.ms;ver++) {
            temp = "";
            temp += verticalCounter++%10;
            for (int hor = 0;hor<MyMap.ms;hor++) {
                Cord curCord = new Cord(hor,ver);
                String curTer = myMap.getMapTile(curCord).getOutLook();
                String toAdd = show(myMap,curCord)? curTer: " ";
                temp += toAdd;
            }
            Log.i("map",temp);
        }
    }

    public static boolean show(MyMap myMap,Cord cur) {
        String curOutLook = myMap.getMapTile(cur).getOutLook();
        int curX = cur.getX(), curY = cur.getY();
        //todo:检查当前地形是否和当前坐标上下左右的地形都相同
        if (!cur.onEdge(myMap.getMs())) {//如果不在边上，分别获取上下左右Terrain并进行比对
            String top = myMap.getMapTile(curX,curY-1).getOutLook(),
                    bottom = myMap.getMapTile(curX,curY+1).getOutLook(),
                    left = myMap.getMapTile(curX-1,curY).getOutLook(),
                    right = myMap.getMapTile(curX+1,curY).getOutLook();
            return !(top.equals(curOutLook) && bottom.equals(curOutLook) &&
                    left.equals(curOutLook) && right.equals(curOutLook) &&
                    !myMap.getMapTile(cur).getOutLook().equals(PaintAble.T.mount)
            );
        } else {//在边上的都显示
            return true;
        }
    }

    public static int basicStack = 0, curStack = 0;

    public static void setBasicStack() {
        basicStack = Thread.currentThread().getStackTrace().length;
    }

    public static int delta() {
        int curStack = Thread.currentThread().getStackTrace().length;
        return curStack-basicStack;
    }

    public static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences("user",Context.MODE_PRIVATE);
    }

    public static void i(int tab,String message) {
        i(tab(tab)+message);
    }

    public static void i(String tag,String key,Object value) {
        Log.i(tag,tab(delta()-1)+key+":"+value);
    }

    public static void i(int tabCount,String key,Object value) {
        i(tab(tabCount)+key+":"+value);
    }

    public static void i(String key,Object value) {
        Log.i("default",key+":"+value);
    }

    public static void i(Object value) {
        Log.i("default",value.toString());
    }

    public static void a() {
        i("a");
    }

    public static void b() {
        i("b");
    }

    public static void c() {
        i("c");
    }

    public static void hello() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        Log.i("default",elements[3].getMethodName());
    }

    public static void pause(int time) {
        try {
            Thread.sleep(time);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }


}
