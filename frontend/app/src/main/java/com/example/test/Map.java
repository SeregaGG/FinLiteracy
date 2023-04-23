package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        System.out.println("\n\n\n in onCreate \n\n\n");
        setLocationActualStatuses();
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

    public void clickHelpButton(View view) {
        setContentView(R.layout.activity_rules_tab);
        QuestManager.updateCoins(this);
    }

    public void returnFromHelp(View view) {
        setContentView(R.layout.activity_map);
        setLocationActualStatuses();
        QuestManager.updateCoins(this);
    }

    private void setLocationActualStatuses() {
        System.out.println("\n\n\n in setLocationActualStatuses " + QuestManager.bank_status + "\n\n\n");

        Button button;

        button = findViewById(R.id.btn_bank);
        if (QuestManager.bank_status == QuestManager.locationStatuses.WRONG_ANSWER) {
            button.setBackgroundResource(R.drawable.map_wrong_answer);
            button.setEnabled(false);
        } else if (QuestManager.bank_status == QuestManager.locationStatuses.CORRECT_ANSWER) {
            button.setBackgroundResource(R.drawable.map_correct_answer);
            button.setEnabled(false);
        }

        button = findViewById(R.id.btn_shop);
        if (QuestManager.shop_status == QuestManager.locationStatuses.WRONG_ANSWER) {
            button.setBackgroundResource(R.drawable.map_wrong_answer);
            button.setEnabled(false);

        } else if (QuestManager.shop_status == QuestManager.locationStatuses.CORRECT_ANSWER) {
            button.setBackgroundResource(R.drawable.map_correct_answer);
            button.setEnabled(false);
        }

        button = findViewById(R.id.btn_mall);
        if (QuestManager.mall_status == QuestManager.locationStatuses.WRONG_ANSWER) {
            button.setBackgroundResource(R.drawable.map_wrong_answer);
            button.setEnabled(false);
        } else if (QuestManager.mall_status == QuestManager.locationStatuses.CORRECT_ANSWER) {
            button.setBackgroundResource(R.drawable.map_correct_answer);
            button.setEnabled(false);
        }

        button = findViewById(R.id.btn_school);
        if (QuestManager.school_status == QuestManager.locationStatuses.WRONG_ANSWER) {
            button.setBackgroundResource(R.drawable.map_wrong_answer);
            button.setEnabled(false);
        } else if (QuestManager.school_status == QuestManager.locationStatuses.CORRECT_ANSWER) {
            button.setBackgroundResource(R.drawable.map_correct_answer);
            button.setEnabled(false);
        }

        button = findViewById(R.id.btn_fin_org);
        if (QuestManager.fin_org_status == QuestManager.locationStatuses.WRONG_ANSWER) {
            button.setBackgroundResource(R.drawable.map_wrong_answer);
            button.setEnabled(false);
        } else if (QuestManager.fin_org_status == QuestManager.locationStatuses.CORRECT_ANSWER) {
            button.setBackgroundResource(R.drawable.map_correct_answer);
            button.setEnabled(false);
        }
    }

}