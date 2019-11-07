package com.example.questionbank.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.questionbank.R;
import com.example.questionbank.utils.ImmersiveStatusBarSettings;


/**
 * 导航页 自动销毁
 */
public class LaunchViewActivity extends Activity {
    Intent intent;
    ImageView loading_page_top_img_id;
    ImageView loading_page_text_iv_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_view);
        new ImmersiveStatusBarSettings().settingStatusBar(this);
//        initView();
        handler.sendEmptyMessageDelayed(0, 2000);

    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            intent = new Intent(LaunchViewActivity.this, MainActivity.class);
            startActivity(intent);
            //执行一次后销毁本页面
            finish();
        }
    };

//    private void initView(){
//        loading_page_top_img_id = findViewById(R.id.loading_page_top_img_id);
//        loading_page_text_iv_id = findViewById(R.id.loading_page_text_iv_id);
//        loading_page_top_img_id.setAnimation(AnimationUtils.loadAnimation(this,R.anim.head_in));
//        loading_page_text_iv_id.setAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_sliding_in));
//
//
//    }

}
