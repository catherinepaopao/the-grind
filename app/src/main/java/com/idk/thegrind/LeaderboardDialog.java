package com.idk.thegrind;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class LeaderboardDialog extends Dialog {
    public LeaderboardDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.question_leaderboard_dialog);
    }

    public void showDialog(int position){
        Button close = findViewById(R.id.close_leaderboard);
        TextView subjLeaderboard = findViewById(R.id.subj_leaderboard);

        subjLeaderboard.setText(GameData.subjects[position] + " Leaderboard");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sounds.play(getContext(), R.raw.click);
                dismiss();
            }
        });

        show();
    }
}
