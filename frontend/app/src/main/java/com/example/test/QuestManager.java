package com.example.test;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {
    public static Question getQuestion(int coins, String location) {
        return questions_.stream().filter(question -> question.coins == coins).findFirst().get();
    }

    public static void putQuestions(List<Question> list) {
        questions_ = list;
    }

    private static List<Question> questions_ = new ArrayList<>();

    public static void updateCoins(AppCompatActivity activity) {
        TextView textView = activity.findViewById(R.id.txtFiveHundred);
        textView.setText(Integer.toString(coins_));
    }

    public static void addCoins(int amount) {
        coins_ += amount;
    }

    public static void removeCoins(int amount) {
        coins_ -= amount;
    }

    public static int getCoins() {
        return coins_;
    }

    private static int coins_ = 0;
}
