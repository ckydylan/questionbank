package com.example.questionbank.bean;

public class Recoder {
    int index;
    String answer;
    String myanswer;

    public Recoder(int index, String answer, String myanswer) {
        this.index = index;
        this.answer = answer;
        this.myanswer = myanswer;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMyanswer() {
        return myanswer;
    }

    public void setMyanswer(String myanswer) {
        this.myanswer = myanswer;
    }
}
