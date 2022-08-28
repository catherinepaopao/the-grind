package com.idk.thegrind;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.Arrays;

public class LeaderboardDialog extends Dialog {
    SharedPreferences prefs;

    public LeaderboardDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.question_leaderboard_dialog);
        prefs = context.getSharedPreferences("grind_prefs", Context.MODE_PRIVATE);
    }

    public void showDialog(int position){
        Button close = findViewById(R.id.close_leaderboard);
        TextView subjLeaderboard = findViewById(R.id.subj_leaderboard);
        TextView lb1 = findViewById(R.id.lb1);
        TextView lb2 = findViewById(R.id.lb2);
        TextView lb3 = findViewById(R.id.lb3);
        TextView lb4 = findViewById(R.id.lb4);
        TextView lb5 = findViewById(R.id.lb5);
        TextView[] lbSpots = {lb1, lb2, lb3, lb4, lb5};
        int[][] scores;

        String[] placeHolder = Conversions.convertArrayStringToArray(prefs.getString("total_times", Conversions.convertArrayToStringTime(GameData.totalTimes)));
        System.out.println("asdf " + Arrays.toString(placeHolder));
        for(int i = 0; i<placeHolder.length; i++){
            GameData.totalTimes[i] = Long.parseLong(placeHolder[i]);
        }
        System.out.println("asdf " + Arrays.toString(GameData.totalTimes));

        Long playerTime = GameData.totalTimes[position];

        if(playerTime > 0){
            GameData.placeholderLeaderboard[4] = prefs.getString("pref_name", "your name") + "@" + playerTime;
            scores = Conversions.sortLeaderboard(5);
        } else {
            scores = Conversions.sortLeaderboard(4);
        }

        for(int i = 0; i< scores.length; i++){
            String entry = GameData.placeholderLeaderboard[scores[i][0]];
            if(!entry.equals("")){
                String[] pieces = GameData.placeholderLeaderboard[scores[i][0]].split("@");
                String name = pieces[0];
                String time = pieces[1];
                String message = Conversions.convertTimeToString(Long.parseLong(time)) + " by " + name;
                lbSpots[i].setText(message);
            }
        }

        if(scores.length != GameData.placeholderLeaderboard.length){
            lb5.setText("");
        }

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
