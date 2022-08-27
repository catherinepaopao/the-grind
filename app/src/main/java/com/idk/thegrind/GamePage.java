package com.idk.thegrind;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GamePage extends AppCompatActivity {
    QuestionAdapter questionAdapter;

    SharedPreferences prefs;
    Button leaderboardButton;
    Button settingsButton;
    Button questionsButton;
    TextView title;

    RecyclerView questionsQuestions;
    Button instructionsSettings;
    Button profileSettings;
    Button subjectSettings;
    Switch sfxSettings;
    TextView generalSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = this.getSharedPreferences("grind_prefs", Context.MODE_PRIVATE);
        setContentView(R.layout.game_page);

        getSupportActionBar().hide();

        leaderboardButton = findViewById(R.id.leaderboard_button);
        settingsButton = findViewById(R.id.settings_button);
        questionsButton = findViewById(R.id.question_button);
        questionsQuestions = findViewById(R.id.questions);
        title = findViewById(R.id.title);
        instructionsSettings = findViewById(R.id.instructions_settings);
        profileSettings = findViewById(R.id.profile_button);
        subjectSettings = findViewById(R.id.subjects_button);
        sfxSettings = findViewById(R.id.sfx_switch);
        generalSettings = findViewById(R.id.general);

        if(prefs.getBoolean("isSFX", true)){
            sfxSettings.setChecked(true);
        }

        hideLeaderboard();
        hideSettings();
        showQuestions();

        QuestionDialog questionDialog = new QuestionDialog(this);
        questionAdapter = new QuestionAdapter(GameData.subjects, questionDialog);

        questionsQuestions.setLayoutManager(new GridLayoutManager(this, 2));
        questionsQuestions.setAdapter(questionAdapter);
        questionAdapter.notifyDataSetChanged();

        questionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameData.currentPage != 1) {
                    GameData.currentPage = 1;
                    title.setText(R.string.daily_qs);
                    showQuestions();
                    hideSettings();
                    hideLeaderboard();
                }
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameData.currentPage != 2) {
                    GameData.currentPage = 2;
                    title.setText(R.string.overall_lb);
                    hideQuestions();
                    hideSettings();
                    showLeaderboard();
                }
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameData.currentPage != 3) {
                    GameData.currentPage = 3;
                    title.setText(R.string.settings);
                    hideQuestions();
                    showSettings();
                    hideLeaderboard();
                }
            }
        });

        sfxSettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor edit = prefs.edit();

                if(!isChecked){
                    edit.putBoolean("isSFX", false);
                } else {
                    edit.putBoolean("isSFX", true);
                }
                edit.apply();
            }
        });

        instructionsSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstructionsDialog instructDialog = new InstructionsDialog(GamePage.this);
                instructDialog.showDialog();
            }
        });

        profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileDialog profileDialog = new ProfileDialog(GamePage.this);
                profileDialog.showDialog();
            }
        });
    }

    public void hideQuestions(){
        questionsQuestions.setVisibility(View.GONE);
    }

    public void showQuestions(){
        questionsQuestions.setVisibility(View.VISIBLE);
    }

    public void hideSettings(){
        instructionsSettings.setVisibility(View.GONE);
        profileSettings.setVisibility(View.GONE);
        sfxSettings.setVisibility(View.GONE);
        subjectSettings.setVisibility(View.GONE);
        generalSettings.setVisibility(View.GONE);
    }

    public void showSettings(){
        instructionsSettings.setVisibility(View.VISIBLE);
        profileSettings.setVisibility(View.VISIBLE);
        sfxSettings.setVisibility(View.VISIBLE);
        subjectSettings.setVisibility(View.VISIBLE);
        generalSettings.setVisibility(View.VISIBLE);
    }

    public void hideLeaderboard(){

    }

    public void showLeaderboard(){

    }
}
