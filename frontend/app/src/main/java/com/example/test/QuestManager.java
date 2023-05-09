package com.example.test;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {
    public static Question getQuestion(int coins, String location) {
        return questions_.stream()
                .filter(question -> question.coins == coins)
                .filter(question -> question.location.equals(location))
                .findAny().get();
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

    public static void clear() {
        locationStatuses bank_status = locationStatuses.NOT_PASSED;
        locationStatuses school_status = locationStatuses.NOT_PASSED;
        locationStatuses mall_status = locationStatuses.NOT_PASSED;
        locationStatuses shop_status = locationStatuses.NOT_PASSED;
        locationStatuses fin_org_status = locationStatuses.NOT_PASSED;
        coins_ = Constants.coins;
    }

    private static int coins_ = Constants.coins;

    public static locationStatuses school_status = locationStatuses.NOT_PASSED;
    public static locationStatuses bank_status = locationStatuses.NOT_PASSED;
    public static locationStatuses mall_status = locationStatuses.NOT_PASSED;
    public static locationStatuses shop_status = locationStatuses.NOT_PASSED;
    public static locationStatuses fin_org_status = locationStatuses.NOT_PASSED;

    public enum locationStatuses {
        NOT_PASSED,
        CORRECT_ANSWER,
        WRONG_ANSWER
    }
}
