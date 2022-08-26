package com.idk.thegrind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button start;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = this.getSharedPreferences("grind_prefs", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        start = findViewById(R.id.startbutton);
        InstructionsDialog instructDialog = new InstructionsDialog(this);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getBoolean("isNew", true)){
                    instructDialog.showDialog();

                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean("isNew", false);
                    edit.apply();

                    instructDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            Intent toQuestions = new Intent(getApplicationContext(), QuestionsPage.class);
                            startActivity(toQuestions);
                        }
                    });
                } else {
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean("isNew", true);
                    edit.apply(); // testing purposes

                    Intent toQuestions = new Intent(getApplicationContext(), QuestionsPage.class);
                    startActivity(toQuestions);
                }
            }
        });
    }
}