package com.example.questionbank.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.questionbank.R;
import com.example.questionbank.adapter.AnswerSheetAdapter;
import com.example.questionbank.adapter.QuestionAdapter;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.db.QuestionDAO;
import com.example.questionbank.utils.ImmersiveStatusBarSettings;
import com.example.questionbank.utils.RollOutUtil;

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
    View view_quit;
    AnswerSheetAdapter adapter;

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
        iv_back.setOnClickListener(v -> {
            alert();

        });
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

    private void alert(){
        AlertDialog.Builder builder  = new AlertDialog.Builder(DoQuestionActivity.this);
        builder.setTitle("警告" ) ;
        builder.setMessage("退出后将无法上传题目，是否要退出" ) ;
        builder.setPositiveButton("是", (dialog, which) -> DoQuestionActivity.this.finish());
        builder.setNegativeButton("否", null);
        builder.show();
    }
    /**
     * 初始化问题内容
     */
    private void initQuestion() {
        QuestionDAO questionDAO = new QuestionDAO(this);
        questionBeanList = questionDAO.qureyQuestion(RollOutUtil.rollOut(RollOutUtil.ALL_RANDOM,100,100));
    }


    /**
     *      * 创建popupWindow
     *      * @param view View
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
        view_quit = layout.findViewById(R.id.view_quit);
        view_quit.setOnClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(this, 8);
        rv_answer_sheet.setLayoutManager(manager);
        adapter = new AnswerSheetAdapter();
        adapter.setJumpViewpager(position -> {
            vp_question.setCurrentItem(position);
            popupWindow.dismiss();
        });
        rv_answer_sheet.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_right:
                bottomwindow(iv_right);
                break;
            case R.id.view_quit:
                popupWindow.dismiss();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        alert();
    }
}
