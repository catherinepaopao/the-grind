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
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class GamePage extends AppCompatActivity implements PlaySound{
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
    ImageView leaderboardMock;

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
        leaderboardMock = findViewById(R.id.lb_mock);

        /* SharedPreferences.Editor edit = prefs.edit();
        edit.putString("game_status", Conversions.convertArrayToString(GameData.eligible)); // UNCOMMENT THIS TO RESET ELEGIBILITY TO DEFAULT STATE
        edit.apply(); */

        /* System.out.println("asdf eligible " + Arrays.toString(GameData.eligible));
        System.out.println("asdf set " + convertArrayToSet(GameData.eligible));
        System.out.println("asdf prefs " + Arrays.toString(convertSetToArray(prefs.getStringSet("game_status", convertArrayToSet(GameData.eligible))))); */

        Conversions.checkDate(prefs);
        GameData.eligible = Conversions.convertArrayStringToArrayInt(prefs.getString("game_status", Conversions.convertArrayToString(GameData.eligible)));

        if(prefs.getBoolean("isSFX", true)){
            sfxSettings.setChecked(true);
        }

        hideLeaderboard();
        hideSettings();
        showQuestions();
        questionsButton.setImageResource(R.drawable.questionsblue2);

        QuestionDialog questionDialog = new QuestionDialog(this);
        LeaderboardDialog leaderboardDialog = new LeaderboardDialog(this);
        questionAdapter = new QuestionAdapter(GameData.subjects, questionDialog, leaderboardDialog, prefs, this);

        questionsQuestions.setLayoutManager(new GridLayoutManager(this, 2));
        questionsQuestions.setAdapter(questionAdapter);
        questionAdapter.notifyDataSetChanged();

        questionDialog.setUpdateRecycler(new QuestionDialog.UpdateRecycler() {
            @Override
            public void updateDisplay(int position, ImageButton button) {
                if(GameData.eligible[position] != 2){
                    button.setImageResource(GameData.subjectButtons[position]);
                } else {
                    button.setImageResource(GameData.subjectButtonsLeaderboard[position]);
                }
                questionAdapter.notifyDataSetChanged();
            }
        });

        questionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameData.currentPage != 1) {
                    Sounds.play(GamePage.this, R.raw.click);
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
                    Sounds.play(GamePage.this, R.raw.click);
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
                    Sounds.play(GamePage.this, R.raw.click);
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
                Sounds.play(GamePage.this, R.raw.click);

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
                Sounds.play(GamePage.this, R.raw.click);
                InstructionsDialog instructDialog = new InstructionsDialog(GamePage.this);
                instructDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                instructDialog.showDialog();
            }
        });

        profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sounds.play(GamePage.this, R.raw.click);
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
        leaderboardMock.setVisibility(View.GONE);
    }

    public void showLeaderboard(){
        leaderboardMock.setVisibility(View.VISIBLE);
    }

    @Override
    public void playSound(int sound) {
        Sounds.play(this, sound);
    }
}
