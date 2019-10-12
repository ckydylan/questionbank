package com.example.questionbank.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
    RadioGroup rg_question_distribution;
    RadioButton rb_random,rb_in_wrong,rb_no_proficiency,rb_only_new;
    Button btn_save_question_round;
    private String question_distribution = "随机抽取";
    private String multiple_choice_num;
    private String judge_choice_num;
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
        btn_save_question_round = findViewById(R.id.btn_save_question_round);
        rg_question_distribution = findViewById(R.id.rg_question_distribution);
        rb_random = findViewById(R.id.rb_random);
        rb_in_wrong = findViewById(R.id.rb_in_wrong);
        rb_no_proficiency = findViewById(R.id.rb_no_proficiency);
        rb_only_new = findViewById(R.id.rb_only_new);

        tv_title.setText("题目范围");



        rg_question_distribution.setOnCheckedChangeListener(this);
        btn_save_question_round.setOnClickListener(v->{
            multiple_choice_num = et_multiple_choice_num.getText().toString();
            judge_choice_num = et_judge_choice_num.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("question_distribution",question_distribution);
            intent.putExtra("multiple_choice_num",multiple_choice_num);
            intent.putExtra("judge_choice_num",judge_choice_num);
            setResult(10,intent);
            finish();
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (rb_random.getId() == checkedId){
            question_distribution = rb_random.getText().toString();
        }else if (rb_in_wrong.getId() == checkedId){
            Log.d("rb_in_wrong",rb_in_wrong.getText().toString());
            question_distribution = rb_in_wrong.getText().toString();
        }else if (rb_no_proficiency.getId() == checkedId){
            question_distribution = rb_no_proficiency.getText().toString();
        }else if (rb_only_new.getId() == checkedId){
            question_distribution = rb_only_new.getText().toString();
        }
    }
}
