package com.idk.thegrind;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

public class InstructionsDialog extends Dialog {
    public InstructionsDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.instructions_dialog);
    }

    public void showDialog(){
        Button close = findViewById(R.id.close_instruct);

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
