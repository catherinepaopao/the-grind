package com.idk.thegrind;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Locale;

public class QuestionDialog extends Dialog {
    public QuestionDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.question_dialog);

    }

    public void showDialog(int position){
        Button submit = findViewById(R.id.submit);
        TextView eligibility = findViewById(R.id.eligibility);
        TextView subjectName = findViewById(R.id.subject_name);
        TextView questionText = findViewById(R.id.question_text);

        subjectName.setText(GameData.subjects[position].toUpperCase(Locale.ROOT));
        questionText.setText(GameData.subjectQuestions[position]);

        if(GameData.eligible[position]){
            eligibility.setText("");
        } else {
            eligibility.setText(R.string.not_eligible);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameData.eligible[position]){ // leaderboard submit

                } else {

                }

                GameData.eligible[position] = false;
                dismiss();
            }
        });

        show();
    }
}
