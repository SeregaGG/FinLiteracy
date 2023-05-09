package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Task extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        Bundle arguments = getIntent().getExtras();
        location_ = arguments.get("location").toString();

        setContentView(R.layout.activity_task_choosing);
        QuestManager.updateCoins(this);
    }

    // activity_task_choosing
    public void easyTask(View view) {
        setContentView(R.layout.activity_task);
        QuestManager.removeCoins(kQuestionPrice);
        QuestManager.updateCoins(this);
        question_ = QuestManager.getQuestion(kEasyTaskReward, location_);

        TextView textView = findViewById(R.id.taskDescription);
        TextView a = findViewById(R.id.txtA);
        TextView b = findViewById(R.id.txtB);
        TextView c = findViewById(R.id.txtC);
        textView.setText(question_.question);
        a.setText(question_.right_answer);
        b.setText(question_.wrong_answers[0]);
        if (question_.wrong_answers.length > 1) {
            c.setText(question_.wrong_answers[1]);
        }

        buffer_ = kEasyTaskReward;
    }

    // activity_task_choosing
    public void middleTask(View view) {
        setContentView(R.layout.activity_task);
        QuestManager.removeCoins(kQuestionPrice);
        QuestManager.updateCoins(this);
        question_ = QuestManager.getQuestion(kMiddleTaskReward, location_);

        TextView textView = findViewById(R.id.taskDescription);
        TextView a = findViewById(R.id.txtA);
        TextView b = findViewById(R.id.txtB);
        TextView c = findViewById(R.id.txtC);
        textView.setText(question_.question);
        a.setText(question_.right_answer);
        b.setText(question_.wrong_answers[0]);
        if (question_.wrong_answers.length > 1) {
            c.setText(question_.wrong_answers[1]);
        }

        buffer_ = kMiddleTaskReward;
    }

    // activity_task_choosing
    public void hardTask(View view) {
        setContentView(R.layout.activity_task);
        QuestManager.removeCoins(kQuestionPrice);
        QuestManager.updateCoins(this);
        question_ = QuestManager.getQuestion(kHardTaskReward, location_);

        TextView textView = findViewById(R.id.taskDescription);
        TextView a = findViewById(R.id.txtA);
        TextView b = findViewById(R.id.txtB);
        TextView c = findViewById(R.id.txtC);
        textView.setText(question_.question);
        a.setText(question_.right_answer);
        b.setText(question_.wrong_answers[0]);
        if (question_.wrong_answers.length > 1) {
            c.setText(question_.wrong_answers[1]);
        }

        buffer_ = kHardTaskReward;
    }

    public void returnToMap(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    // activity_task
    public void onClickFirst(View view) {
        TextView a = findViewById(R.id.txtA);
        answer_ = (String) a.getText();
        view.setBackgroundResource(R.drawable.rectangle_bg_gray_300);
    }

    // activity_task
    public void onClickSecond(View view) {
        TextView a = findViewById(R.id.txtB);
        answer_ = (String) a.getText();
        view.setBackgroundResource(R.drawable.rectangle_bg_gray_300);
    }

    // activity_task
    public void onClickThird(View view) {
        TextView a = findViewById(R.id.txtC);
        answer_ = (String) a.getText();
        view.setBackgroundResource(R.drawable.rectangle_bg_gray_300);
    }

    public void onClickAnswer(View view) {
        if (answer_.equals(question_.right_answer)) {
            QuestManager.addCoins(buffer_);
            QuestManager.updateCoins(this);
            buffer_ = 0;
            updateLocationStatuses(true);
            setContentView(R.layout.activity_right_answer);
            TextView textView = findViewById(R.id.txtFiveHundredOne);
            textView.setText("Твой счет: " + QuestManager.getCoins());
            QuestManager.updateCoins(this);
        } else {
            buffer_ = 0;
            updateLocationStatuses(false);
            setContentView(R.layout.activity_wrong_answer);
            TextView textView = findViewById(R.id.txtFiveHundredOne);
            textView.setText("Твой счет: " + QuestManager.getCoins());
            QuestManager.updateCoins(this);

        }
    }

    private void updateLocationStatuses(boolean is_passed) {
        var result = is_passed ? QuestManager.locationStatuses.CORRECT_ANSWER : QuestManager.locationStatuses.WRONG_ANSWER;
        if (location_.equals(Constants.loc_school)) {
            QuestManager.school_status = result;
        } else if (location_.equals(Constants.loc_shop)) {
            QuestManager.shop_status = result;
        } else if (location_.equals(Constants.loc_entertainment_center)) {
            QuestManager.mall_status = result;
        } else if (location_.equals(Constants.loc_bank)) {
            QuestManager.bank_status = result;
        } else if (location_.equals(Constants.loc_fin_org)) {
            QuestManager.fin_org_status = result;
        } else {
            throw new IllegalArgumentException("bad location");
        }
    }

    private static final int kEasyTaskReward = 200;
    private static final int kMiddleTaskReward = 300;
    private static final int kHardTaskReward = 400;
    private static final int kQuestionPrice = 100;
    Question question_;
    String location_ = "";
    String answer_ = "";
    int buffer_ = 0;
}