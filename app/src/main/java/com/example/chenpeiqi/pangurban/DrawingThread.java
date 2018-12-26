package com.example.chenpeiqi.pangurban;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.SurfaceHolder;

import com.example.chenpeiqi.pangurban.MapConstruct.MapElement.MyMap;
import com.example.chenpeiqi.pangurban.MapConstruct.MathConcept.*;

import java.util.ArrayList;

import static com.example.chenpeiqi.pangurban.Helper.MyLog.i;

/**
 * Created on 2018/11/23.
 */
public class DrawingThread implements Runnable {
    
    public static final int STARTING_ANIMATION = 0,
            REGULAR_ANIMATION = 1, TEXT_ANIMATION = 2;
    private Context context;
    private SurfaceHolder holder;
    private Bitmap pointer;
    private int animationIdentifier;
    private MyMap myMap;
    
    //starting animation的构造方法
    DrawingThread(Context context, SurfaceHolder holder) {
        this.context = context;
        this.holder = holder;
        this.animationIdentifier = STARTING_ANIMATION;
        
        //todo:certain with bitmap size
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(
                context.getResources(), R.drawable.pointer, options);
        int oriWidth = options.outWidth, oriHeight = options.outHeight;
        options.inSampleSize = oriWidth / 50;
        options.inJustDecodeBounds = false;
        pointer = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.pointer, options);
    }
    
    //regular animation的构造方法
    DrawingThread(Context context, SurfaceHolder surfaceHolder, MyMap myMap) {
        this.context = context;
        this.holder = surfaceHolder;
        this.myMap = myMap;
        this.animationIdentifier = REGULAR_ANIMATION;
        
        //todo:1.从screen size x central cord得出想要读取的map tile 集合
    }
    
    @Override
    public void run() {
        i("DrawingThread.run()>>");
        switch (animationIdentifier) {
            case STARTING_ANIMATION:
                startingAnimation();
                break;
            case REGULAR_ANIMATION:
                regularAnimation();
                break;
            case TEXT_ANIMATION:
                textAnimation();
                break;
        }
    }
    
    private void startingAnimation() {
        i("startingAnimation>>");
        Paint paint = new Paint(); paint.setColor(Color.GREEN);
        Canvas canvas = holder.lockCanvas();
        int width = canvas.getWidth(), height = canvas.getHeight();
        //产生bezier曲线所有的点
        QUAD curQUAD = QUAD.trans((int) (Math.random() / 0.25));//aka staQUAD
        ORIEN staORIEN = curQUAD.getORIEN(true)[Math.random() > 0.5 ? 1 : 0];
        ArrayList<Cord> keyPoints = new ArrayList<>();
        Cord start = new Cord(curQUAD, staORIEN, width, height);
        keyPoints.add(start);
        canvas.drawCircle(start.getX(), start.getY(), 5, paint);
        for (int current = 0; current < 4; current++) {
            //quad>>orientation>>cord
            ORIEN[] endORIENs = curQUAD.getORIEN(current == 3);
            ORIEN endORIEN = ORIEN.separate(endORIENs, staORIEN);
            Cord end = new Cord(curQUAD, endORIEN, width, height);
            keyPoints.add(end);
            canvas.drawCircle(end.getX(), end.getY(), 5, paint);
            canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
            
            curQUAD = curQUAD.next(endORIEN);
            staORIEN = endORIEN.opposite();
            start = end;
        }
        holder.unlockCanvasAndPost(canvas);
        
        //从key points产生bezier曲线，绘制辅助线
        Bezier bezier = new Bezier(keyPoints);
        ArrayList<Cord> pixels = bezier.getPixels();
        ArrayList<Float> angels = bezier.getAngels();
        ArrayList<Cord> selected = new ArrayList<>();
        //间隔取点，绘制角度
        boolean lor = true;
        Cord previous = pixels.get(0);
        for (int i = 150; i < pixels.size(); i += 150) {
            
            Cord cur = pixels.get(i),
                    stepAside = stepAside(previous, cur, lor, 50 * 3);
            lor = !lor;
            stepAside.setAngel(angels.get(i));
            selected.add(stepAside);
            previous = stepAside;
        }
        
        
        for (int i = 0; i < selected.size() * 25 + 75; i += 10) {
            Canvas canvas1 = holder.lockCanvas();
            canvas1.drawColor(Color.WHITE);
            for (int j = 0; j < selected.size(); j++) {
                Cord curFP = selected.get(j);
                int preAlpha = i - j * 25;
                if (preAlpha > 99 || preAlpha < 0) continue;
                int alpha = (50 - Math.abs(50 - preAlpha)) * 2;
                paint.setAlpha(alpha);
                Matrix matrix = new Matrix();
                matrix.postTranslate(curFP.getX(), curFP.getY());
                matrix.postRotate(curFP.getAngel(), curFP.getX(), curFP.getY());
                canvas1.drawBitmap(pointer, matrix, paint);
                
            }
            holder.unlockCanvasAndPost(canvas1);
            
        }
        ((BattleField) context).handler.sendEmptyMessage(
                BattleField.MyHandler.startingAnimationFinished);
    }
    
    private Cord stepAside(Cord previous, Cord origin, boolean lor, int miniDis) {
        //step aside 过程中还需要避免点和点之间的碰撞
        float[] pos = new float[]{(float) origin.getX(), (float) origin.getY()},
                tan = new float[]{pos[0] / 100, pos[1] / 100};
        float ap = -tan[0] / tan[1], bp = pos[1] - ap * pos[0];
        float a = ap * ap + 1, b = 2 * (ap * bp - ap * pos[1] - pos[0]),
                c = (bp - pos[1]) * (bp - pos[1]) + pos[0] * pos[0] - 2500;
        float x1 = (float) (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
        float x2 = (float) (-b - Math.sqrt(b * b - 4 * a * c)) / (2 * a);
        float y1 = ap * x1 + bp, y2 = ap * x2 + bp;
        Cord before = lor ? new Cord(x1, y1) : new Cord(x2, y2);
        Line line = new Line(previous, before);
        Cord travelFar = line.getPercentageCord(
                (float) miniDis / previous.getDis(before));
        return travelFar;
    }
    
    private void regularAnimation() {
        Canvas canvas = holder.lockCanvas();
        
        //todo:vector drawable>>bitmap>>canvas.drawBitmap
        VectorDrawable tree = load(R.drawable.ic_tree),
                mount = load(R.drawable.ic_mount);
        canvas.drawColor(Color.WHITE);
        tree.setBounds(100, 100, 200, 200); tree.draw(canvas);
        tree.setBounds(200, 250, 300, 350); tree.draw(canvas);
        mount.setBounds(400, 400, 600, 600); mount.draw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }
    
    private VectorDrawable load(int id) {
        return (VectorDrawable) ResourcesCompat
                .getDrawable(context.getResources(), id, null);
    }
    
    private void textAnimation() {}
    
}
