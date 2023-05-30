package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_token);

        EditText editText = findViewById(R.id.tokenText);
        editText.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(5), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

    public void onEnterToken(View view) {
        EditText editText = findViewById(R.id.tokenText);
        String token = editText.getText().toString();

        response_finished = false;
        updateStudentInfoByToken(token);
        while (!response_finished) ;
        if (!is_response_success) return;

        if (Constants.in_use) {
            Intent intent = new Intent(this, com.example.test.MainActivity.class);
            startActivity(intent);
        } else {
            beginRegistration();
        }
    }

    private void beginRegistration() {
        setContentView(R.layout.activity_registration);

        // TODO: Сделать что-то с фильтром первой буквы
//
//        EditText editText = findViewById(R.id.enterFirstName);
//        EditText editText1 = findViewById(R.id.enterSecondName);
//
//        editText.setFilters(new InputFilter[]{new InputFilter() {
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                boolean keepOriginal = true;
//                StringBuilder sb = new StringBuilder(end - start);
//                String s = "Тест";
//                boolean cyrillic = s.chars()
//                        .mapToObj(Character.UnicodeBlock::of)
//                        .anyMatch(b -> b.equals(Character.UnicodeBlock.CYRILLIC));
//                for (int i = start; i < end; i++) {
//                    if(source.charAt(i) != Character.UnicodeBlock.CYRILLIC)
//                    char c = source.charAt(i);
//                    if (i == 0) {
//                        char a = source.charAt(i);
//                        a = Character.toUpperCase(a);
//                        System.out.println(a);
//                        sb.append(a);
//                    } else {
//                        keepOriginal = true;
//                        sb.append(source.charAt(i));
//                    }
//                }
//                return null;
//                else return sb;
//            }
//        }});

//        TextView city = findViewById(R.id.txtCity);
//        TextView school = findViewById(R.id.txtSchool);
//        TextView grade = findViewById(R.id.txtClassGrade);
//        TextView token = findViewById(R.id.txtCity1);

//        city.setText(Constants.city);
//        school.setText(Constants.school);
//        grade.setText(Constants.class_name);
//        token.setText(Constants.token);
    }

    public void onEnterFullName(View view) throws JsonProcessingException {
        EditText firstName = findViewById(R.id.enterFirstName);
        String firstNameStr = firstName.getText().toString();
        EditText secondName = findViewById(R.id.enterSecondName);
        String secondNameStr = secondName.getText().toString();

        response_finished = false;
        registerCharacter(firstNameStr, secondNameStr);
        while (!response_finished) ;
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
                    Map<String, Object> response_map = Constants.object_mapper.readValue(responseBody.string(), new TypeReference<>() {
                    });
                    Constants.city = response_map.get("city").toString();
                    Constants.school = response_map.get("school").toString();
                    Constants.class_name = response_map.get("class_name").toString();
                    Constants.token = token;
                    Constants.in_use = (boolean) response_map.get("in_use");
                    Constants.can_play = (boolean) response_map.get("can_play");
                    System.out.println("!!!" + response_map.get("can_play"));
                    System.out.println(Constants.can_play);
                    response_finished = true;
                } catch (Exception e) {
                    is_response_success = false;
                    System.out.println("Ошибка 2 " + e);
                    response_finished = true;
                }
            }
        });
    }

    private void registerCharacter(String first_name, String second_name) throws
            JsonProcessingException {
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
                    Map<String, Object> response_map = Constants.object_mapper.readValue(responseBody.string(), new TypeReference<>() {
                    });
                    Constants.first_name = first_name;
                    Constants.second_name = second_name;
                    response_finished = true;
                } catch (Exception e) {
                    is_response_success = false;
                    System.out.println("Ошибка 2 " + e);
                    response_finished = true;
                }
            }
        });
    }

    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}