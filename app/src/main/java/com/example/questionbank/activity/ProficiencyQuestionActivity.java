package com.example.questionbank.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionbank.R;
import com.example.questionbank.adapter.ProficiencyQuestionAdapter;
import com.example.questionbank.adapter.WrongQuestionAdapter;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.db.QuestionDAO;
import com.example.questionbank.utils.ImmersiveStatusBarSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cky
 * date 2019-10-13
 * 熟练题
 */
public class ProficiencyQuestionActivity extends Activity {
    RecyclerView rv_proficiency_question;
    TextView tv_title;
    private ProficiencyQuestionAdapter adapter;
    private List<QuestionBean> proficiencyList;
    private QuestionDAO questionDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proficiency_question);
        initView();
    }

    private void initView() {
        new ImmersiveStatusBarSettings().settingStatusBar(this);
        tv_title = findViewById(R.id.tv_title);
        rv_proficiency_question = findViewById(R.id.rv_proficiency_question);

        rv_proficiency_question.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        tv_title.setText("熟练题");

        initData();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_proficiency_question.setLayoutManager(manager);
        adapter = new ProficiencyQuestionAdapter(proficiencyList,this);

        adapter.setOnItemClickListener(new ProficiencyQuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                QuestionBean questionBean = proficiencyList.get(position);
                Intent intent = new Intent(ProficiencyQuestionActivity.this, WrongQuestionDetailActivity.class);
                intent.putExtra("wrong_question", questionBean);
                startActivity(intent);
            }

            @Override
            public void delete(int position) {
                Log.d("position1",""+position);
                proficiencyList.remove(position);
            }
        });
        rv_proficiency_question.setAdapter(adapter);
    }

    private void initData() {
        proficiencyList = new ArrayList<>();
        questionDAO = new QuestionDAO(this);
        if (questionDAO.qureyEzQuestionByid() != null) {
            proficiencyList = questionDAO.qureyEzQuestionByid();
            Log.d("qureyEzQuestionByid", questionDAO.qureyEzQuestionByid().size() + "");
        } else {
            Toast.makeText(this, "您还没有添加熟练题", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
