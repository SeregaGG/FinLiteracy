package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Registration extends AppCompatActivity {
    boolean response_finished = false;
    boolean is_response_success = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_token);
    }

    public void onEnterToken(View view) {
        EditText editText = findViewById(R.id.tokenText);
        String token = editText.getText().toString();

        response_finished = false;
        updateStudentInfoByToken(token);
        while (!response_finished) {
        }
        if (!is_response_success) return;

        beginRegistration();
    }

    private void beginRegistration() {
        setContentView(R.layout.activity_registration);

        TextView city = findViewById(R.id.txtCity);
        TextView school = findViewById(R.id.txtSchool);
        TextView grade = findViewById(R.id.txtClassGrade);

        city.setText(Constants.city);
        school.setText(Constants.school);
        grade.setText(Constants.class_name);
    }

    public void onEnterFullName(View view) throws JsonProcessingException {
        EditText firstName = findViewById(R.id.enterFirstName);
        String firstNameStr = firstName.getText().toString();
        EditText secondName = findViewById(R.id.enterSecondName);
        String secondNameStr = secondName.getText().toString();

        response_finished = false;
        registerCharacter(firstNameStr, secondNameStr);
        while (!response_finished) {
        }
        if (!is_response_success) return;

        Intent intent = new Intent(this, com.example.test.MainActivity.class);
        startActivity(intent);
    }

    private void updateStudentInfoByToken(String token) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.server_url + "/token/info").newBuilder();
        urlBuilder.addQueryParameter("token", token);
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();

        System.out.println(request.toString());
        is_response_success = true;
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
                    Constants.city = response_map.get("city").toString();
                    Constants.school = response_map.get("school").toString();
                    Constants.class_name = response_map.get("class_name").toString();
                    Constants.token = token;
                    response_finished = true;
                } catch (Exception e) {
                    response_finished = true;
                    is_response_success = false;
                    System.out.println("Ошибка 2 " + e);
                }
            }
        });
    }

    private void registerCharacter(String first_name, String second_name) throws JsonProcessingException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        Map<String, String> json_template = Map.of("first_name", first_name, "second_name", second_name);
        String json = Constants.object_mapper.writeValueAsString(json_template);
        RequestBody body = RequestBody.create(json, JSON);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.server_url + "/auth").newBuilder();
        urlBuilder.addQueryParameter("token", Constants.token);
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .post(body)
                .build();

        System.out.println(request.toString());
        is_response_success = true;
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
                    Constants.first_name = first_name;
                    Constants.second_name = second_name;
                    response_finished = true;
                } catch (Exception e) {
                    response_finished = true;
                    is_response_success = false;
                    System.out.println("Ошибка 2 " + e);
                }
            }
        });
    }
}