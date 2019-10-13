package com.example.questionbank.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssertUtil {
    public static String ASSERT_DB = "quBank.db";
    public static String DB_URL = "/data/data/" + "com.example.questionbank"+ "/databases/" + ASSERT_DB;
    //public static String DB_URL_DIR = "/data/data/" + "com.example.questionbank"+ "/databases/";

    public static void copyFile(Context context){
        File file = new File(DB_URL);

        if (!file.exists()){
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(file.length() > 1024*25){
            return;
        }
        try {
            FileOutputStream out = new FileOutputStream(DB_URL);
            BufferedInputStream in = new BufferedInputStream(context.getAssets().open(ASSERT_DB));
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = in.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
