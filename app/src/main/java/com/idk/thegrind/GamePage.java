package com.idk.thegrind;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class GamePage extends AppCompatActivity {
    RecyclerView questions;
    Button leaderboardButton;
    Button settingsButton;
    Button questionsButton;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_page);

        getSupportActionBar().hide();

        leaderboardButton = findViewById(R.id.leaderboard_button);
        settingsButton = findViewById(R.id.settings_button);
        questionsButton = findViewById(R.id.question_button);
        questions = findViewById(R.id.questions);
        title = findViewById(R.id.title);

        questionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameData.currentPage != 1) {
                    GameData.currentPage = 1;
                    title.setText(R.string.daily_qs);
                    questions.setVisibility(View.VISIBLE);
                }
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameData.currentPage != 2) {
                    GameData.currentPage = 2;
                    title.setText(R.string.overall_lb);
                    questions.setVisibility(View.GONE);
                }
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameData.currentPage != 3) {
                    GameData.currentPage = 3;
                    title.setText(R.string.settings);
                    questions.setVisibility(View.GONE);
                }
            }
        });
    }
}
