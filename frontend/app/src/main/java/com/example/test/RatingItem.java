package com.example.test;

public class RatingItem {
    private String position;
    private String name;
    private String score;

    public RatingItem(int position, String name, int score) {
        this.position = Integer.toString(position);
        this.name = name;
        this.score = Integer.toString(score);
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = Integer.toString(position);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = Integer.toString(score);
    }
}
