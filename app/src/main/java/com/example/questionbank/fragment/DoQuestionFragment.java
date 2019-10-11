package com.example.questionbank.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.questionbank.R;
import com.example.questionbank.activity.DoQuestionActivity;
import com.example.questionbank.activity.MainActivity;
import com.example.questionbank.bean.QuestionAnswerBean;
import com.example.questionbank.bean.QuestionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cky
 * date 2019-10-11
 */
public class DoQuestionFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    private QuestionBean questionBean;
    private Drawable drawable;
    private int index;
    TextView tv_answer, tv_person_select, tv_question_id;
    TextView tv_question_type, tv_question;
    RadioGroup rg_select;
    RadioButton rb_a, rb_b, rb_c, rb_d;
    ConstraintLayout cl_answer;
    Button btn_finish_question;


    public DoQuestionFragment(int index) {
        Log.d("index", index + "");
        this.index = index;
        questionBean = DoQuestionActivity.questionBeanList.get(index);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_question, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_question_type = view.findViewById(R.id.tv_question_type);
        tv_question = view.findViewById(R.id.tv_question);
        rg_select = view.findViewById(R.id.rg_select);
        tv_question_id = view.findViewById(R.id.tv_question_id);
        rb_a = view.findViewById(R.id.rb_a);
        rb_b = view.findViewById(R.id.rb_b);
        rb_c = view.findViewById(R.id.rb_c);
        rb_d = view.findViewById(R.id.rb_d);
        tv_answer = view.findViewById(R.id.tv_answer);
        tv_person_select = view.findViewById(R.id.tv_person_select);
        cl_answer = view.findViewById(R.id.cl_answer);
        btn_finish_question = view.findViewById(R.id.btn_finish_question);

        rg_select.setOnCheckedChangeListener(this);

        showLastButton();
        setRadioButtonBG();
        setQuestion();
    }

    /**
     * 设置题目
     */
    private void setQuestion() {
        tv_question_id.setText(index + 1 +"/"+DoQuestionActivity.questionBeanList.size());
        if ("选择".equals(questionBean.getType())) {
            tv_question_type.setText(questionBean.getType());
            tv_question.setText(questionBean.getQuestion());
            rb_a.setText(questionBean.getSelect_A());
            rb_b.setText(questionBean.getSelect_B());
            rb_c.setText(questionBean.getSelect_C());
            rb_d.setText(questionBean.getSelect_D());
        } else if ("判断".equals(questionBean.getType())) {
            tv_question_type.setText(questionBean.getType());
            tv_question.setText(questionBean.getQuestion());
            rb_a.setText(questionBean.getSelect_A());
            rb_b.setText(questionBean.getSelect_B());
            rb_c.setVisibility(View.INVISIBLE);
            rb_d.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 设置选择的ABCD
     */
    private void setRadioButtonBG() {
        drawable = getResources().getDrawable(R.drawable.a_select, getContext().getTheme());
        drawable.setBounds(0, 0, 100, 100);
        rb_a.setCompoundDrawables(drawable, null, null, null);

        drawable = getResources().getDrawable(R.drawable.b_select, getContext().getTheme());
        drawable.setBounds(0, 0, 100, 100);
        rb_b.setCompoundDrawables(drawable, null, null, null);

        drawable = getResources().getDrawable(R.drawable.c_select, getContext().getTheme());
        drawable.setBounds(0, 0, 100, 100);
        rb_c.setCompoundDrawables(drawable, null, null, null);

        drawable = getResources().getDrawable(R.drawable.d_select, getContext().getTheme());
        drawable.setBounds(0, 0, 100, 100);
        rb_d.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 选择后显示答案
     *
     * @param checkedId    判断是否已经选择
     * @param personSelect 人选择的答案
     */
    private void showAnswer(int checkedId, String personSelect) {
        if (checkedId != 0) {
            cl_answer.setVisibility(View.VISIBLE);
            //todo 从数据库中拿取答案删
            String answer = "A";
            tv_answer.setText(answer);
            tv_person_select.setText(personSelect);

            //答案不正确
            if (!tv_person_select.getText().toString().equals(tv_answer.getText().toString())) {
                tv_person_select.setTextColor(Color.RED);
                DoQuestionActivity.questionAnswerBeanList.get(index).setAnswerStatus(0);
            } else {
                //答案正确
                DoQuestionActivity.questionAnswerBeanList.get(index).setAnswerStatus(1);
                tv_person_select.setTextColor(Color.parseColor("#4664E6"));
            }
        }
    }

    /**
     * 设置选择之后不可再选择
     */
    private void setClickableFalse() {
        rb_a.setClickable(false);
        rb_b.setClickable(false);
        rb_c.setClickable(false);
        rb_d.setClickable(false);
    }

    private void showLastButton() {
        if (index == DoQuestionActivity.questionBeanList.size() - 1) {
            btn_finish_question.setVisibility(View.VISIBLE);
            btn_finish_question.setOnClickListener(v -> {
            });
        }
    }

//        @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_last_question:
//                flag--;
//                if (flag < 0) {
//                    Toast.makeText(this, "已经是第一题", Toast.LENGTH_SHORT).show();
//                    flag = 0;
//                }
//                setQuestion(flag);
//                break;
//            case R.id.btn_next_question:
//                flag++;
//                if (flag > 3) {
//                    Toast.makeText(this, "已经是最后一题", Toast.LENGTH_SHORT).show();
//                    flag = 3;
//                }
//                clearSelect();
//                setQuestion(flag);
//                break;
//        }
//    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        String personSelect = null;
        if (rb_a.getId() == checkedId) {
            personSelect = "A";
            Toast.makeText(getContext(), rb_a.getText().toString(), Toast.LENGTH_SHORT).show();
        } else if (rb_b.getId() == checkedId) {
            personSelect = "B";
            Toast.makeText(getContext(), rb_b.getText().toString(), Toast.LENGTH_SHORT).show();
        } else if (rb_c.getId() == checkedId) {
            personSelect = "C";
            Toast.makeText(getContext(), rb_c.getText().toString(), Toast.LENGTH_SHORT).show();
        } else if (rb_d.getId() == checkedId) {
            personSelect = "D";
            Toast.makeText(getContext(), rb_d.getText().toString(), Toast.LENGTH_SHORT).show();
        }
        if (checkedId != 0) {
            setClickableFalse();
        }
        showAnswer(checkedId, personSelect);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
