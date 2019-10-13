package com.example.questionbank.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TestDAO {
    private Context context;
    private SQLiteDatabase database;

    public TestDAO(Context context) {
        this.context = context;
        SQLiteOpenHelper helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }
}
