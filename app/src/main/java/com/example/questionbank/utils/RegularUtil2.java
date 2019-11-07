package com.example.questionbank.utils;

import android.content.Context;
import android.util.Log;

import com.example.questionbank.bean.QuestionBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil2 {
    public static final String REGEX_FULL_QUESTION = "\\d{1,3}\\.(\\s*.*?)答案:[A,B,C,D]{1,4}";

    public static final String NO_MUTIPLE_QUESTION = "\\d{1,3}\\.(\\s*.*?)[。,\\?]";
    public static final String MUTIPLE_QUESTION = "\\d{1,3}\\.(\\s*.*?)\\(多选\\)";
    public static final String REGEX_SELECT_A = "A\\..*?\\s?(?=B\\.)";
    public static final String REGEX_SELECT_B = "B\\..*?\\s?(?=C\\.)";
    public static final String REGEX_SELECT_C = "C\\..*?\\s?(?=D\\.)";
    public static final String REGEX_SELECT_D = "D\\.(.*\\s*)(?=答案)";
    public static final String REGEX_ANSWER = "答案:[A,B,C,D]{1,4}";

    public static ArrayList<QuestionBean> processQuestion(Context context) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(context.getAssets().open("tiku.txt")));

        StringBuilder sb = new StringBuilder();
        String x;

        while ((x = in.readLine()) != null){
            sb.append(x);
        }
        in.close();

        String file = sb.toString();
//        LogUtil.loge(">>>>>>","selectMatcher:"+file);
//        //获取题目（所有题干）

        Pattern selectPattern = Pattern.compile(REGEX_FULL_QUESTION);
        Pattern multiplePattern = Pattern.compile(MUTIPLE_QUESTION);
        Pattern noMultiPattern = Pattern.compile(NO_MUTIPLE_QUESTION);
//        Pattern fullPattern = Pattern.compile(REGEX_FULL_SINGLE_QUESTION);
        Pattern answerPatern = Pattern.compile(REGEX_ANSWER);
//
        Pattern aPatern = Pattern.compile(REGEX_SELECT_A);
        Pattern bPatern = Pattern.compile(REGEX_SELECT_B);
        Pattern cPatern = Pattern.compile(REGEX_SELECT_C);
        Pattern dPatern = Pattern.compile(REGEX_SELECT_D);
//
        Matcher selectMatcher = selectPattern.matcher(file);
