package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
                System.out.println(QuestManager.getTime());
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000); // выполнение метода через 1 секунду
    }

    public void toDialogBegin(View view) {
        setContentView(R.layout.activity_dialog_begin);
    }

    public void toDialogEnd(View view) {
        setContentView(R.layout.activity_dialog_end);
    }

    public void toHelp(View view) {
        setContentView(R.layout.activity_rules_tab);
    }

    public void returnFromHelp(View view) {
        setContentView(R.layout.activity_start);
    }
}