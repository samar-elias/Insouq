package com.hudhud.insouqapplication.AppUtils.Responses;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String question, arQuestion;
    private ArrayList<Answer> answers;

    public Question() {
        this.question = "";
        this.arQuestion = "";
        this.answers = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getArQuestion() {
        return arQuestion;
    }

    public void setArQuestion(String arQuestion) {
        this.arQuestion = arQuestion;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
