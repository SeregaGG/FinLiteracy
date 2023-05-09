package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<RatingItem> ratingItems = new ArrayList<RatingItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_rating);
        for (int i = 0; i < 300; i++) {
            ratingItems.add(new RatingItem(1, "Daniil", 1000));
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerRating);
        RatingAdapter ratingAdapter = new RatingAdapter(this, ratingItems);
        recyclerView.setAdapter(ratingAdapter);
//        setContentView(R.layout.activity_start);
    }

    public void toMap(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
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