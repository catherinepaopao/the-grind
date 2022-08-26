package com.idk.thegrind;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionsPage extends AppCompatActivity {
    RecyclerView questions;
    Button leaderboard;
    Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_page);

        getSupportActionBar().hide();
    }
}
