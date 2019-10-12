package com.example.questionbank.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.questionbank.R;
import com.example.questionbank.bean.QuestionBean;

/**
 * @author cky
 * date 2019-10-12
 */
public class WrongQuestionDetailActivity extends Activity {
    TextView tv_question_type,tv_question_id,tv_question,tv_answer;
    RadioButton rb_a, rb_b, rb_c, rb_d;
    private Drawable drawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_question_detail);
        initView();
    }

    private void initView(){
        tv_question_type = findViewById(R.id.tv_question_type);
        tv_question_id = findViewById(R.id.tv_question_id);
        tv_question = findViewById(R.id.tv_question);
        tv_answer = findViewById(R.id.tv_answer);
        rb_a = findViewById(R.id.rb_a);
        rb_b = findViewById(R.id.rb_b);
        rb_c = findViewById(R.id.rb_c);
        rb_d = findViewById(R.id.rb_d);

        setRadioButtonBG();

        QuestionBean questionBean = (QuestionBean) getIntent().getSerializableExtra("wrong_question");
        Log.d("questionBean",questionBean.toString());
        tv_question_id.setText(String.valueOf(questionBean.getId()));
        tv_question.setText(questionBean.getQuestion());
        tv_question_type.setText(questionBean.getType());
        tv_answer.setText(questionBean.getAnswer());
        rb_a.setText(questionBean.getSelect_A());
        rb_b.setText(questionBean.getSelect_B());
        rb_c.setText(questionBean.getSelect_C());
        rb_d.setText(questionBean.getSelect_D());

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
