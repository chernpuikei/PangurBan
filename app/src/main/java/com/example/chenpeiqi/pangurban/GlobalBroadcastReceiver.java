package com.example.chenpeiqi.pangurban;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created on 2018/11/24.
 */
public class GlobalBroadcastReceiver extends BroadcastReceiver {

    String animation_finished = "animation finished";

    @Override
    public void onReceive(Context context,Intent intent) {
        switch (intent.getAction()) {
            case "app done":
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

        }
    }

}
