package com.example.test;

public class Question {

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getWrong_answers() {
        return wrong_answers;
    }

    public void setWrong_answers(String[] wrong_answers) {
        this.wrong_answers = wrong_answers;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public String getQuest_name() {
        return quest_name;
    }

    public void setQuest_name(String quest_name) {
        this.quest_name = quest_name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String question;
    public String[] wrong_answers;
    public String right_answer;
    public String quest_name;
    public int coins;
    public String location;
    public int question_id;

}
