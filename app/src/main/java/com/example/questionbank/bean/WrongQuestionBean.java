package com.example.questionbank.bean;

import java.util.List;

public class WrongQuestionBean {
    private List<QuestionBean> choices;
    private List<QuestionBean> judges;

    public WrongQuestionBean(List<QuestionBean> choices, List<QuestionBean> judges) {
        this.choices = choices;
        this.judges = judges;
    }

    public List<QuestionBean> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionBean> choices) {
        this.choices = choices;
    }

    public List<QuestionBean> getJudges() {
        return judges;
    }

    public void setJudges(List<QuestionBean> judges) {
        this.judges = judges;
    }
}
