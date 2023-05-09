package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        QuestManager.putQuestions(getQuestionsFromServer());

        setContentView(R.layout.activity_map);
        QuestManager.updateCoins(this);
        setLocationActualStatuses();
    }

    public void schoolToTask(View view) {
        toTask("school");
    }

    public void bankToTask(View view) {
        toTask("bank");
    }

    public void mallToTask(View view) {
        toTask("mall");
    }

    public void marketToTask(View view) {
        toTask("shop");
    }

    public void atmToTask(View view) {
        toTask("fin_org");
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

    private List<Question> getQuestionsFromServer() {

        Request request = new Request.Builder()
                .url("http://81.200.149.240:8000/questions")
                .build();

        List<Question> q = new ArrayList<>();
        try (Response response = Constants.okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            q = Arrays.asList(Constants.objectMapper.readValue(response.body().string(), Question[].class));
            // пример получения конкретного заголовка ответа
            // System.out.println("Server: " + response.header("Server"));
        } catch (IOException e) {
            System.out.println("Ошибка подключения: " + e);
        }

        return q;
    }

    private int counter = 0;

}