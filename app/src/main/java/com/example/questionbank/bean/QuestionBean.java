package com.example.questionbank.bean;

public class QuestionBean {
    int id;
    String question;
    String type;
    String select_A;
    String select_B;
    String select_C;
    String select_D;
    String answer;

    public QuestionBean(){

    }
    public QuestionBean(int id, String question, String type, String select_A, String select_B, String select_C, String select_D, String answer) {
        this.id = id;
        this.question = question;
        this.type = type;
        this.select_A = select_A;
        this.select_B = select_B;
        this.select_C = select_C;
        this.select_D = select_D;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSelect_A() {
        return select_A;
    }

    public void setSelect_A(String select_A) {
        this.select_A = select_A;
    }

    public String getSelect_B() {
        return select_B;
    }

    public void setSelect_B(String select_B) {
        this.select_B = select_B;
    }

    public String getSelect_C() {
        return select_C;
    }

    public void setSelect_C(String select_C) {
        this.select_C = select_C;
    }

    public String getSelect_D() {
        return select_D;
    }

    public void setSelect_D(String select_D) {
        this.select_D = select_D;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
