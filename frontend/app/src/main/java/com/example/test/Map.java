package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Map extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        // do nothing
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        // Configure the behavior of the hidden system bars.
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        getQuestionsFromServer();
//        QuestManager.putQuestions(getQuestionsFromServer()); // TODO: Лучше делать в MainActivity.

        QuestManager.subject.subscribe(v -> setTimerClock(v)); // TODO: Подписывается каждый раз, как создается

        setContentView(R.layout.activity_map);
        QuestManager.updateCoins(this);
        setLocationActualStatuses();
    }

    public void schoolToTask(View view) {
        toTask(Constants.loc_school);
    }

    public void bankToTask(View view) {
        toTask(Constants.loc_fin_org);
    }

    public void mallToTask(View view) {
        toTask(Constants.loc_entertainment_center);
    }

    public void marketToTask(View view) {
        toTask(Constants.loc_shop);
    }

    public void atmToTask(View view) {
        toTask(Constants.loc_bankomat);
    }

    private void toTask(String location) {
        Intent intent = new Intent(this, Task.class);
        intent.putExtra("location", location);
        startActivity(intent);
    }

    public void returnFromHelp(View view) {
        setContentView(R.layout.activity_map);
        setLocationActualStatuses();
        QuestManager.updateCoins(this);
    }

    @SuppressLint("SetTextI18n")
    public void clickFinishBtn(View view) throws InterruptedException {
        is_finished = false;
        is_success = false;
        sendResultsToServer();
        while (!is_finished) {
            Thread.sleep(10);
        }
        if (!is_success) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Ошибка в отправке результатов", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        setContentView(R.layout.activity_finish);
        TextView textView = findViewById(R.id.txtThreeHundredOne);
        textView.setText("Твой счет: " + QuestManager.getCoins());
        QuestManager.updateCoins(this);
    }

    public void toDialog(View view) {
        if (QuestManager.getCoins() <= 0) {
            setContentView(R.layout.activity_final_talk_negative);
        } else {
            setContentView(R.layout.activity_final_talk_positive);
        }
        QuestManager.updateCoins(this);
    }

    public void toRating(View view) {
        Intent intent = new Intent(this, Rating.class);
        startActivity(intent);
    }

    private void setLocationActualStatuses() {
        Button button;

        counter = 0;
        try {
            button = findViewById(R.id.btn_bankomat);
            if (QuestManager.bank_status == QuestManager.locationStatuses.WRONG_ANSWER) {
                button.setBackgroundResource(R.drawable.map_wrong_answer);
                button.setEnabled(false);
                counter++;
            } else if (QuestManager.bank_status == QuestManager.locationStatuses.CORRECT_ANSWER) {
                button.setBackgroundResource(R.drawable.map_correct_answer);
                button.setEnabled(false);
                counter++;
            }
        } catch (Exception exception) {
            System.out.println(exception);
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

        System.out.println("\n\n\n" + counter + "\n\n\n");
        if (counter == 5) {
            var btn = findViewById(R.id.btn_finish);
            btn.setVisibility(View.VISIBLE);
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

    private void sendResultsToServer() {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(QuestManager.getJsonQuestionResults(), JSON);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.server_url + "/results").newBuilder();
        urlBuilder.addQueryParameter("token", Constants.token);
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .post(body)
                .build();

        System.out.println(request.toString());
        System.out.println(QuestManager.getJsonQuestionResults());

        Constants.okHttp_client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.peekBody(2048)) { // TODO: Проверить на правильность.
                    if (!response.isSuccessful()) {
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    }
                    is_success = true;
                    is_finished = true;
                } catch (Exception e) {
                    System.out.println("Ошибка 2 " + e);
                    is_success = false;
                    is_finished = true;
                }
            }
        });
    }

    public void toHelp(View view) {
        setContentView(R.layout.activity_rules_tab);
        QuestManager.updateCoins(this);
    }

    private boolean is_finished = true;
    private boolean is_success = true;
    private int counter = 0;

}