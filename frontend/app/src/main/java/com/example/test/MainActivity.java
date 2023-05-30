package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

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
        System.out.println(Constants.can_play);
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
}