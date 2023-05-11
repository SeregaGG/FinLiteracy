package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

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