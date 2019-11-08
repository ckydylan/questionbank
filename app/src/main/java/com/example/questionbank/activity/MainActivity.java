package com.example.questionbank.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.questionbank.R;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.db.QuestionDAO;
import com.example.questionbank.fragment.HomeFragment;
import com.example.questionbank.fragment.QuestionFragment;
import com.example.questionbank.fragment.SettingFragment;
import com.example.questionbank.utils.AssertUtil;
import com.example.questionbank.utils.ImmersiveStatusBarSettings;
import com.example.questionbank.utils.RegularUtil2;
import com.example.questionbank.view.BottomBar;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
//todo:delete--测试regex
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ArrayList<QuestionBean> list =  RegularUtil2.processQuestion(MainActivity.this);
//                    QuestionDAO questionDAO = new QuestionDAO(MainActivity.this);
//                    questionDAO.addQuestion(list);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                AssertUtil.copyFile(MainActivity.this);
            }
        }).start();

        new ImmersiveStatusBarSettings().settingStatusBar(this);
        BottomBar bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#1A55CB")
                .addItem(HomeFragment.class,
                        "首页",
                        R.mipmap.home_before,
                        R.mipmap.home_after)
                .addItem(QuestionFragment.class,
                        "题库",
                        R.mipmap.question_before,
                        R.mipmap.question_after)
                .addItem(SettingFragment.class,
                        "设置",
                        R.mipmap.setting_before,
                        R.mipmap.setting_after)
                .build();

        //google ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });


//        Log.e("new QuestionDAO(th", "initView: "+ new QuestionDAO(this).qureyAllQuestionByTestTimes().size());
//        if(new QuestionDAO(this).qureyQnum() < 2000){
//            try {
//                new QuestionDAO(this).addQuestion(RegularUtil.processQuestion(this));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        Log.e(">>>", "initView: >>>>>"+new QuestionDAO(this).qureyQnum());
    }
}
