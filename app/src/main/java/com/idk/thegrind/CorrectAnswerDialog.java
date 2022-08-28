package com.idk.thegrind;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CorrectAnswerDialog extends Dialog {
    SharedPreferences prefs;

    public CorrectAnswerDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.congrats_dialog);
        prefs = context.getSharedPreferences("grind_prefs", Context.MODE_PRIVATE);
    }

    public void showDialog(long time, boolean forLeaderboard){
        Button returnQuestions = findViewById(R.id.return_questions);
        TextView timeTaken = findViewById(R.id.time_result);
        TextView leaderboardMsg = findViewById(R.id.leaderboard_submit_msg);
        EditText nameEntry = findViewById(R.id.final_name);
        TextView errorMsg = findViewById(R.id.error_message);

        timeTaken.setText(Conversions.convertTimeToString(time));
        errorMsg.setVisibility(View.GONE);

        if(forLeaderboard){
            leaderboardMsg.setText(R.string.leaderboard_submit);
            leaderboardMsg.setTextColor(Color.parseColor("#4b9668"));
            nameEntry.setVisibility(View.VISIBLE);
            nameEntry.setText(prefs.getString("pref_name", ""));
        } else {
            leaderboardMsg.setText(R.string.leaderboard_submit_error);
            leaderboardMsg.setTextColor(Color.parseColor("#964e4b"));
            nameEntry.setVisibility(View.GONE);
        }

        returnQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameEntry.getText().toString().equals("")){
                    if(forLeaderboard){
                        Sounds.play(getContext(), R.raw.error);
                        errorMsg.setVisibility(View.VISIBLE);
                    }
                } else {
                    Sounds.play(getContext(), R.raw.click);
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("pref_name", nameEntry.getText().toString());
                    dismiss();
                }
            }
        });

        show();
    }
}
