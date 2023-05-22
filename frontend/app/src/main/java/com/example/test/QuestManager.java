package com.example.test;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.subjects.PublishSubject;

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
        TextView textView = activity.findViewById(R.id.txtScore);
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

    public static void addQuestionResult(int question_id, boolean was_answer_right) throws JsonProcessingException {
        Map json_template = Map.of(
                "question_id", question_id,
                "was_answer_right", was_answer_right);
        String json = Constants.object_mapper.writeValueAsString(json_template);
        json_question_results = json_question_results.concat(json);
        json_counter++;
        if (json_counter < 5) {
            json_question_results = json_question_results.concat(",");
        } else {
            json_question_results = json_question_results.concat("]");
        }
    }

    public static String getJsonQuestionResults() {
        return json_question_results;
    }

    public static void tickTime() {
        time_ -= 1;
        subject.onNext(time_);
    }

    public static int getTime() {
        return time_;
    }

    public static void clear() {
        locationStatuses bank_status = locationStatuses.NOT_PASSED;
        locationStatuses school_status = locationStatuses.NOT_PASSED;
        locationStatuses mall_status = locationStatuses.NOT_PASSED;
        locationStatuses shop_status = locationStatuses.NOT_PASSED;
        locationStatuses fin_org_status = locationStatuses.NOT_PASSED;
        coins_ = Constants.coins;
        time_ = Constants.time;
    }

    public static PublishSubject<Integer> subject = PublishSubject.create();

    private static int coins_ = Constants.coins;
    private static int time_ = Constants.time;

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

    private static String json_question_results = "[";
    private static int json_counter = 0;
}
