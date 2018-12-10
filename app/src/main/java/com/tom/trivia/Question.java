package com.tom.trivia;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String category;
    private String type;
    private String question;
    private ArrayList<String> answers;
    private String correctAnswer;
    private String difficulty;

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void setCorrectAnswer(String correct_answer) {
        this.correctAnswer = correct_answer;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
