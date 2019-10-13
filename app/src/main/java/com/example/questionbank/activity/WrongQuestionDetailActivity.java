package com.example.questionbank.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.questionbank.R;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.utils.ImmersiveStatusBarSettings;

/**
 * @author cky
 * date 2019-10-12
 */
public class WrongQuestionDetailActivity extends Activity {
    TextView tv_question_type,tv_question_id,tv_question,tv_answer,tv_title;
    RadioButton rb_a, rb_b, rb_c, rb_d;
    private Drawable drawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_question_detail);
        initView();
    }

    private void initView(){
        new ImmersiveStatusBarSettings().settingStatusBar(this);
        tv_question_type = findViewById(R.id.tv_question_type);
        tv_question_id = findViewById(R.id.tv_question_id);
        tv_question = findViewById(R.id.tv_question);
        tv_answer = findViewById(R.id.tv_answer);
        tv_title = findViewById(R.id.tv_title);
        rb_a = findViewById(R.id.rb_a);
        rb_b = findViewById(R.id.rb_b);
        rb_c = findViewById(R.id.rb_c);
        rb_d = findViewById(R.id.rb_d);

        setRadioButtonBG();

        QuestionBean questionBean = (QuestionBean) getIntent().getSerializableExtra("wrong_question");
        tv_question_id.setText(String.valueOf(questionBean.getId()));
        tv_question.setText(questionBean.getQuestion());
        tv_answer.setText(questionBean.getAnswer());
        tv_title.setText("详情");
        if ("choice".equals(questionBean.getType())){
            tv_question_type.setText("选择");
            rb_a.setText(questionBean.getSelect_A());
            rb_b.setText(questionBean.getSelect_B());
            rb_c.setText(questionBean.getSelect_C());
            rb_d.setText(questionBean.getSelect_D());
        }else if ("judge".equals(questionBean.getType())){
            tv_question_type.setText("判断");
            rb_a.setVisibility(View.INVISIBLE);
            rb_b.setVisibility(View.INVISIBLE);
            rb_c.setVisibility(View.INVISIBLE);
            rb_d.setVisibility(View.INVISIBLE);
        }


    }

    /**
     * 设置选择的ABCD
     */
    private void setRadioButtonBG() {
        drawable = getResources().getDrawable(R.drawable.a_select, this.getTheme());
        drawable.setBounds(0, 0, 100, 100);
        rb_a.setCompoundDrawables(drawable, null, null, null);

        drawable = getResources().getDrawable(R.drawable.b_select, this.getTheme());
        drawable.setBounds(0, 0, 100, 100);
        rb_b.setCompoundDrawables(drawable, null, null, null);

        drawable = getResources().getDrawable(R.drawable.c_select, this.getTheme());
        drawable.setBounds(0, 0, 100, 100);
        rb_c.setCompoundDrawables(drawable, null, null, null);

        drawable = getResources().getDrawable(R.drawable.d_select, this.getTheme());
        drawable.setBounds(0, 0, 100, 100);
        rb_d.setCompoundDrawables(drawable, null, null, null);
    }
}
