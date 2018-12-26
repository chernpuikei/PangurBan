package com.example.chenpeiqi.pangurban.MapConstruct.MathConcept;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.ArrayList;

import static android.graphics.Color.GREEN;

public class Painting implements Runnable {

    private SurfaceHolder holder;
    private Bezier bezier;
    private Paint paint = new Paint();

    Painting(SurfaceHolder holder, Bezier bezier) {
        this.holder = holder;
        this.bezier = bezier;
        paint.setColor(GREEN);
    }

    Painting(SurfaceHolder holder,ArrayList<Cord> hello){

    }

    @Override
    public void run() {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        ArrayList<Cord> pix = bezier.getPixels();
        for (Cord cur : pix) {
            canvas.drawCircle(cur.getX(), cur.getY(), 5, paint);
        }
        holder.unlockCanvasAndPost(canvas);
    }

}
