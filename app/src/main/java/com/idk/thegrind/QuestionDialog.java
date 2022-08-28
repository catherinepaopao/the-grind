package com.idk.thegrind;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    CorrectAnswerDialog correctDialog;

    public QuestionDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.question_dialog);
        prefs = context.getSharedPreferences("grind_prefs", Context.MODE_PRIVATE);
        correctDialog = new CorrectAnswerDialog(context);
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
        answerBox.clearFocus();
        subjectName.setText(GameData.subjects[position].toUpperCase(Locale.ROOT));

        long currTime = System.currentTimeMillis();

        /* edit.putString("starting_times", Conversions.convertArrayToStringTime(GameData.startingTimesDefault)); // RESET STARTING TIMES
        edit.apply(); */

        GameData.startingTimes = Conversions.convertArrayStringToArray(prefs.getString("starting_times", Conversions.convertArrayToStringTime(GameData.startingTimes)));
        String[] placeHolder = Conversions.convertArrayStringToArray(prefs.getString("total_times", Conversions.convertArrayToStringTime(GameData.totalTimes)));

        for(int i = 0; i<placeHolder.length; i++){
            GameData.totalTimes[i] = Long.parseLong(placeHolder[i]);
        }

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
                Sounds.play(getContext(), R.raw.click);
                String playerAnswer = answerBox.getText().toString().toLowerCase(Locale.ROOT);
                playerAnswer = playerAnswer.replace(" ", "");
                correctDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                if(playerAnswer.length() == 0){
                    Sounds.play(getContext(), R.raw.error);
                    wrongAnswer.setVisibility(View.VISIBLE);
                    wrongAnswer.setText(R.string.no_answer);
                } else if(playerAnswer.length() > 1 || !playerAnswer.matches("[a-zA-Z]+")) {
                    Sounds.play(getContext(), R.raw.error);
                    wrongAnswer.setVisibility(View.VISIBLE);
                    wrongAnswer.setText(R.string.bad_answer);
                } else {
                    if(playerAnswer.equals(GameData.subjectAnswers[position])){
                        Sounds.play(getContext(), R.raw.correct);
                        long result = System.currentTimeMillis()-Long.parseLong(GameData.startingTimes[position]);
                        if(GameData.eligible[position] == 0){ // leaderboard submit
                            Sounds.play(getContext(), R.raw.congrats);
                            GameData.totalTimes[position] = result;
                            correctDialog.showDialog(result, true);
                        } else {
                            correctDialog.showDialog(result, false);
                        }

                        GameData.eligible[position] = 2;
                        updateRecycler.updateDisplay(position, buttonDisplay);
                        dismiss();
                    } else {
                        Sounds.play(getContext(), R.raw.wrong);
                        GameData.eligible[position] = 1;
                        eligibility.setVisibility(View.VISIBLE);
                        answerBox.setText("");
                        wrongAnswer.setText(R.string.wrong_answer);
                        wrongAnswer.setVisibility(View.VISIBLE);
                    }

                    String gameStatus = Conversions.convertArrayToString(GameData.eligible);
                    String totalTime = Conversions.convertArrayToStringTime(GameData.totalTimes);
                    edit.putString("total_times", totalTime);
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
