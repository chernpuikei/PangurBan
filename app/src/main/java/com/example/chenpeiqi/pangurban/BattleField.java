package com.example.chenpeiqi.pangurban;

import android.content.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.chenpeiqi.pangurban.Helper.MyLog;
import com.example.chenpeiqi.pangurban.Helper.MyTool;
import com.example.chenpeiqi.pangurban.MapConstruct.MapElement.MyMap;

import java.lang.ref.WeakReference;

import static com.example.chenpeiqi.pangurban.Helper.MyLog.i;
import static com.example.chenpeiqi.pangurban.Helper.MyLog.setBasicStack;

public class BattleField extends AppCompatActivity {
    
    AnimateView animateView;
    MyHandler handler;
    MyMap myMap;
    boolean idStatusAcquiredFlag = false;
    public static final int requestCode_getContent = 0;
    
    static class MyHandler extends Handler {
        
        WeakReference<BattleField> hello;
        MyMap myMap;
        
        public static final int startingAnimationFinished = 0,
                idStatusAcquired = 1, mainAnimation = 4,
                dialogCreated = 5, map = 6, terminateApp = 7;
        
        MyHandler(WeakReference<BattleField> hello) {
            this.hello = hello;
        }
        
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case startingAnimationFinished:
                    i(1, "<< message.startingAnimationFinished");
                    String spid = MyTool.getSP(hello.get()).getString("id", "nah");
                    i(2, "spid", spid);
                    if (spid.equals("nah")) {
                        //如果SP中没有账户信息则弹出登陆注册对话框
                        LoginDialog dialog = new LoginDialog();
                        dialog.show(hello.get().getFragmentManager(), "get in");
                    } else {
                        //如果已经存在账户信息则开始绘制主动画
                        new Thread(new DrawingThread(
                                hello.get(), hello.get().animateView.getHolder(), myMap)).start();
                    }
                    break;
                case idStatusAcquired://id获取的过程同样也是加载数据进activity的过程
                    i(1, "<< message.idStatusAcquired");
                    hello.get().setIdStatusAcquiredFlag(true);
                    hello.get().myMap = (MyMap) msg.getData().getSerializable("map");
                    break;
                case terminateApp:
                    hello.get().terminateApp();
                
            }
            
        }
        
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 3; i++) i("***************");
        handler = new MyHandler(new WeakReference<>(this));
        IntentFilter account = new IntentFilter();
        account.addCategory("action");
        setBasicStack();
        //todo:播放动画，同时启动检查用户信息以确定下一步动作
        animateView = new AnimateView(this);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        animateView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(animateView);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("login"); intentFilter.addAction("register");
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new NetworkMessageReceiver(), intentFilter);
        simulateRegisterRequest();
        
    }
    
    void simulateRegisterRequest() {
        i("simulateRegisterRequest>>");
        Intent intent = new Intent("register");
        intent.putExtra("id", "simulateRegisterRequest");
        intent.putExtra("password", "123");
        new NetworkTransaction().enqueueWork(
                this, NetworkTransaction.class, NetworkTransaction.jobId_login_register, intent);
    }
    
    void terminateApp() {
        i("terminateApp>>");
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    
    private void setIdStatusAcquiredFlag(boolean whetherOrNot) {
        this.idStatusAcquiredFlag = whetherOrNot;
    }
    
    private boolean getIdStatusAcquiredFlag() {
        return idStatusAcquiredFlag;
    }
    
    private class AnimateView extends SurfaceView implements SurfaceHolder.Callback {
        
        Context context;
        Paint green, red;
        Bitmap pointer;
        
        public AnimateView(Context context) {
            super(context);
            this.context = context;
            i("AnimateView>>");
            getHolder().addCallback(this);
            green = new Paint(); green.setColor(Color.GREEN);
            red = new Paint(); red.setColor(Color.RED);
        }
        
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            i("surfaceCreated>>");
            Canvas canvas = getHolder().lockCanvas();
            int width = canvas.getWidth(), height = canvas.getHeight();
            holder.unlockCanvasAndPost(canvas);
            //sync SharedPreference
            SharedPreferences sp = MyLog.getSP(BattleField.this);
            sp.edit().putInt("width", width).putInt("height", height).apply();
            String id = sp.getString("id", "nah");
            new Thread(new DrawingThread(context, holder)).start();//starting animation
        }
        
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        
        }
        
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        
        }
        
    }
    
    private class NetworkMessageReceiver extends BroadcastReceiver {
        
        @Override
        public void onReceive(Context context, Intent intent) {
            i("Activity receives message", intent);
            boolean result = intent.getBooleanExtra("result", false);
            if (!result) return;//只处理result是true的broadcast,false handled by dialog
            
            switch (intent.getAction()) {
                case "register":
                case "login":
                    
                    //invalidate SharedPreference
                    SharedPreferences sp = MyTool.getSP(context);
                    sp.edit().putString("id", intent.getStringExtra("id")).apply();
                    
                    //send MyMap to Handler and draw
                    Message message = new Message();
                    message.what = MyHandler.idStatusAcquired;
                    MyMap myMap = (MyMap) intent.getSerializableExtra("map");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("map", myMap);
                    message.setData(bundle);
                    handler.sendMessage(message);
                    break;
                
            }
        }
    }
}
