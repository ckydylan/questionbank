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
import com.example.questionbank.adapter.WrongQuestionAdapter;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.db.QuestionDAO;
import com.example.questionbank.utils.ImmersiveStatusBarSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cky
 * date 2019-10-12
 * 错题集
 */
public class WrongQuestionCollectionActivity extends Activity implements Spinner.OnItemSelectedListener {
    TextView tv_title;
    RecyclerView rv_wrong_question;
    Spinner spinner_question_sort;
    private WrongQuestionAdapter adapter;
    private List<QuestionBean> wrongList;
    private QuestionDAO questionDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_question_collection);
        initView();
    }

    private void initView() {
        new ImmersiveStatusBarSettings().settingStatusBar(this);
        tv_title = findViewById(R.id.tv_title);
        spinner_question_sort = findViewById(R.id.spinner_question_sort);
        rv_wrong_question = findViewById(R.id.rv_wrong_question);
        rv_wrong_question.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        tv_title.setText("错题集");

        initData();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_wrong_question.setLayoutManager(manager);
        adapter = new WrongQuestionAdapter(wrongList);

        adapter.setOnItemClickListener(new WrongQuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                QuestionBean questionBean = wrongList.get(position);
                Intent intent = new Intent(WrongQuestionCollectionActivity.this, WrongQuestionDetailActivity.class);
                intent.putExtra("wrong_question", questionBean);
                // todo 传递点击的对象
                startActivity(intent);
            }
        });
        rv_wrong_question.setAdapter(adapter);
    }

    private void initData() {
        wrongList = new ArrayList<>();
        questionDAO = new QuestionDAO(this);
        if (questionDAO.qureyWrongQuestionByid() != null) {
            wrongList = questionDAO.qureyWrongQuestionByid();
            Log.d("question", questionDAO.qureyWrongQuestionByid().size() + "");
        } else {
            Toast.makeText(this, "当前没有错题", Toast.LENGTH_SHORT).show();
            return;
        }
        spinner_question_sort.setOnItemSelectedListener(this);
    }

    /**
     * 选择排序方式
     */
    private void selectType() {
        String s = (String) spinner_question_sort.getSelectedItem();
        if ("默认排序(根据ID)".equals(s)) {
            if (questionDAO.qureyWrongQuestionByid() != null) {
                wrongList = questionDAO.qureyWrongQuestionByid();
                Log.d("question", questionDAO.qureyWrongQuestionByid().size() + "");
            } else {
                Toast.makeText(this, "当前没有错题", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if ("根据错题次数排序".equals(s)) {
            if (questionDAO.qureyWrongQuestionByWrongTimes() != null) {
                wrongList = null;
                wrongList = questionDAO.qureyWrongQuestionByWrongTimes();
                for (int i = 0; i < wrongList.size(); i++) {
                    Log.d("wrongList", wrongList.get(i).toString());
                }
                adapter.notifyDataSetChanged();
                Log.d("question", questionDAO.qureyWrongQuestionByid().size() + "");
            } else {
                Toast.makeText(this, "当前没有错题", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Log.d("spinner_question_sort", s);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectType();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
