package com.idk.thegrind;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CorrectAnswerDialog extends Dialog {
    public CorrectAnswerDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.congrats_dialog);
    }

    public void showDialog(long time, boolean forLeaderboard){
        Button returnQuestions = findViewById(R.id.return_questions);
        TextView timeTaken = findViewById(R.id.time_result);
        TextView leaderboardMsg = findViewById(R.id.leaderboard_submit_msg);

        timeTaken.setText(Conversions.convertTimeToString(time));

        if(forLeaderboard){
            leaderboardMsg.setText(R.string.leaderboard_submit);
            leaderboardMsg.setTextColor(Color.parseColor("#4b9668"));
        } else {
            leaderboardMsg.setText(R.string.leaderboard_submit_error);
            leaderboardMsg.setTextColor(Color.parseColor("#964e4b"));
        }

        returnQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        show();
    }
}
