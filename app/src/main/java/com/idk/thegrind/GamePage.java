package com.idk.thegrind;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GamePage extends AppCompatActivity {
    QuestionAdapter questionAdapter;

    SharedPreferences prefs;
    ImageButton leaderboardButton;
    ImageButton settingsButton;
    ImageButton questionsButton;
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

        /* SharedPreferences.Editor edit = prefs.edit();
        edit.putString("game_status", Conversions.convertArrayToString(GameData.eligible)); // UNCOMMENT THIS TO RESET ELEGIBILITY TO DEFAULT STATE
        edit.apply(); */

        /* System.out.println("asdf eligible " + Arrays.toString(GameData.eligible));
        System.out.println("asdf set " + convertArrayToSet(GameData.eligible));
        System.out.println("asdf prefs " + Arrays.toString(convertSetToArray(prefs.getStringSet("game_status", convertArrayToSet(GameData.eligible))))); */


        GameData.eligible = Conversions.convertArrayStringToArrayInt(prefs.getString("game_status", Conversions.convertArrayToString(GameData.eligible)));

        if(prefs.getBoolean("isSFX", true)){
            sfxSettings.setChecked(true);
        }

        hideLeaderboard();
        hideSettings();
        showQuestions();
        questionsButton.setImageResource(R.drawable.questionsblue2);

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
                    questionsButton.setImageResource(R.drawable.questionsblue2);
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
                    leaderboardButton.setImageResource(R.drawable.leaderboardblue2);
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
                    settingsButton.setImageResource(R.drawable.settingsblue2);
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
                instructDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                instructDialog.showDialog();
            }
        });

        profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileDialog profileDialog = new ProfileDialog(GamePage.this);
                profileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                profileDialog.showDialog();
            }
        });
    }

    public void hideQuestions(){
        questionsQuestions.setVisibility(View.GONE);
        questionsButton.setImageResource(R.drawable.questions2);
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
        settingsButton.setImageResource(R.drawable.settings2);
    }

    public void showSettings(){
        instructionsSettings.setVisibility(View.VISIBLE);
        profileSettings.setVisibility(View.VISIBLE);
        sfxSettings.setVisibility(View.VISIBLE);
        subjectSettings.setVisibility(View.VISIBLE);
        generalSettings.setVisibility(View.VISIBLE);
    }

    public void hideLeaderboard(){
        leaderboardButton.setImageResource(R.drawable.leaderboard2);
    }

    public void showLeaderboard(){

    }
}
