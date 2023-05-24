package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;

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

        QuestManager.subject.subscribe(v -> setTimerClock(v)); // TODO: Подписывается каждый раз, как создается

        setContentView(R.layout.activity_task_choosing);
        QuestManager.updateCoins(this);
    }

    // activity_task_choosing
    public void easyTask(View view) {
        setContentView(R.layout.activity_task);
        QuestManager.removeCoins(kQuestionPrice);
        QuestManager.updateCoins(this);
        question_ = QuestManager.getQuestion(kEasyTaskReward, location_);

        ImageView imageView = findViewById(R.id.img_task);
        if (location_.equals(Constants.loc_fin_org)) {
            imageView.setImageResource(R.drawable.q6);
        } else if (location_.equals(Constants.loc_shop)) {
            imageView.setImageResource(R.drawable.q2);
        } else if (location_.equals(Constants.loc_school)) {
            imageView.setImageResource(R.drawable.q4);
        } else if (location_.equals(Constants.loc_entertainment_center)) {
            imageView.setImageResource(R.drawable.q10);
        } else if (location_.equals(Constants.loc_bankomat)) {
            imageView.setImageResource(R.drawable.wizard);
        }

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

        ImageView imageView = findViewById(R.id.img_task);
        if (location_.equals(Constants.loc_school)) {
            imageView.setImageResource(R.drawable.q11);
        } else if (location_.equals(Constants.loc_shop)) {
            imageView.setImageResource(R.drawable.q7);
        } else if (location_.equals(Constants.loc_fin_org)) {
            imageView.setImageResource(R.drawable.q8);
        } else if (location_.equals(Constants.loc_bankomat)) {
            imageView.setImageResource(R.drawable.q7);
        } else if (location_.equals(Constants.loc_entertainment_center)) {
            imageView.setImageResource(R.drawable.q9);
        }
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

        ImageView imageView = findViewById(R.id.img_task);
        if (location_.equals(Constants.loc_fin_org)) {
            imageView.setImageResource(R.drawable.q5);
        } else if (location_.equals(Constants.loc_school)) {
            imageView.setImageResource(R.drawable.q11);
        } else if (location_.equals(Constants.loc_entertainment_center)) {
            imageView.setImageResource(R.drawable.q1);
        } else if (location_.equals(Constants.loc_bankomat)) {
            imageView.setImageResource(R.drawable.q3);
        } else if (location_.equals(Constants.loc_shop)) {
            imageView.setImageResource(R.drawable.q11);
        }

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
        clearAllAnswers();
        TextView a = findViewById(R.id.txtA);
        answer_ = (String) a.getText();
        View view1 = findViewById(R.id.linearColumna);
        view1.setBackgroundResource(R.drawable.rectangle_bg_gray_300);
    }

    // activity_task
    public void onClickSecond(View view) {
        clearAllAnswers();
        TextView a = findViewById(R.id.txtB);
        answer_ = (String) a.getText();
        View view1 = findViewById(R.id.linearRowb);
        view1.setBackgroundResource(R.drawable.rectangle_bg_gray_300);
    }

    // activity_task
    public void onClickThird(View view) {
        clearAllAnswers();
        TextView a = findViewById(R.id.txtC);
        answer_ = (String) a.getText();
        View view1 = findViewById(R.id.linearColumnc);
        view1.setBackgroundResource(R.drawable.rectangle_bg_gray_300);
    }

    private void clearAllAnswers() {
        View view = findViewById(R.id.linearColumna);
        view.setBackgroundResource(R.drawable.rectangle_bg_white_a700_cc);
        view = findViewById(R.id.linearRowb);
        view.setBackgroundResource(R.drawable.rectangle_bg_white_a700_cc);
        view = findViewById(R.id.linearColumnc);
        view.setBackgroundResource(R.drawable.rectangle_bg_white_a700_cc_radius_bl_25_br_25);
    }

    public void onClickAnswer(View view) throws JsonProcessingException {
        if (answer_.equals(question_.right_answer)) {
            QuestManager.addCoins(buffer_);
            QuestManager.updateCoins(this);
            buffer_ = 0;
            updateLocationStatuses(true);
            setContentView(R.layout.activity_right_answer);
            TextView textView = findViewById(R.id.txtFiveHundredOne);
            textView.setText("Твой счет: " + QuestManager.getCoins());
            QuestManager.updateCoins(this);
            QuestManager.addQuestionResult(question_.question_id, true);
        } else {
            buffer_ = 0;
            updateLocationStatuses(false);
            setContentView(R.layout.activity_wrong_answer);
            TextView textView = findViewById(R.id.txtFiveHundredOne);
            textView.setText("Твой счет: " + QuestManager.getCoins());
            QuestManager.updateCoins(this);
            QuestManager.addQuestionResult(question_.question_id, false);
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
        } else if (location_.equals(Constants.loc_bankomat)) {
            QuestManager.bank_status = result;
        } else if (location_.equals(Constants.loc_fin_org)) {
            QuestManager.fin_org_status = result;
        } else {
            throw new IllegalArgumentException("bad location");
        }
    }

    private void setTimerClock(int new_time) {
        if (new_time == 0) {
            setContentView(R.layout.activity_timeout);
            TextView textView = findViewById(R.id.txtThreeHundredOne);
            textView.setText("Твой счет: " + QuestManager.getCoins());
            QuestManager.subject.onComplete();
            return;
        }
        TextView timer = findViewById(R.id.txtTime);
        String minutes = Integer.toString(new_time / 60);
        String seconds = Integer.toString(new_time % 60);
        timer.setText(minutes + ":" + seconds);
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