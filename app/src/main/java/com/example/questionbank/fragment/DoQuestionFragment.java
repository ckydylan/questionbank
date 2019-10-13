package com.example.questionbank.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.db.QuestionDAO;

import java.util.List;

/**
 * @author cky
 * date 2019-10-11
 */
public class DoQuestionFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private QuestionBean questionBean;
    private Drawable drawable;
    private int index;
    private List<QuestionBean> questionBeanList;
    private boolean isFinish = false;
    TextView tv_answer, tv_person_select, tv_question_id, tv_set_ez;
    TextView tv_question_type, tv_question;
    RadioGroup rg_select;
    RadioButton rb_a, rb_b, rb_c, rb_d;
    ConstraintLayout cl_answer;
    Button btn_finish_question;
    boolean flag = false;


    public DoQuestionFragment(int index) {
        Log.d("index", index + "");
        this.index = index;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_question, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionBeanList = ((DoQuestionActivity) getActivity()).getQuestionBeanList();
        questionBean = ((DoQuestionActivity) getActivity()).getQuestionBeanList().get(index);
        initView(view);
    }

    private void initView(View view) {
        tv_question_type = view.findViewById(R.id.tv_question_type);
        tv_question = view.findViewById(R.id.tv_question);
        rg_select = view.findViewById(R.id.rg_select);
        tv_question_id = view.findViewById(R.id.tv_question_id);
        tv_set_ez = view.findViewById(R.id.tv_set_ez);
        rb_a = view.findViewById(R.id.rb_a);
        rb_b = view.findViewById(R.id.rb_b);
        rb_c = view.findViewById(R.id.rb_c);
        rb_d = view.findViewById(R.id.rb_d);
        tv_answer = view.findViewById(R.id.tv_answer);
        tv_person_select = view.findViewById(R.id.tv_person_select);
        cl_answer = view.findViewById(R.id.cl_answer);
        btn_finish_question = view.findViewById(R.id.btn_finish_question);

        rg_select.setOnCheckedChangeListener(this);
        tv_set_ez.setOnClickListener(this);

        showLastButton();
        setRadioButtonBG();
        setQuestion();
    }

    /**
     * 设置题目
     */
    private void setQuestion() {
        tv_question_id.setText(index + 1 + "/" + questionBeanList.size());
        setEZOrNormal();
        if ("choice".equals(questionBean.getType())) {
            tv_question_type.setText("选择");
            tv_question.setText(questionBean.getQuestion());
            rb_a.setText(questionBean.getSelect_A());
            rb_b.setText(questionBean.getSelect_B());
            rb_c.setText(questionBean.getSelect_C());
            rb_d.setText(questionBean.getSelect_D());
        } else if ("judge".equals(questionBean.getType())) {
            tv_question_type.setText("判断");
            tv_question.setText(questionBean.getQuestion());
            rb_a.setText("T");
            rb_b.setText("F");
            rb_c.setVisibility(View.INVISIBLE);
            rb_d.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置是否为简单题
     */
    private void setEZOrNormal() {
        if ("normal".equals(questionBeanList.get(index).getHardlevel())) {
            tv_set_ez.setText("设置为简单题");
            tv_set_ez.setBackgroundResource(R.color.cccccc);
        } else if ("ez".equals(questionBeanList.get(index).getHardlevel())) {
            tv_set_ez.setText("简单");
            tv_set_ez.setBackgroundResource(R.drawable.btn_do_question_bg);
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
            String answer = questionBean.getAnswer();
            tv_answer.setText(answer);
            tv_person_select.setText(personSelect);
            int historyTime = questionBeanList.get(index).getTesttime();
            questionBeanList.get(index).setTesttime(historyTime+1);
            //todo:cky
            //答案不正确
            if (!tv_person_select.getText().toString().equals(tv_answer.getText().toString())) {
                tv_person_select.setTextColor(Color.RED);
                questionBeanList.get(index).setAnswerStatus(0);
                int historyTimes = questionBeanList.get(index).getWrongtime();
                questionBeanList.get(index).setWrongtime(historyTimes + 1);
                questionBeanList.get(index).setHardlevel("usualWrong");
                questionBeanList.get(index).setLastwrong("false");

            } else {
                //答案正确
                questionBeanList.get(index).setAnswerStatus(1);
                tv_person_select.setTextColor(Color.parseColor("#4664E6"));
                int historyTimes = questionBeanList.get(index).getRighttime();
                questionBeanList.get(index).setRighttime(historyTimes + 1);
                questionBeanList.get(index).setLastwrong("true");
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
        if (index == questionBeanList.size() - 1) {
            btn_finish_question.setVisibility(View.VISIBLE);
            btn_finish_question.setOnClickListener(this);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        String personSelect = null;
        if ("choice".equals(questionBeanList.get(index).getType())) {
            if (rb_a.getId() == checkedId) {
                personSelect = "A";
            } else if (rb_b.getId() == checkedId) {
                personSelect = "B";
            } else if (rb_c.getId() == checkedId) {
                personSelect = "C";
            } else if (rb_d.getId() == checkedId) {
                personSelect = "D";
            }
        } else if ("judge".equals(questionBeanList.get(index).getType())) {
            if (rb_a.getId() == checkedId) {
                personSelect = "T";
            } else if (rb_b.getId() == checkedId) {
                personSelect = "F";
            }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set_ez:
                if (!flag) {
                    tv_set_ez.setText("简单");
                    tv_set_ez.setBackgroundResource(R.drawable.btn_do_question_bg);
                    Toast.makeText(getActivity(), "此题目已设置为简单，可以再次点击恢复", Toast.LENGTH_LONG).show();
                    questionBeanList.get(index).setHardlevel("ez");
                    flag = !flag;
                } else {
                    tv_set_ez.setText("设置为简单题");
                    tv_set_ez.setBackgroundResource(R.color.cccccc);
                    questionBeanList.get(index).setHardlevel("normal");
                    flag = !flag;
                }
                break;
            case R.id.btn_finish_question:
                for (QuestionBean question : questionBeanList) {
                    if (question.getAnswerStatus() != 2) {
                        isFinish = true;
                    } else {
                        isFinish = false;
                        break;
                    }
                }
                if (!isFinish) {
                    Toast.makeText(getContext(), "没做完", Toast.LENGTH_SHORT).show();
                } else {
                    for (QuestionBean question : questionBeanList) {
                        Log.d("question", question.toString());
                        new QuestionDAO(getActivity()).updateQuestion(question);
                    }
                }
                break;
        }
    }
}
