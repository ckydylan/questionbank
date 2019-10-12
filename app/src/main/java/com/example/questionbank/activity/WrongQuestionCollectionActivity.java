package com.example.questionbank.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionbank.R;
import com.example.questionbank.adapter.WrongQuestionAdapter;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.utils.ImmersiveStatusBarSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cky
 * date 2019-10-12
 * 错题集
 */
public class WrongQuestionCollectionActivity extends Activity {
    TextView tv_title;
    RecyclerView rv_wrong_question;
    WrongQuestionAdapter adapter;
    List<QuestionBean> wrongList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_question_collection);
        initView();
    }

    private void initView(){
        new ImmersiveStatusBarSettings().settingStatusBar(this);
        tv_title = findViewById(R.id.tv_title);
        rv_wrong_question = findViewById(R.id.rv_wrong_question);
        rv_wrong_question.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        tv_title.setText("错题集");

        initData();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_wrong_question.setLayoutManager(manager);
        adapter = new WrongQuestionAdapter(wrongList);

        adapter.setOnItemClickListener(new WrongQuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                QuestionBean questionBean = wrongList.get(position);
                Intent intent = new Intent(WrongQuestionCollectionActivity.this,WrongQuestionDetailActivity.class);
                intent.putExtra("wrong_question",questionBean);
                // todo 传递点击的对象
                startActivity(intent);
            }
        });
        rv_wrong_question.setAdapter(adapter);
    }

    private void initData(){
        wrongList = new ArrayList<>();

        String question = null;
        String type = null;
        String select_A = null;
        String select_B = null;
        String select_C = null;
        String select_D = null;
        String answer = null;
        question = "对坐标计算中关于“基点”、“节点”的概念下面哪种说法是错误的的概念下面哪种说法是错误的的概念下面哪种说法是错误的";
        type = "选择";
        select_A = "逼近线段的交点称为节点逼近线段的交点称为节点逼近线段的交点称为节称为节点逼近线段的交点称逼近线段的交点称为节点";
        select_B = "各相邻几何元素的交点或切点称为基点";
        select_C = "各相邻几何元素的交点或切点称为节点";
        select_D = "节点和基点是两个不同的概念";
        answer = "A";
        for (int i = 0; i < 10; i++) {
            wrongList.add(new QuestionBean(i, question, type, select_A, select_B, select_C, select_D, answer));

        }
    }
}
