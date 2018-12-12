package com.tom.trivia;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Highscore implements Comparable<Highscore> {
    private String playerName;
    private int score;
    private String date;

    public Highscore(String name, int score) {
        this.playerName = name;
        this.score = score;
        setDate();
    }

    public Highscore(String name, int score, String date) {
        this.playerName = name;
        this.score = score;
        this.date = date;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        this.date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    @Override
    public int compareTo(Highscore h) {
        return (h.getScore() - this.score);
    }

    @Override
    public String toString() {
        return (this.playerName + " : " + this.score);
    }
}
