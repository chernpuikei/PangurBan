package com.example.chenpeiqi.pangurban;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import com.example.chenpeiqi.pangurban.MapConstruct.MapElement.MyMap;

/**
 * Created on 2018/12/3.
 */
public class MapGenerator extends JobIntentService {

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Intent resultIntent = new Intent("map");
        resultIntent.putExtra("map",new MyMap());
        sendBroadcast(resultIntent);
    }
}
