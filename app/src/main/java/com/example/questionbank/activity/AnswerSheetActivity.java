package com.example.questionbank.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.questionbank.R;

/**
 * @author cky
 * date 2019-10-10
 * 答题卡
 */
public class AnswerSheetActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_sheet);
    }
}
