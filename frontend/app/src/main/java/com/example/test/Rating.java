package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.type.TypeReference;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Rating extends AppCompatActivity {
    List<RatingItem> ratingItems = new ArrayList<RatingItem>();

    String score;
    boolean temp_flag = false;
    boolean temp_flag2 = false;

    boolean is_success = false;

    @Override
    public void onBackPressed() {
        // do nothing
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        // Configure the behavior of the hidden system bars.
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        setContentView(R.layout.activity_rating);

        updateRating();

        while (!temp_flag) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("updateRating");
        }

        if(!is_success){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Ошибка в запросе updateRating", Toast.LENGTH_SHORT);
            toast.show();
        }

        is_success = false;

        updateIndRating();

        while (!temp_flag2) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("updateIndRating");
        }

        if(!is_success){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Ошибка в запросе updateIndRating", Toast.LENGTH_SHORT);
            toast.show();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerRating);
        RatingAdapter ratingAdapter = new RatingAdapter(this, ratingItems);
        recyclerView.setAdapter(ratingAdapter);

        TextView textView = findViewById(R.id.txtThirtyThree);
        textView.setText(Constants.first_name + " " + Constants.second_name);
        TextView textView2 = findViewById(R.id.txtLanguage);
        textView2.setText(score);
    }

    public void toStart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updateRating() {
        Request request = new Request.Builder()
                .url(Constants.server_url + "/results/all")
                .build();

        Constants.okHttp_client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.peekBody(12000)) { // TODO: Проверить на правильность.
                    if (!response.isSuccessful()) {
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    }
                    String str = responseBody.string();
                    String str1 = str.trim();
                    System.out.println(str1);
                    ratingItems = Arrays.asList(Constants.object_mapper.readValue(str1, RatingItem[].class));
                    is_success = true;
                    temp_flag = true;
                } catch (Exception e) {
                    System.out.println("Ошибка 1 " + e);
                    is_success = false;
                    temp_flag = true;
                }
            }
        });
    }

    private void updateIndRating() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.server_url + "/results").newBuilder();
        urlBuilder.addQueryParameter("token", Constants.token);
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();

        System.out.println(request.toString());
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
                    Map<String, String> response_map = Constants.object_mapper.readValue(responseBody.string(), new TypeReference<>() {
                    });
                    score = response_map.get("Results");
                    is_success = true;
                    temp_flag2 = true;
                } catch (Exception e) {
                    System.out.println("Ошибка 2 " + e);
                    score = "0";
                    is_success = false;
                    temp_flag2 = true;
                }
            }
        });
    }
}