package com.example.questionbank.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.questionbank.R;
import com.example.questionbank.utils.ImmersiveStatusBarSettings;

/**
 * @author cky
 * date 2019-10-10
 * 题目范围
 */
public class QuestionRoundActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
    TextView tv_title;
    EditText et_multiple_choice_num,et_judge_choice_num;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_round);
        initView();
    }

    private void initView(){
        new ImmersiveStatusBarSettings().settingStatusBar(this);
        tv_title = findViewById(R.id.tv_title);
        et_multiple_choice_num = findViewById(R.id.et_multiple_choice_num);
        et_judge_choice_num = findViewById(R.id.et_judge_choice_num);

        tv_title.setText("题目范围");

        String multiple_choice_num = et_multiple_choice_num.getText().toString();
        String judge_choice_num = et_judge_choice_num.getText().toString();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
