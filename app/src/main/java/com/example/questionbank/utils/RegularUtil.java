package com.example.questionbank.utils;

import android.content.Context;
import android.util.Log;

import com.example.questionbank.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RegularUtil {
    public static final String REGEX_SINGLE_QUESTION = "1\\d{4}\\..*。";
    public static final String REGEX_JUDGE_QUESTION = "2\\d{4}\\..*。";
    public static final String REGEX_SELECT_A = "A\\..*\\s(?=B)";
    public static final String REGEX_SELECT_B = "B\\..*\\s(?=C)";
    public static final String REGEX_SELECT_C = "C\\..*\\s(?=D)";
    public static final String REGEX_SELECT_D = "D\\..*\\s(?=\\d{5})";
    public static final String REGEX_ANSWER = "（ [A,B,C,D,F,T] ）";
    public static final String REGEX_FOOT_NUM = "- \\d{1,3} -";

    public static void processQuestion(Context context) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(context.getAssets().open("questionbank1.txt")));

        StringBuilder sb = new StringBuilder();
        String x;

        while ((x = in.readLine()) != null){
            sb.append(x);
        }

        //todo：剔除页脚
        String file = sb.toString();
        file = file.replaceAll("- \\d{1,3} -","");
        //todo:获取题目


        //todo：获取选项

        //todo：获取答案
        Log.e("sb", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
        LogUtil.loge("sb",sb.toString());
        Log.e("sb", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );

    }
}
