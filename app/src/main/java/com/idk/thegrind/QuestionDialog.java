package com.idk.thegrind;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionDialog extends Dialog {
    SharedPreferences prefs;

    public QuestionDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.question_dialog);
        prefs = context.getSharedPreferences("grind_prefs", Context.MODE_PRIVATE);
    }

    public void showDialog(int position){
        Button submit = findViewById(R.id.submit);
        TextView eligibility = findViewById(R.id.eligibility);
        TextView wrongAnswer = findViewById(R.id.wrong_answer);
        TextView subjectName = findViewById(R.id.subject_name);
        TextView questionText = findViewById(R.id.question_text);
        EditText answerBox = findViewById(R.id.answer_box);

        wrongAnswer.setVisibility(View.GONE);
        answerBox.setText("");
        subjectName.setText(GameData.subjects[position].toUpperCase(Locale.ROOT));
        questionText.setText(GameData.subjectQuestions[position]);

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

                if(GameData.eligible[position] == 0){ // leaderboard submit
                    if(playerAnswer.equals(GameData.subjectAnswers[position])){
                        GameData.eligible[position] = 2;
                        // congratulations message/popup asking to submit name + their time/score
                        dismiss();
                    } else {
                        GameData.eligible[position] = 1;
                        eligibility.setVisibility(View.VISIBLE);
                        answerBox.setText("");
                        wrongAnswer.setVisibility(View.VISIBLE);
                    }
                } else {
                    if(playerAnswer.equals(GameData.subjectAnswers[position])){
                        GameData.eligible[position] = 2;
                        // congratulations message but notif that they don't get leaderboard, no name entry
                        dismiss();
                    } else {
                        answerBox.setText("");
                        wrongAnswer.setVisibility(View.VISIBLE);
                    }
                }
                Set<String> gameStatus = convertArrayToSet(GameData.eligible);

                SharedPreferences.Editor edit = prefs.edit();
                edit.putStringSet("game_status", gameStatus);
                edit.apply();
            }
        });

        show();
    }

    public static <String> Set<String> convertArrayToSet(int array[]) {
        Set<String> set = new HashSet<>();

        for (int i = 0; i<array.length; i++){
            int newNum = array[i]+i*3; // CHANGE IF MORE THAN 3 STATES
            String ph = (String) java.lang.String.valueOf(newNum);
            set.add(ph);
        }

        return set;
    }
}
