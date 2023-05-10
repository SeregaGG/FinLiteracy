package com.example.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Rating extends AppCompatActivity {
    List<RatingItem> ratingItems = new ArrayList<RatingItem>();
    boolean temp_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rating);

        updateRatingResponses();
        System.out.println("\n\n\n");
        System.out.println(ratingItems.size());
        System.out.println("\n\n\n");

        while (!temp_flag){}

        RecyclerView recyclerView = findViewById(R.id.recyclerRating);
        RatingAdapter ratingAdapter = new RatingAdapter(this, ratingItems);
        recyclerView.setAdapter(ratingAdapter);
    }

    private void updateRatingResponses() {
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
                    System.out.println("\n\n\n");
                    System.out.println(responseBody.string());
                    System.out.println("\n\n\n");
                    String item = """
                            [
                            {
                            "first_name":"string",
                            "second_name":"string",
                            "score":1200
                            }
                            ]
                            """;
                    ratingItems = Arrays.asList(Constants.object_mapper.readValue(item, RatingItem[].class));
                    temp_flag = true;
                } catch (Exception e) {
                    System.out.println("Ошибка" + e);
                }
            }
        });
    }

    private void updateRating(){

    }
}