//        Matcher selectFullMatcher = fullPattern.matcher(file);
        ArrayList<QuestionBean> questionList = new ArrayList<>(1024);

        while(selectMatcher.find()){
            String fullQuestion = selectMatcher.group().trim();
            //LogUtil.loge(">>>>>>","selectMatcher:"+question);
            QuestionBean questionBean = new QuestionBean();
            //筛选题目
            Matcher multipleMatcher = multiplePattern.matcher(fullQuestion);
            if(multipleMatcher.find()){
                String multipleQuestion = multipleMatcher.group();
                questionBean.setQuestion(multipleQuestion.trim());
                questionBean.setType("multiple");
                //LogUtil.loge(">>>>>>","multipleQuestion:"+multipleQuestion);
            }else {
                Matcher noMultipleMatcher = noMultiPattern.matcher(fullQuestion);
                if(noMultipleMatcher.find()){
                    String noMultipleQuestion = noMultipleMatcher.group();
                    questionBean.setQuestion(noMultipleQuestion.trim());
                    //LogUtil.loge(">>>>>>","multipleQuestion:"+noMultipleQuestion);
                }
            }
            //筛选答案A:
            Matcher aMatcher = aPatern.matcher(fullQuestion);
            if(aMatcher.find()){
                String an = aMatcher.group();
//                LogUtil.loge(">>>>>>","a:"+an);
                questionBean.setSelect_A(an);
            }
            //B:
            Matcher bMatcher = bPatern.matcher(fullQuestion);
            String bn = "error";
            if(bMatcher.find()){
                bn = bMatcher.group();
//                LogUtil.loge(">>>>>>","b:"+bn);
                if(questionBean.getType() == null){
                    questionBean.setType("choice");
                }
            }else{
                bn ="B.错误";
                if(questionBean.getType() == null){
                    questionBean.setType("judge");
                }
            }
            questionBean.setSelect_B(bn);
            //C:
            Matcher cMatcher = cPatern.matcher(fullQuestion);
            if(cMatcher.find()){
                String cn = cMatcher.group();
                questionBean.setSelect_C(cn);
            }
            //D:
            Matcher dMatcher = dPatern.matcher(fullQuestion);
            if(dMatcher.find()){
                String dn = dMatcher.group();
                questionBean.setSelect_D(dn);
                //LogUtil.loge(">>>>>>","multipleQuestion:"+dn);
            }
            //答案：
            Matcher ansMatcher = answerPatern.matcher(fullQuestion);
            if(ansMatcher.find()){
                String ans = ansMatcher.group();
                String an = ans.substring(3,ans.length());
                if(questionBean.getType().equals("judge")){
                    Log.e(">>>", "processQuestion: >>>"+an+">>>"+questionBean );
                    if(an.equals("A")){
                        an ="T";
                    }else {
                        an ="F";
                    }
                }
                questionBean.setAnswer(an);
                Log.e(">>>", ">>>>>>>>>>>>>>>>>>>>>" );

                Log.e(">>>", "processQuestion: >>>"+an+">>>"+questionBean );

                //LogUtil.loge(">>>>>>","multipleQuestion:"+dn);
            }
            questionList.add(questionBean);
 //           LogUtil.loge(">>>>>>","questionBean:"+questionBean);

//            Matcher ansMatcher = answerPatern.matcher(question);
//            if(ansMatcher.find()){
//                //LogUtil.loge(">>>>>>",ansMatcher.group());
//                String ans = ansMatcher.group();
//                ans = ans.replaceAll("(\\(|（) ","").replaceAll(" (）|\\))","").trim();
//                questionBean.setAnswer(ans);
//                questionBean.setType("choice");
//            }
//            question = question.replaceAll(REGEX_ANSWER,"( )");
//            questionBean.setQuestion(question);
//            questionList.add(questionBean);
        }
        //LogUtil.loge(">>>>>>","size"+questionList.size());
        //获取选项

//        Matcher aMatcher = aPatern.matcher(file);
//        int an = 0;
//        while(aMatcher.find()){
//            questionList.get(an).setSelect_A(aMatcher.group());
//            an++;
//        }
//
//        Matcher bMatcher = bPatern.matcher(file);
//        int bn = 0;
//        while(bMatcher.find()){
//            questionList.get(bn).setSelect_B(bMatcher.group());
//            bn++;
//        }
//
//        Matcher cMatcher = cPatern.matcher(file);
//        int cn = 0;
//        while(cMatcher.find()){
//            questionList.get(cn).setSelect_C(cMatcher.group());
//            cn++;
//        }
//
//        Matcher dMatcher = dPatern.matcher(file);
//        int dn = 0;
//
//        while(dMatcher.find()){
//            questionList.get(dn).setSelect_D(dMatcher.group());
//            dn++;
//        }
//
//        Pattern judgePattern = Pattern.compile(REGEX_JUDGE_QUESTION);
//        Matcher jMatcher = judgePattern.matcher(file);
//        //int jn = 0;
//        while(jMatcher.find()){
//            String question = jMatcher.group();
//            QuestionBean questionBean = new QuestionBean();
//            Matcher ansMatcher = answerPatern.matcher(question);
//            if(ansMatcher.find()){
//                String ans = ansMatcher.group();
//                ans = ans.replaceAll("(\\(|（) ","").replaceAll(" (）|\\))","").trim();
//                questionBean.setAnswer(ans);
//                questionBean.setType("judge");
//            }
//            question = question.replaceAll(REGEX_ANSWER,"( )");
//            questionBean.setQuestion(question);

//        }
        return questionList;
    }


}
