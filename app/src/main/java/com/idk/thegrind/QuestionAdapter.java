package com.idk.thegrind;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>{
    String[] subjects;
    QuestionDialog questionDialog;

    public QuestionAdapter(String[] subjects, QuestionDialog questionDialog) {
        this.subjects = subjects;
        this.questionDialog = questionDialog;
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
        viewHolder.getButtonView().setImageResource(GameData.subjectButtons[position]);

        viewHolder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameData.eligible[position] != 2){
                    questionDialog.showDialog(position);
                } else {
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
