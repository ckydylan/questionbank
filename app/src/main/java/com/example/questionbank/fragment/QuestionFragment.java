package com.example.questionbank.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.questionbank.R;
import com.example.questionbank.activity.ProficiencyQuestionActivity;
import com.example.questionbank.activity.WrongQuestionCollectionActivity;

/**
 * @author cky
 * date 2019-10-10
 */
public class QuestionFragment extends Fragment implements View.OnClickListener {
    TextView tv_title;
    CardView cv_wrong_question_collection, cv_proficiency_question, cv_data_statistics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_title = view.findViewById(R.id.tv_title);
        cv_wrong_question_collection = view.findViewById(R.id.cv_wrong_question_collection);
        cv_proficiency_question = view.findViewById(R.id.cv_proficiency_question);
        cv_data_statistics = view.findViewById(R.id.cv_data_statistics);

        tv_title.setText("题库");
        cv_wrong_question_collection.setOnClickListener(this);
        cv_proficiency_question.setOnClickListener(this);
        cv_data_statistics.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //错题集
            case R.id.cv_wrong_question_collection:
                startActivity(new Intent(getContext(), WrongQuestionCollectionActivity.class));
                break;
            //熟练题
            case R.id.cv_proficiency_question:
                startActivity(new Intent(getContext(), ProficiencyQuestionActivity.class));

                break;
            //数据统计
            case R.id.cv_data_statistics:

                break;
        }
    }
}
