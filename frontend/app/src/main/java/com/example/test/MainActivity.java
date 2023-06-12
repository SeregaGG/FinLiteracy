package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

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

        setContentView(R.layout.activity_start);
    }

    public void toMap(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // код, который нужно выполнить каждую секунду
                // например, вывод на экран сообщения
                QuestManager.tickTime();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000); // выполнение метода через 1 секунду
    }

    public void toDialogBegin(View view) {
        System.out.println("\n\n\n" + Constants.can_play + "\n\n\n");
        if (!Constants.can_play) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы уже прошли игру", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            setContentView(R.layout.activity_dialog_begin);
        }
    }

    public void toDialogEnd(View view) {
        setContentView(R.layout.activity_dialog_end);
    }

    public void toStartFromDialog(View view) {
        setContentView(R.layout.activity_start);
    }

    public void toRating(View view) {
        Intent intent = new Intent(this, Rating.class);
        startActivity(intent);
    }

    public void toHelp(View view) {
        setContentView(R.layout.activity_rules_tab);
    }

    public void returnFromHelp(View view) {
        setContentView(R.layout.activity_start);
    }

    public void getCertificate(View view) throws InterruptedException {
        is_finished = false;
        getCertificateFromServer();
        while (!is_finished) {
            Thread.sleep(10);
            System.out.println("getCertificateFromServer");
        }
        if (!is_success) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Ошибка в получении сертификата или его нет", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        setContentView(R.layout.certificate);
        ScalingImage imageView = (ScalingImage) findViewById(R.id.cert);
        imageView.setRotation(90);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageBitmap(bitmap);
    }

    private void getCertificateFromServer() {
        MediaType JSON = MediaType.get("image/png");
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.server_url + "/certificate").newBuilder();
        urlBuilder.addQueryParameter("token", Constants.token);
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
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
                try { // TODO: Проверить на правильность.
                    if (!response.isSuccessful()) {
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    }

                    InputStream inputStream = response.body().byteStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
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

    private boolean is_finished = true;
    private boolean is_success = true;

    Bitmap bitmap;
}
