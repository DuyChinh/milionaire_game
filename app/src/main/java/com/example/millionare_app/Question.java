package com.example.millionare_app;

import java.util.List;

public class Question {
    private int id;
    private String content;
    private int score;
    private List<Answer> listAnswer;

    public Question(int id, String content, int score, List<Answer> listAnswer) {
        this.id = id;
        this.content = content;
        this.score = score;
        this.listAnswer = listAnswer;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getScore() {
        return score;
    }

    public List<Answer> getListAnswer() {
        return listAnswer;
    }

    public void setId(int number) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setListAnswer(List<Answer> listAnswer) {
        this.listAnswer = listAnswer;
    }

    public int setScore(int score) {
        return this.score = score;
    }
}
