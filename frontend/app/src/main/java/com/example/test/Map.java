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
        QuestManager.updateCoins(this);
    }

    public void schoolToTask(View view) {
        toTask("school");
    }

    public void bankToTask(View view) {
        toTask("bank");
    }

    public void mallToTask(View view) {
        toTask("mall");
    }

    public void marketToTask(View view) {
        toTask("shop");
    }

    public void atmToTask(View view) {
        toTask("fin_org");
    }

    private void toTask(String location) {
        Intent intent = new Intent(this, Task.class);
        intent.putExtra("location", location);
        startActivity(intent);
    }

    public void clickHelpButton(View view){
        setContentView(R.layout.activity_rules_tab);
        QuestManager.updateCoins(this);
    }

    public void returnFromHelp(View view){
        setContentView(R.layout.activity_map);
        QuestManager.updateCoins(this);
    }
}