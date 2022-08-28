package com.idk.thegrind;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>{
    String[] subjects;
    QuestionDialog questionDialog;
    LeaderboardDialog leaderboardDialog;
    SharedPreferences prefs;
    PlaySound playSound;

    public QuestionAdapter(String[] subjects, QuestionDialog questionDialog, LeaderboardDialog leaderboardDialog, SharedPreferences prefs, PlaySound playSound) {
        this.subjects = subjects;
        this.questionDialog = questionDialog;
        this.prefs = prefs;
        this.leaderboardDialog = leaderboardDialog;
        this.playSound = playSound;
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton buttonView;

        public QuestionViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            buttonView = view.findViewById(R.id.question_icon);
        }

        public ImageButton getButtonView() {
            return buttonView;
        }
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_cell, viewGroup, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder viewHolder, final int pos) {

        final int position = viewHolder.getAdapterPosition();

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        if(GameData.eligible[position] != 2){
            viewHolder.getButtonView().setImageResource(GameData.subjectButtons[position]);
        } else {
            viewHolder.getButtonView().setImageResource(GameData.subjectButtonsLeaderboard[position]);
        }

        viewHolder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound.playSound(R.raw.click);
                Conversions.checkDate(prefs);

                if(GameData.eligible[position] != 2){
                    questionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    questionDialog.showDialog(position, viewHolder.getButtonView());
                } else {
                    leaderboardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    leaderboardDialog.showDialog(position);
                    // leaderboard page
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.length;
    }
}

interface PlaySound {
    void playSound(int sound);
}
