package com.idk.thegrind;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>{
    String[] subjects;
    QuestionDialog questionDialog;

    public QuestionAdapter(String[] subjects, QuestionDialog questionDialog) {
        this.subjects = subjects;
        this.questionDialog = questionDialog;
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final Button buttonView;

        public QuestionViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            buttonView = (Button) view.findViewById(R.id.question_icon);
        }

        public Button getButtonView() {
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
        viewHolder.getButtonView().setText(subjects[position]);

        viewHolder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionDialog.showDialog(position);
            }
        });

        questionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                GameData.eligible[position] = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.length;
    }
}
