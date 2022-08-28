package com.idk.thegrind;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionDialog extends Dialog {
    SharedPreferences prefs;
    UpdateRecycler updateRecycler = null;

    public QuestionDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.question_dialog);
        prefs = context.getSharedPreferences("grind_prefs", Context.MODE_PRIVATE);
    }

    public void setUpdateRecycler(UpdateRecycler updateRecycler) {
        this.updateRecycler = updateRecycler;
    }

    public void showDialog(int position, ImageButton buttonDisplay){
        Button submit = findViewById(R.id.submit);
        TextView eligibility = findViewById(R.id.eligibility);
        TextView wrongAnswer = findViewById(R.id.wrong_answer);
        TextView subjectName = findViewById(R.id.subject_name);
        TextView questionText = findViewById(R.id.question_text);
        EditText answerBox = findViewById(R.id.answer_box);
        SharedPreferences.Editor edit = prefs.edit();

        wrongAnswer.setVisibility(View.GONE);
        answerBox.setText("");
        subjectName.setText(GameData.subjects[position].toUpperCase(Locale.ROOT));

        long currTime = System.currentTimeMillis();

        /* edit.putString("starting_times", Conversions.convertArrayToStringTime(GameData.startingTimesDefault)); // RESET STARTING TIMES
        edit.apply(); */

        GameData.startingTimes = Conversions.convertArrayStringToArray(prefs.getString("starting_times", Conversions.convertArrayToStringTime(GameData.startingTimes)));


        if(Long.parseLong(GameData.startingTimes[position]) < 0){ // if not started, set start as now
            GameData.startingTimes[position] = String.valueOf(currTime);
            edit.putString("starting_times", Conversions.convertArrayToStringTime(GameData.startingTimes));
            edit.apply();
        }

        questionText.setText(GameData.subjectQuestions[position]); // change to position + hashmap to get daily question, initialize in GameData

        if(GameData.eligible[position] == 0){
            eligibility.setVisibility(View.GONE);
        } else {
            eligibility.setVisibility(View.VISIBLE);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerAnswer = answerBox.getText().toString().toLowerCase(Locale.ROOT);
                playerAnswer = playerAnswer.replace(" ", "");

                if(playerAnswer.length() == 0){
                    wrongAnswer.setVisibility(View.VISIBLE);
                    wrongAnswer.setText(R.string.no_answer);
                } else {
                    if(GameData.eligible[position] == 0){ // leaderboard submit
                        if(playerAnswer.equals(GameData.subjectAnswers[position])){
                            GameData.eligible[position] = 2;
                            System.out.println("asdf " + Conversions.convertTimeToString(System.currentTimeMillis()-Long.parseLong(GameData.startingTimes[position])));
                            // congratulations message/popup asking to submit name + their time/score
                            // change to leaderboard image
                            updateRecycler.updateDisplay(position, buttonDisplay);
                            dismiss();
                        } else {
                            GameData.eligible[position] = 1;
                            eligibility.setVisibility(View.VISIBLE);
                            answerBox.setText("");
                            wrongAnswer.setText(R.string.wrong_answer);
                            wrongAnswer.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if(playerAnswer.equals(GameData.subjectAnswers[position])){
                            GameData.eligible[position] = 2;
                            // congratulations message but notif that they don't get leaderboard, no name entry
                            updateRecycler.updateDisplay(position, buttonDisplay);
                            dismiss();
                        } else {
                            answerBox.setText("");
                            wrongAnswer.setText(R.string.wrong_answer);
                            wrongAnswer.setVisibility(View.VISIBLE);
                        }
                    }
                    String gameStatus = Conversions.convertArrayToString(GameData.eligible);

                    edit.putString("game_status", gameStatus);
                    edit.apply();
                }
            }
        });

        show();
    }

    interface UpdateRecycler{
        void updateDisplay(int position, ImageButton button);
    }
}
