package com.example.questionbank.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.questionbank.R;
import com.example.questionbank.db.QuestionDAO;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * @author cky
 * date 2019-10-10
 */
public class SettingFragment extends Fragment {
    TextView tv_title;
    Button btn_delete_record;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_title = view.findViewById(R.id.tv_title);
        btn_delete_record = view.findViewById(R.id.btn_delete_record);
        tv_title.setText("设置");

        btn_delete_record.setOnClickListener(
                v -> {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("警告");
                    dialog.setCancelable(false);
                    dialog.setMessage("点击确定将会删除您之前的所有作题记录，点击取消返回");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new QuestionDAO(getContext()).clearQuestionBank();
                            Toast.makeText(getContext(), "删除记录成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
        );
        //加载广告
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
