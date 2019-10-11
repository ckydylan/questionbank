package com.example.questionbank.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.questionbank.R;
import com.example.questionbank.adapter.AnswerSheetAdapter;
import com.example.questionbank.adapter.QuestionAdapter;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.utils.ImmersiveStatusBarSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cky
 * date 2019-10-10
 * 做题目
 */
public class DoQuestionActivity extends FragmentActivity implements View.OnClickListener {
    public static List<QuestionBean> questionBeanList;
    private PopupWindow popupWindow;
    TextView tv_title;
    ImageView iv_back, iv_right;
    ViewPager vp_question;
    RecyclerView rv_answer_sheet;
    QuestionAdapter questionAdapter;
    ImageView iv_right_or_wrong;
    TextView tv_question_num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_question);
        initQuestion();
        initView();
    }

    private void initView() {
        new ImmersiveStatusBarSettings().settingStatusBar(this);
        vp_question = findViewById(R.id.vp_question);
        tv_title = findViewById(R.id.tv_title);
        iv_back = findViewById(R.id.iv_back);
        iv_right = findViewById(R.id.iv_right);

        iv_right.setOnClickListener(this);
        tv_title.setText("答题");
        iv_right.setImageResource(R.mipmap.question_right_menu);
        iv_back.setImageResource(R.mipmap.back);
        iv_back.setOnClickListener(v -> this.finish());
        vp_question.setCurrentItem(0);
        questionAdapter = new QuestionAdapter(getSupportFragmentManager());
        vp_question.setAdapter(questionAdapter);
        vp_question.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化问题内容
     */
    private void initQuestion() {
        String question = null;
        String type = null;
        String select_A = null;
        String select_B = null;
        String select_C = null;
        String select_D = null;
        String answer = null;
        questionBeanList = new ArrayList<>();
        question = "对坐标计算中关于“基点”、“节点”的概念下面哪种说法是错误的";
        type = "选择";
        select_A = "逼近线段的交点称为节点";
        select_B = "各相邻几何元素的交点或切点称为基点";
        select_C = "各相邻几何元素的交点或切点称为节点";
        select_D = "节点和基点是两个不同的概念";
        answer = "A";
        questionBeanList.add(new QuestionBean(1, question, type, select_A, select_B, select_C, select_D, answer));

        question = "对坐标计算中关于“基点”、“节点”的概念下面哪种说法是错误的2";
        type = "选择";
        select_A = "逼近线段的交点称为节点2";
        select_B = "各相邻几何元素的交点或切点称为基点";
        select_C = "各相邻几何元素的交点或切点称为节点";
        select_D = "节点和基点是两个不同的概念";
        answer = "A";
        questionBeanList.add(new QuestionBean(2, question, type, select_A, select_B, select_C, select_D, answer));

        question = "对坐标计算中关于“基点”、“节点”的概念下面哪种说法是错误的3";
        type = "判断";
        select_A = "T";
        select_B = "F";
        select_C = "";
        select_D = "";
        answer = "A";
        questionBeanList.add(new QuestionBean(3, question, type, select_A, select_B, select_C, select_D, answer));

        question = "对坐标计算中关于“基点”、“节点”的概念下面哪种说法是错误的4";
        type = "判断";
        select_A = "T";
        select_B = "F";
        select_C = "";
        select_D = "";
        answer = "A";
        questionBeanList.add(new QuestionBean(4, question, type, select_A, select_B, select_C, select_D, answer));
    }


    /**
     *      * 创建popupWindow
     * <p>
     *      *popupWindow 是全局定义的，根据自己需要惊醒定义
     *      * @param view View 比如：btn_ok的点击后触发popupWindow，则view就是id为btn_ok对应的view
     *      
     */
    private void bottomwindow(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.popupwindow_answer_sheet, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加按键事件监听
//        setButtonListeners(layout);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
//        popupWindow.setOnDismissListener(new poponDismissListener());
//        backgroundAlpha(1f);

        rv_answer_sheet = layout.findViewById(R.id.rv_answer_sheet);
        iv_right_or_wrong = layout.findViewById(R.id.iv_right_or_wrong);
        tv_question_num = layout.findViewById(R.id.tv_question_num);

        GridLayoutManager manager = new GridLayoutManager(this,2);
        rv_answer_sheet.setLayoutManager(manager);
        rv_answer_sheet.setAdapter(new AnswerSheetAdapter());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_right:
                bottomwindow(iv_right);
                break;
        }
    }


}
