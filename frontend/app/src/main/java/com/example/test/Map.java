package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_map);
    }

    public void schoolToTask(View view) {
        Intent intent = new Intent(this, Task.class);
        intent.putExtra("location", "school");
        startActivity(intent);
    }

    public void bankToTask(View view) {
        Intent intent = new Intent(this, Task.class);
        intent.putExtra("location", "bank");
        startActivity(intent);
    }

    public void mallToTask(View view) {
        Intent intent = new Intent(this, Task.class);
        intent.putExtra("location", "mall");
        startActivity(intent);
    }

    public void marketToTask(View view) {
        Intent intent = new Intent(this, Task.class);
        intent.putExtra("location", "shop");
        startActivity(intent);
    }

    public void atmToTask(View view) {
        Intent intent = new Intent(this, Task.class);
        intent.putExtra("location", "fin_org");
        startActivity(intent);
    }
}