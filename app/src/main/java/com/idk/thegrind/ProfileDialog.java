package com.idk.thegrind;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class ProfileDialog extends Dialog {
    SharedPreferences prefs;

    public ProfileDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.profile_dialog);

        prefs = context.getSharedPreferences("grind_prefs", Context.MODE_PRIVATE);
    }

    public void showDialog(){
        Button save = findViewById(R.id.save);
        EditText nameEntry = findViewById(R.id.pref_name);
        nameEntry.setText(prefs.getString("pref_name", ""));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sounds.play(getContext(), R.raw.click);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("pref_name", nameEntry.getText().toString());
                edit.apply();
                dismiss();
            }
        });

        show();
    }

}
