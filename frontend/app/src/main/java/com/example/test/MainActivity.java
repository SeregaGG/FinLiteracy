package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_rules_full_dialog);
    }

    // Обработка нажатия кнопки
    public void toMap(View view) {
        setContentView(R.layout.activity_map);
    }

    public void toTaskChoosing(View view) {
        setContentView(R.layout.activity_task_choosing);
    }

    public void toTask(View view) {
        setContentView(R.layout.activity_task);
    }

    public void toRightAnswer(View view) {
        setContentView(R.layout.activity_right_answer);
    }

    public void toFinish(View view) {
        setContentView(R.layout.activity_finish);
    }
}