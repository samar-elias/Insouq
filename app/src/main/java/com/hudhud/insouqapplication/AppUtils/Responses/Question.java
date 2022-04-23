package com.hudhud.insouqapplication.AppUtils.Responses;

public class Question {
    private String id, question, answerImg, answerImg2, answerImg3, arQuestion, arAnswerImg, arAnswerImg2, arAnswerImg3, isDeleted;
    private boolean isVisible;

    public Question() {
        this.id = "";
        this.question = "";
        this.answerImg = "";
        this.answerImg2 = "";
        this.answerImg3 = "";
        this.arQuestion = "";
        this.arAnswerImg = "";
        this.arAnswerImg2 = "";
        this.arAnswerImg3 = "";
        this.isDeleted = "";
        this.isVisible = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerImg() {
        return answerImg;
    }

    public void setAnswerImg(String answerImg) {
        this.answerImg = answerImg;
    }

    public String getAnswerImg2() {
        return answerImg2;
    }

    public void setAnswerImg2(String answerImg2) {
        this.answerImg2 = answerImg2;
    }

    public String getAnswerImg3() {
        return answerImg3;
    }

    public void setAnswerImg3(String answerImg3) {
        this.answerImg3 = answerImg3;
    }

    public String getArQuestion() {
        return arQuestion;
    }

    public void setArQuestion(String arQuestion) {
        this.arQuestion = arQuestion;
    }

    public String getArAnswerImg() {
        return arAnswerImg;
    }

    public void setArAnswerImg(String arAnswerImg) {
        this.arAnswerImg = arAnswerImg;
    }

    public String getArAnswerImg2() {
        return arAnswerImg2;
    }

    public void setArAnswerImg2(String arAnswerImg2) {
        this.arAnswerImg2 = arAnswerImg2;
    }

    public String getArAnswerImg3() {
        return arAnswerImg3;
    }

    public void setArAnswerImg3(String arAnswerImg3) {
        this.arAnswerImg3 = arAnswerImg3;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
