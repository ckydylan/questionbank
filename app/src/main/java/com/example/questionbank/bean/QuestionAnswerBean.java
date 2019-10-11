package com.example.questionbank.bean;

/**
 * @author cky
 * date 2019-10-11
 */
public class QuestionAnswerBean {
    private int questionNum;
    private int answerStatus;


    public QuestionAnswerBean(int questionNum, int answerStatus) {
        this.questionNum = questionNum;
        this.answerStatus = answerStatus;
    }


    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public int getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(int answerStatus) {
        this.answerStatus = answerStatus;
    }
}
