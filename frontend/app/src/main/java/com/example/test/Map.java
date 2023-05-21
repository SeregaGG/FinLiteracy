package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        getQuestionsFromServer();
//        QuestManager.putQuestions(getQuestionsFromServer()); // TODO: Лучше делать в MainActivity.

        QuestManager.subject.subscribe(v -> setTimerClock(v));

        setContentView(R.layout.activity_map);
        QuestManager.updateCoins(this);
        setLocationActualStatuses();
    }

    public void schoolToTask(View view) {
        toTask(Constants.loc_school);
    }

    public void bankToTask(View view) {
        toTask(Constants.loc_bank);
    }

    public void mallToTask(View view) {
        toTask(Constants.loc_entertainment_center);
    }

    public void marketToTask(View view) {
        toTask(Constants.loc_shop);
    }

    public void atmToTask(View view) {
        toTask(Constants.loc_fin_org);
    }

    private void toTask(String location) {
        Intent intent = new Intent(this, Task.class);
        intent.putExtra("location", location);
        startActivity(intent);
    }

    public void clickHelpButton(View view) {
        setContentView(R.layout.activity_rules_tab);
        QuestManager.updateCoins(this);
    }

    public void returnFromHelp(View view) {
        setContentView(R.layout.activity_map);
        setLocationActualStatuses();
        QuestManager.updateCoins(this);
    }

    @SuppressLint("SetTextI18n")
    public void clickFinishBtn(View view) {
        setContentView(R.layout.activity_finish);
        TextView textView = findViewById(R.id.txtThreeHundredOne);
        textView.setText("Твой счет: " + QuestManager.getCoins());
        QuestManager.updateCoins(this);
    }

    public void toRating(View view) {
        Intent intent = new Intent(this, Rating.class);
        startActivity(intent);
    }

    private void setLocationActualStatuses() {
        Button button;

        button = findViewById(R.id.btn_bank);
        if (QuestManager.bank_status == QuestManager.locationStatuses.WRONG_ANSWER) {
            button.setBackgroundResource(R.drawable.map_wrong_answer);
            button.setEnabled(false);
            counter++;
        } else if (QuestManager.bank_status == QuestManager.locationStatuses.CORRECT_ANSWER) {
            button.setBackgroundResource(R.drawable.map_correct_answer);
            button.setEnabled(false);
            counter++;
        }

        button = findViewById(R.id.btn_shop);
        if (QuestManager.shop_status == QuestManager.locationStatuses.WRONG_ANSWER) {
            button.setBackgroundResource(R.drawable.map_wrong_answer);
            button.setEnabled(false);
            counter++;
        } else if (QuestManager.shop_status == QuestManager.locationStatuses.CORRECT_ANSWER) {
            button.setBackgroundResource(R.drawable.map_correct_answer);
            button.setEnabled(false);
            counter++;
        }

        button = findViewById(R.id.btn_mall);
        if (QuestManager.mall_status == QuestManager.locationStatuses.WRONG_ANSWER) {
            button.setBackgroundResource(R.drawable.map_wrong_answer);
            button.setEnabled(false);
            counter++;
        } else if (QuestManager.mall_status == QuestManager.locationStatuses.CORRECT_ANSWER) {
            button.setBackgroundResource(R.drawable.map_correct_answer);
            button.setEnabled(false);
            counter++;
        }

        button = findViewById(R.id.btn_school);
        if (QuestManager.school_status == QuestManager.locationStatuses.WRONG_ANSWER) {
            button.setBackgroundResource(R.drawable.map_wrong_answer);
            button.setEnabled(false);
            counter++;
        } else if (QuestManager.school_status == QuestManager.locationStatuses.CORRECT_ANSWER) {
            button.setBackgroundResource(R.drawable.map_correct_answer);
            button.setEnabled(false);
            counter++;
        }

        button = findViewById(R.id.btn_fin_org);
        if (QuestManager.fin_org_status == QuestManager.locationStatuses.WRONG_ANSWER) {
            button.setBackgroundResource(R.drawable.map_wrong_answer);
            button.setEnabled(false);
            counter++;
        } else if (QuestManager.fin_org_status == QuestManager.locationStatuses.CORRECT_ANSWER) {
            button.setBackgroundResource(R.drawable.map_correct_answer);
            button.setEnabled(false);
            counter++;
        }

        if (counter == 5) {
            var btn = findViewById(R.id.btn_finish);
            btn.setVisibility(View.VISIBLE);
        }
    }

    private void setTimerClock(int new_time) {
        if(new_time == 0){
            setContentView(R.layout.activity_timeout);
            TextView textView = findViewById(R.id.txtThreeHundredOne);
            textView.setText("Твой счет: " + QuestManager.getCoins());
            QuestManager.subject.onComplete();
            return;
        }
        TextView timer = findViewById(R.id.txtTime);
        System.out.println("MAP" + new_time);
        String minutes = Integer.toString(new_time / 60);
        String seconds = Integer.toString(new_time % 60);
        timer.setText(minutes + ":" + seconds);
    }

    private void getQuestionsFromServer() {
        Request request = new Request.Builder()
                .url(Constants.server_url + "/questions")
                .build();

        Constants.okHttp_client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    }

                    QuestManager.putQuestions(Arrays.asList(Constants.object_mapper.readValue(responseBody.string(), Question[].class)));
                } catch (Exception e) {
                    System.out.println("Ошибка" + e);
                }
            }
        });
    }

    private int counter = 0;

}