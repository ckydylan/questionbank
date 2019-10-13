package com.example.questionbank.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.bean.TestBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDAO {
    private Context context;
    private SQLiteOpenHelper helper;

    public TestDAO(Context context) {
        this.context = context;
        helper = new DatabaseHelper(context);
    }

    /**
     * 上传测试记录
     * @param tb
     */
    public void addTestRecord(TestBean tb){

        SQLiteDatabase database = helper.getWritableDatabase();
        helper.onUpgrade(database,1,2);

        //String sql = "insert into tb_test_record(q_num,wrong_num,date_time) values(?,?,?)";
        ContentValues contentValues = new ContentValues();
        contentValues.put("q_num", tb.getqNum());
        contentValues.put("wrong_num", tb.getWrongQuNum());
        contentValues.put("date_time", tb.getDate());
        database.insert("tb_test_record", null, contentValues);
        //database.close();
    }
    /**
     * 获取当天测试记录
     */
    public int getTodayRecord(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String datestr = sdf.format(date);

        Cursor cursor = db.query("tb_test_record",null,"date_time = ?",new String[]{datestr},null,null,null);
        //List<TestBean> testBeans = new ArrayList<>(10);
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            return 0;
        }
        int num = 0;
        do{
//            TestBean testBean = new TestBean();
//            testBean.setId(cursor.getInt(cursor.getColumnIndex("_id")));
//            testBean.setId(cursor.getInt(cursor.getColumnIndex("q_num")));
//            testBean.setId(cursor.getInt(cursor.getColumnIndex("wrong_num")));
//            testBean.setDate(cursor.getString(cursor.getColumnIndex("date_time")));
            num += cursor.getInt(cursor.getColumnIndex("q_num"));
            //testBeans.add(testBean);
        }while (cursor.moveToNext());

        return num;
        //db.query();
    }

    /**
     * 获取id最新的10条测试记录
     */
    public List<TestBean> getNewest10Record(){
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query("tb_test_record",null,null,null,null,null,"_id DESC");
        List<TestBean> testBeans = new ArrayList<>(10);
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            return null;
        }
        do{
            TestBean testBean = new TestBean();
            testBean.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            testBean.setId(cursor.getInt(cursor.getColumnIndex("q_num")));
            testBean.setId(cursor.getInt(cursor.getColumnIndex("wrong_num")));
            testBean.setDate(cursor.getString(cursor.getColumnIndex("date_time")));
            testBeans.add(testBean);
        }while (cursor.moveToNext());
        return testBeans;
    }
}
