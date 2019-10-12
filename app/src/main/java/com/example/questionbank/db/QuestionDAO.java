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

    public void updateQuestion(QuestionBean questionBean){
        SQLiteOpenHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //在values中添加内容
        values.put("testtime",questionBean.getTesttime());
        values.put("wrongtime",questionBean.getWrongtime());
        values.put("righttime",questionBean.getRighttime());
        values.put("lastwrong",questionBean.getLastwrong());
        //修改条件
        String whereClause = "_id = ?";
        //修改添加参数
        String[] whereArgs={String.valueOf(questionBean.getId())};
        //修改
        //int x =
        database.update("tb_question",values,whereClause,whereArgs);
        database.close();
        return ;
    }

    public List<QuestionBean> qureyQuestion(int[] qnums){
        SQLiteOpenHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();

        String[] qnum_str = new String[qnums.length];
        StringBuilder sb = new StringBuilder("_id in (");
        for(int i = 0; i < qnums.length; i++){
            if(i != qnums.length-1){
                sb.append("?,");
            }else {
                sb.append("?");
            }
            qnum_str[i] = String.valueOf(qnums[i]);
        }
        sb.append(")");
        Cursor cursor = database.query("tb_question",null
        ,sb.toString(), qnum_str,null,null,null);

        List<QuestionBean> questionBeans = new ArrayList<>();
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            return questionBeans;
        }

        do{
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String question = cursor.getString(cursor.getColumnIndex("question"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String a_choice = cursor.getString(cursor.getColumnIndex("select_A"));
            String b_choice = cursor.getString(cursor.getColumnIndex("select_B"));
            String c_choice = cursor.getString(cursor.getColumnIndex("select_C"));
            String d_choice = cursor.getString(cursor.getColumnIndex("select_D"));
            String answer = cursor.getString(cursor.getColumnIndex("answer"));
            String q_class = cursor.getString(cursor.getColumnIndex("q_class"));
            int testtime = cursor.getInt(cursor.getColumnIndex("testtime"));
            int wrongtime = cursor.getInt(cursor.getColumnIndex("wrongtime"));
            int righttime = cursor.getInt(cursor.getColumnIndex("righttime"));
            String hardlevel = cursor.getString(cursor.getColumnIndex("hardlevel"));

            QuestionBean qb = new QuestionBean();
            qb.setId(id);
            qb.setQuestion(question);
            qb.setType(type);
            qb.setSelect_A(a_choice);
            qb.setSelect_B(b_choice);
            qb.setSelect_C(c_choice);
            qb.setSelect_D(d_choice);
            qb.setAnswer(answer);
            qb.setqClass(q_class);
            qb.setTesttime(testtime);
            qb.setWrongtime(wrongtime);
            qb.setRighttime(righttime);
            qb.setHardlevel(hardlevel);
            qb.setAnswerStatus(2);
            questionBeans.add(qb);
        }while (cursor.moveToNext());
        cursor.close();
//
//        for(QuestionBean qb : questionBeans){
//            LogUtil.loge(">>>qureyQuestion>>>",qb.toString());
//        }
        return questionBeans;
    }


}
