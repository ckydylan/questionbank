package com.example.questionbank.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.utils.LogUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private Context context;

    public QuestionDAO(Context context) {
        this.context = context;
    }

    public void addQuestion(ArrayList<QuestionBean> list){
            SQLiteOpenHelper helper = new DatabaseHelper(context);
            SQLiteDatabase database = helper.getWritableDatabase();

            String sql = "insert into tb_question(question,type,select_A,select_B,select_C,select_D,answer) values(?,?,?,?,?,?,?)";
            SQLiteStatement stat = database.compileStatement(sql);
            //database
            for(QuestionBean qb : list){
//            stat.bindString(1,qb.getQuestion());
//            stat.bindString(2,qb.getType());
//            stat.bindString(3,qb.getSelect_A());
//            stat.bindString(4,qb.getSelect_B());
//            stat.bindString(5,qb.getSelect_C());
//            stat.bindString(6,qb.getSelect_D());
//            stat.bindString(7,qb.getAnswer());
//            stat.executeInsert();
                ContentValues contentValues = new ContentValues();
                contentValues.put("question",qb.getQuestion());
                contentValues.put("select_A",qb.getSelect_A());
                contentValues.put("select_B",qb.getSelect_B());
                contentValues.put("select_C",qb.getSelect_C());
                contentValues.put("select_D",qb.getSelect_D());
                contentValues.put("type",qb.getType());
                contentValues.put("answer",qb.getAnswer());
                database.insert("tb_question", null, contentValues);
            }
        database.close();
    }

    public int qureyQnum(){
        SQLiteOpenHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query("tb_question",new String[]{"_id"},null,null,null,null,"_id DESC");
        cursor.moveToFirst();
        int id = 0;
        try{
            id = cursor.getInt(0);
        }catch (CursorIndexOutOfBoundsException e){
            Log.e("SQLite", "first create table" );
        }
        database.close();
        return id;
    }

    public void qureyQuestion(int[] qnums){
        SQLiteOpenHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();

//        for(int i : ){
//
//        }
        StringBuilder sb = new StringBuilder("_id in (");
        for(int i : qnums){

        }
        Cursor cursor = database.query("tb_question",new String[]{"_id","question","type","select_A","select_B","select_C","select_D","answer"}
        ,"_id = ?", null,null,null,null);

        List<QuestionBean> questionBeans = new ArrayList<>(qnums.length);
        cursor.moveToFirst();
        while(cursor != null){
            int id = cursor.getInt(0);
            String question = cursor.getString(1);
            String type = cursor.getString(2);
            String a_choice = cursor.getString(3);
            String b_choice = cursor.getString(4);
            String c_choice = cursor.getString(5);
            String d_choice = cursor.getString(6);
            String answer = cursor.getString(7);
            cursor.moveToNext();
            QuestionBean qb = new QuestionBean();
            qb.setId(id);
            qb.setQuestion(question);
            qb.setType(type);
            qb.setSelect_A(a_choice);
            qb.setSelect_B(b_choice);
            qb.setSelect_C(c_choice);
            qb.setSelect_D(d_choice);
            qb.setAnswer(answer);
            questionBeans.add(qb);
        }
        for(QuestionBean qb : questionBeans){
            LogUtil.loge(">>>>>>",qb.toString());
        }
//
//        int id = 0;
//        try{
//            id = cursor.getInt(0);
//        }catch (CursorIndexOutOfBoundsException e){
//            Log.e("SQLite", "first create table" );
//        }

        return;
    }


}
