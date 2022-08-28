package com.idk.thegrind;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class Sounds {
    public static void play(Context context, int sound){
        SharedPreferences prefs = context.getSharedPreferences("grind_prefs", Context.MODE_PRIVATE);
        if(prefs.getBoolean("isSFX", true)){
            MediaPlayer player = MediaPlayer.create(context, sound);
            player.start();

            player.setOnCompletionListener(mp -> mp.reset());
        }
    }
}