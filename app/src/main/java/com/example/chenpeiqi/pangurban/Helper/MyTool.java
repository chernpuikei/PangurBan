package com.example.chenpeiqi.pangurban.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created on 2018/11/5.
 */
public class MyTool {

    public static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences("login status",Context.MODE_PRIVATE);
    }
}
