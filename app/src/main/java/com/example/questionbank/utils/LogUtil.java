package com.example.questionbank.utils;

import android.util.Log;

public class LogUtil {
    public static final int MAX_SIZE = 1024*2;

    public static void loge(String tag, String msg){
        while (msg.length() > MAX_SIZE){
            Log.e(tag, msg.substring(0,MAX_SIZE));
            msg = msg.substring(MAX_SIZE);
        }
        Log.e(tag, msg);

    }
}
