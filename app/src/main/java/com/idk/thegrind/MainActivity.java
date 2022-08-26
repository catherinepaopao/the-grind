package com.idk.thegrind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        start = findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Sounds.play(MainActivity.this, R.raw.select);
                Intent toQuestions = new Intent(getApplicationContext(), QuestionsPage.class);
                startActivity(QuestionsPage); */
            }
        });
    }
}