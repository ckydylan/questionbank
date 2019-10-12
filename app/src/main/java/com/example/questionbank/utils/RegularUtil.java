package com.example.questionbank.utils;

import android.content.Context;
import android.util.Log;

import com.example.questionbank.R;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.db.DatabaseHelper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
    public static final String REGEX_SINGLE_QUESTION = "1\\d{4}\\..*?(\\s.*?)?。";
    public static final String REGEX_FULL_SINGLE_QUESTION = "1\\d{4}\\..*?(\\s.*)?。(\\s.*){2,5}(?=(1\\d{4}|!stop!))";
    public static final String REGEX_JUDGE_QUESTION = "2\\d{4}\\..*?(\\s.*?){1,2}。";
    public static final String REGEX_SELECT_A = "A\\..*?\\s?(?=B\\.)";
    public static final String REGEX_SELECT_B = "B\\..*?\\s?(?=C\\.)";
    public static final String REGEX_SELECT_C = "C\\..*?\\s?(?=D\\.)";
    public static final String REGEX_SELECT_D = "D\\..*?(?=(11\\d{3}|12\\d{3}|13\\d{3}|14\\d{3}|!stop!))";
    public static final String REGEX_ANSWER = "(\\(|（) [A,B,C,D,F,T] (）|\\))";
    public static final String REGEX_FOOT_NUM = "- \\d{1,3} -";

    public static ArrayList<QuestionBean> processQuestion(Context context) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(context.getAssets().open("questionbank1.txt")));

        StringBuilder sb = new StringBuilder();
        String x;

        while ((x = in.readLine()) != null){
            sb.append(x);
        }
        in.close();

        //剔除页脚
        String file = sb.toString();
        file = file.replaceAll(REGEX_FOOT_NUM,"");
        //获取题目（单选）
        Pattern selectPattern = Pattern.compile(REGEX_SINGLE_QUESTION);
        Pattern fullPattern = Pattern.compile(REGEX_FULL_SINGLE_QUESTION);
        Pattern answerPatern = Pattern.compile(REGEX_ANSWER);

        Pattern aPatern = Pattern.compile(REGEX_SELECT_A);
        Pattern bPatern = Pattern.compile(REGEX_SELECT_B);
        Pattern cPatern = Pattern.compile(REGEX_SELECT_C);
        Pattern dPatern = Pattern.compile(REGEX_SELECT_D);

        Matcher selectMatcher = selectPattern.matcher(file);
        Matcher selectFullMatcher = fullPattern.matcher(file);
        ArrayList<QuestionBean> questionList = new ArrayList<>(2000);

        while(selectMatcher.find()){
            String question = selectMatcher.group();
            QuestionBean questionBean = new QuestionBean();
            Matcher ansMatcher = answerPatern.matcher(question);
            if(ansMatcher.find()){
                //LogUtil.loge(">>>>>>",ansMatcher.group());
                String ans = ansMatcher.group();
                ans = ans.replaceAll("(\\(|（) ","").replaceAll(" (）|\\))","").trim();
                questionBean.setAnswer(ans);
                questionBean.setType("choice");
            }
            question = question.replaceAll(REGEX_ANSWER,"( )");
            questionBean.setQuestion(question);
            questionList.add(questionBean);
        }
        //LogUtil.loge(">>>>>>","size"+questionList.size());
        //获取选项

        Matcher aMatcher = aPatern.matcher(file);
        int an = 0;
        while(aMatcher.find()){
            questionList.get(an).setSelect_A(aMatcher.group());
            an++;
        }

        Matcher bMatcher = bPatern.matcher(file);
        int bn = 0;
        while(bMatcher.find()){
            questionList.get(bn).setSelect_B(bMatcher.group());
            bn++;
        }

        Matcher cMatcher = cPatern.matcher(file);
        int cn = 0;
        while(cMatcher.find()){
            questionList.get(cn).setSelect_C(cMatcher.group());
            cn++;
        }

        Matcher dMatcher = dPatern.matcher(file);
        int dn = 0;

        while(dMatcher.find()){
            questionList.get(dn).setSelect_D(dMatcher.group());
            dn++;
        }

        Pattern judgePattern = Pattern.compile(REGEX_JUDGE_QUESTION);
        Matcher jMatcher = judgePattern.matcher(file);
        //int jn = 0;
        while(jMatcher.find()){
            String question = jMatcher.group();
            QuestionBean questionBean = new QuestionBean();
            Matcher ansMatcher = answerPatern.matcher(question);
            if(ansMatcher.find()){
                String ans = ansMatcher.group();
                ans = ans.replaceAll("(\\(|（) ","").replaceAll(" (）|\\))","").trim();
                questionBean.setAnswer(ans);
                questionBean.setType("judge");
            }
            question = question.replaceAll(REGEX_ANSWER,"( )");
            questionBean.setQuestion(question);
            questionList.add(questionBean);
        }
        return questionList;
    }
}
