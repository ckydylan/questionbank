package com.example.questionbank.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.questionbank.R;

/**
 * @author cky
 * date 2019-10-10
 * 题目范围
 */
public class QuestionRoundActivity extends Activity {
    TextView tv_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_round);
        initView();
    }

    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("题目范围");
    }
}
