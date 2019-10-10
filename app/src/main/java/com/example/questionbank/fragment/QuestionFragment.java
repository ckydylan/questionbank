package com.example.questionbank.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.questionbank.R;

/**
 * @author cky
 * date 2019-10-10
 */
public class QuestionFragment extends Fragment {
    TextView tv_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("题库");
    }
}
