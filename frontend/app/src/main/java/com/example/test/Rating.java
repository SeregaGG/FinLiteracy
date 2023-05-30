package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.type.TypeReference;

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

    @Override
    public void onBackPressed() {
        // do nothing
    }

    @Override
    protected void onResume() {
        super.onResume();
        //for new api versions.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_rating);

        updateRating();
        updateIndRating();

        while (!temp_flag2) {
        }
        while (!temp_flag) {
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
                try (ResponseBody responseBody = response.peekBody(2048)) { // TODO: Проверить на правильность.
                    if (!response.isSuccessful()) {
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    }
                    ratingItems = Arrays.asList(Constants.object_mapper.readValue(responseBody.string(), RatingItem[].class));
                    temp_flag = true;
                } catch (Exception e) {
                    System.out.println("Ошибка 1 " + e);
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
                    temp_flag2 = true;
                } catch (Exception e) {
                    System.out.println("Ошибка 2 " + e);
                    score = "0";
                    temp_flag2 = true;
                }
            }
        });
    }
}