package com.example.questionbank.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "quBank.db";
    public static final int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sql = "create table  tb_question (_id INTEGER PRIMARY KEY AUTOINCREMENT,question varchar(200),\n" +
//                "type varchar(20),\n" +
//                "select_A varchar(50),\n" +
//                "select_B varchar(50),\n" +
//                "select_C varchar(50),\n" +
//                "select_D varchar(50),\n" +
//                "answer varchar(10),\n"+
//                "q_class varchar(10),\n"+
//                "testtime INTEGER,\n"+
//                "wrongtime INTEGER,\n"+
//                "righttime INTEGER,\n"+
//                "hardlevel varchar(10),\n"+
//                "lastwrong varchar(10))";
//        db.execSQL(sql);
                String sql = "create table tb_test (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date_time varchar(20),\n" +
                "q_num INTEGER,\n" +
                "wrong_num INTEGER)";
                db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// 数据库版本号变更会调用 onUpgrade 函数，在这根据版本号进行升级数据库
        switch (oldVersion) {
            case 1:
                // do something
                break;

            default:
                break;
        }
    }
}
