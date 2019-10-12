package com.example.questionbank.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.questionbank.R;
import com.example.questionbank.activity.DoQuestionActivity;
import com.example.questionbank.activity.QuestionRoundActivity;

/**
 * @author cky
 * date 2019-10-10
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    TextView tv_title,tv_question_edit;
    Button btn_do_question;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        tv_title = view.findViewById(R.id.tv_title);
        tv_question_edit = view.findViewById(R.id.tv_question_edit);//题库中修改
        btn_do_question = view.findViewById(R.id.btn_do_question);//题库中开始做题

        tv_question_edit.setOnClickListener(this);
        btn_do_question.setOnClickListener(this);
        tv_title.setText("首页");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_question_edit:
                startActivity(new Intent(getContext(), QuestionRoundActivity.class));
                break;
            case R.id.btn_do_question:
                startActivity(new Intent(getContext(), DoQuestionActivity.class));
                break;
        }
    }
}
