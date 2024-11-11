package com.example.millionare_app;

import java.util.List;

public class Question {
    private int id;
    private String content;
    private List<Answer> listAnswer;

    public Question(int id, String content, List<Answer> listAnswer) {
        this.id = id;
        this.content = content;
        this.listAnswer = listAnswer;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
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
}
