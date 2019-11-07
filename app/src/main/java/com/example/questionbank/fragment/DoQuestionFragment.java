package com.example.questionbank.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.questionbank.R;
import com.example.questionbank.activity.DoQuestionActivity;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.bean.Recoder;
import com.example.questionbank.bean.TestBean;
import com.example.questionbank.db.QuestionDAO;
import com.example.questionbank.db.TestDAO;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    LinearLayout ll_multiple;
    TextView tv_answer, tv_person_select, tv_question_id, tv_set_ez;
    TextView tv_question_type, tv_question;
    RadioGroup rg_select;
    RadioButton rb_a, rb_b, rb_c, rb_d;
    CheckBox cb_a, cb_b, cb_c, cb_d;
    ConstraintLayout cl_answer;
    Button btn_finish_question, btn_make_sure;
    boolean flag = false;
    TestBean testBean;
    StringBuffer sb;
    public static Map<Integer,Recoder> recoders = new HashMap<>(128);


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
        testBean = ((DoQuestionActivity) getActivity()).getTestBean();
        testBean.setqNum(index + 1);
        initView(view);
    }

    private void initView(View view) {
        btn_make_sure = view.findViewById(R.id.btn_make_sure);
        ll_multiple = view.findViewById(R.id.ll_multiple);
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
        cb_a = view.findViewById(R.id.cb_a);
        cb_b = view.findViewById(R.id.cb_b);
        cb_c = view.findViewById(R.id.cb_c);
        cb_d = view.findViewById(R.id.cb_d);

        sb = new StringBuffer();
        rg_select.setOnCheckedChangeListener(this);
        tv_set_ez.setOnClickListener(this);

        btn_make_sure.setOnClickListener(this);
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
            rg_select.setVisibility(View.VISIBLE);
            ll_multiple.setVisibility(View.INVISIBLE);
            tv_question_type.setText("选择");
            tv_question.setText(questionBean.getQuestion());
            rb_a.setText(questionBean.getSelect_A());
            rb_b.setText(questionBean.getSelect_B());
            rb_c.setText(questionBean.getSelect_C());
            rb_d.setText(questionBean.getSelect_D());
        } else if ("judge".equals(questionBean.getType())) {
            rg_select.setVisibility(View.VISIBLE);
            ll_multiple.setVisibility(View.INVISIBLE);
            tv_question_type.setText("判断");
            tv_question.setText(questionBean.getQuestion());
            rb_a.setText("T");
            rb_b.setText("F");
            rb_c.setVisibility(View.INVISIBLE);
            rb_d.setVisibility(View.INVISIBLE);
        } else if ("multiple".equals(questionBean.getType())) {
            ll_multiple.setVisibility(View.VISIBLE);
            rg_select.setVisibility(View.INVISIBLE);
            tv_question_type.setText("多选题");
            tv_question.setText(questionBean.getQuestion());
            cb_a.setText(questionBean.getSelect_A());
            cb_b.setText(questionBean.getSelect_B());
            cb_c.setText(questionBean.getSelect_C());
            cb_d.setText(questionBean.getSelect_D());
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
        cb_a.setCompoundDrawables(drawable, null, null, null);

        drawable = getResources().getDrawable(R.drawable.b_select, getContext().getTheme());
        drawable.setBounds(0, 0, 100, 100);
        rb_b.setCompoundDrawables(drawable, null, null, null);
        cb_b.setCompoundDrawables(drawable, null, null, null);

        drawable = getResources().getDrawable(R.drawable.c_select, getContext().getTheme());
        drawable.setBounds(0, 0, 100, 100);
        rb_c.setCompoundDrawables(drawable, null, null, null);
        cb_c.setCompoundDrawables(drawable, null, null, null);

        drawable = getResources().getDrawable(R.drawable.d_select, getContext().getTheme());
        drawable.setBounds(0, 0, 100, 100);
        rb_d.setCompoundDrawables(drawable, null, null, null);
        cb_d.setCompoundDrawables(drawable, null, null, null);
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
            questionBeanList.get(index).setTesttime(historyTime + 1);
            //答案不正确
            if (!tv_person_select.getText().toString().equals(tv_answer.getText().toString())) {
                testBean.setWrongQuNum(testBean.getWrongQuNum() + 1);
                Log.d("wrongNumb", testBean.getWrongQuNum() + "");
                tv_person_select.setTextColor(Color.RED);
                questionBeanList.get(index).setAnswerStatus(0);
                int historyTimes = questionBeanList.get(index).getWrongtime();
                questionBeanList.get(index).setWrongtime(historyTimes + 1);
                questionBeanList.get(index).setHardlevel("usualWrong");
                questionBeanList.get(index).setLastwrong("false");

                if (tv_set_ez.getText().toString().equals("熟练")) {
                    tv_set_ez.setText("您已做错");
                    tv_set_ez.setTextColor(Color.RED);
                    tv_set_ez.setBackgroundResource(R.color.cccccc);
                    questionBeanList.get(index).setHardlevel("normal");
                    tv_set_ez.setClickable(false);
                }
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
     * 选择后显示多选题答案
     *
     * @param personSelect 人选择的答案
     */
    private void showMulAnswer(String personSelect) {
        cl_answer.setVisibility(View.VISIBLE);
        String answer = questionBean.getAnswer();
        tv_answer.setText(answer);
        tv_person_select.setText(personSelect);
        int historyTime = questionBeanList.get(index).getTesttime();
        questionBeanList.get(index).setTesttime(historyTime + 1);
        //答案不正确
        if (!tv_person_select.getText().toString().equals(tv_answer.getText().toString())) {
            testBean.setWrongQuNum(testBean.getWrongQuNum() + 1);
            Log.d("wrongNumb", testBean.getWrongQuNum() + "");
            tv_person_select.setTextColor(Color.RED);
            questionBeanList.get(index).setAnswerStatus(0);
            int historyTimes = questionBeanList.get(index).getWrongtime();
            questionBeanList.get(index).setWrongtime(historyTimes + 1);
            questionBeanList.get(index).setHardlevel("usualWrong");
            questionBeanList.get(index).setLastwrong("false");

            if (tv_set_ez.getText().toString().equals("熟练")) {
                tv_set_ez.setText("您已做错");
                tv_set_ez.setTextColor(Color.RED);
                tv_set_ez.setBackgroundResource(R.color.cccccc);
                questionBeanList.get(index).setHardlevel("normal");
                tv_set_ez.setClickable(false);
            }
        } else {
            //答案正确
            questionBeanList.get(index).setAnswerStatus(1);
            tv_person_select.setTextColor(Color.parseColor("#4664E6"));
            int historyTimes = questionBeanList.get(index).getRighttime();
            questionBeanList.get(index).setRighttime(historyTimes + 1);
            questionBeanList.get(index).setLastwrong("true");
        }
        rememberRecoder(index,new Recoder(index,answer,personSelect));
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

    /**
     * 最后一个完成按钮
     */
    private void showLastButton() {
        if (index == questionBeanList.size() - 1) {
            btn_finish_question.setVisibility(View.VISIBLE);
            btn_finish_question.setOnClickListener(this);
        }
    }

    private void alert() {
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("信息");
            float rightPer = (float) (index + 1 - testBean.getWrongQuNum()) / (index + 1);
            NumberFormat nt = NumberFormat.getPercentInstance();
            nt.setMinimumFractionDigits(2);
            String format = nt.format(rightPer);
            builder.setMessage("已做:" + (index + 1) + "条\n\t错误:" + (testBean.getWrongQuNum()) + "条" + "\n\t" + "正确率：" + format);
            builder.setPositiveButton("是", (dialog, which) -> getActivity().finish());
            builder.show();
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
        Recoder recoder = recoders.get(index);
        if(recoder != null){
            Log.e("onResume:", "onResume: "+ recoder.getMyanswer());
            //showMulAnswer(recoder.getMyanswer());
            cl_answer.setVisibility(View.VISIBLE);
            tv_answer.setText(recoder.getAnswer());
            tv_person_select.setText(recoder.getMyanswer());
            //答案不正确
            if (!tv_person_select.getText().toString().equals(tv_answer.getText().toString())) {
                tv_person_select.setTextColor(Color.RED);
            } else {
                //答案正确
                tv_person_select.setTextColor(Color.parseColor("#4664E6"));
            }
            btn_make_sure.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set_ez:
                if (!flag) {
                    tv_set_ez.setText("熟练");
                    tv_set_ez.setBackgroundResource(R.drawable.btn_do_question_bg);
                    Toast.makeText(getActivity(), "此题目已设置为熟练，可以再次点击恢复", Toast.LENGTH_SHORT).show();
                    questionBeanList.get(index).setHardlevel("ez");
                    flag = !flag;
                } else {
                    tv_set_ez.setText("设置为熟练题");
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
                    Toast.makeText(getContext(), "您未做完，请仔细检查", Toast.LENGTH_SHORT).show();
                } else {
                    for (QuestionBean question : questionBeanList) {
                        new QuestionDAO(getActivity()).updateQuestion(question);
                    }
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
                    String s = sdf.format(date);
                    testBean.setDate(s);
                    new TestDAO(getContext()).addTestRecord(testBean);
                    alert();
                }
                break;
            case R.id.btn_make_sure:
                cb_a.setClickable(false);
                cb_b.setClickable(false);
                cb_c.setClickable(false);
                cb_d.setClickable(false);
                btn_make_sure.setVisibility(View.GONE);
                if (cb_a.isChecked()) {
                    Log.d("a", cb_a.isChecked() + "");
                    sb.append("A");
                }
                if (cb_b.isChecked()) {
                    Log.d("b", cb_b.isChecked() + "");

                    sb.append("B");
                }
                if (cb_c.isChecked()) {
                    Log.d("c", cb_c.isChecked() + "");

                    sb.append("C");
                }
                if (cb_d.isChecked()) {
                    Log.d("d", cb_d.isChecked() + "");

                    sb.append("D");
                }
                showMulAnswer(sb.toString());

                break;
        }
    }


    //todo:try by zq
    private void rememberRecoder(Integer index,Recoder recoder){
        Log.e("onResume", "rememberRecoder: >>>>>" );
        recoders.put(index,recoder);
        Log.e("onResume", "rememberRecoder: >>>>>" );
    }

}
