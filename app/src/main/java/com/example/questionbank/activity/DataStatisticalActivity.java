package com.example.questionbank.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionbank.R;
import com.example.questionbank.adapter.AllQuestionAdapter;
import com.example.questionbank.adapter.ProficiencyQuestionAdapter;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.db.QuestionDAO;
import com.example.questionbank.utils.ImmersiveStatusBarSettings;

import java.util.List;

/**
 * @author cky
 * date 2019-10-13
 * 数据统计
 */
public class DataStatisticalActivity extends Activity implements Spinner.OnItemSelectedListener {
    Spinner spinner_question_sort;
    RecyclerView rv_all_question;
    TextView tv_title, tv_progress;
    private List<QuestionBean> allQuestionList;
    private AllQuestionAdapter adapter;
    private QuestionDAO questionDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_statistical);
        initView();
    }

    private void initView() {
        new ImmersiveStatusBarSettings().settingStatusBar(this);
        spinner_question_sort = findViewById(R.id.spinner_question_sort);
        rv_all_question = findViewById(R.id.rv_all_question);
        tv_title = findViewById(R.id.tv_title);
        tv_progress = findViewById(R.id.tv_progress);
        tv_title.setText("题库");


        spinner_question_sort.setOnItemSelectedListener(this);
        initData();
        tv_progress.setText("做题进度：" + (2000-questionDAO.qureyUnDoneQuentstionNum()) + "/2000");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_all_question.setLayoutManager(manager);
        rv_all_question.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new AllQuestionAdapter(allQuestionList);
        adapter.setOnItemClickListener(new AllQuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                QuestionBean questionBean = allQuestionList.get(position);
                Intent intent = new Intent(DataStatisticalActivity.this, WrongQuestionDetailActivity.class);
                intent.putExtra("wrong_question", questionBean);
                startActivity(intent);
            }
        });
        rv_all_question.setAdapter(adapter);
    }

    private void initData() {
        questionDAO = new QuestionDAO(this);
        allQuestionList = questionDAO.qureyAllQuestionById();
        if (questionDAO.qureyAllQuestionById() != null) {
            allQuestionList = questionDAO.qureyAllQuestionById();
        }
    }

    /**
     * 选择排序方式
     */
    private void selectType() {
        String s = (String) spinner_question_sort.getSelectedItem();
        if ("默认排序(根据ID)".equals(s)) {
            if (questionDAO.qureyAllQuestionById() != null) {
                allQuestionList = questionDAO.qureyAllQuestionById();
            }
        } else if ("根据做题次数排序".equals(s)) {
            if (questionDAO.qureyAllQuestionByTestTimes() != null) {
                allQuestionList = null;
                allQuestionList = questionDAO.qureyAllQuestionByTestTimes();
                adapter.setList(allQuestionList);
                adapter.notifyDataSetChanged();
                for (QuestionBean q :
                        allQuestionList) {
                    Log.d("question", q.toString());
                }
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectType();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
