package com.idk.thegrind;

import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Conversions {
    public static String convertArrayToString(int array[]) {
        StringBuilder arrayString = new StringBuilder((String) "");

        for(int i = 0; i<array.length; i++){
            if(i != 0){
                arrayString.append("@");
            }
            if(array[i]<0){
                arrayString.append("-1");
            } else {
                arrayString.append(array[i]);
            }
        }

        return arrayString.toString();
    }

    public static String convertArrayToStringTime(String array[]) {
        StringBuilder arrayString = new StringBuilder((String) "");

        for(int i = 0; i<array.length; i++){
            if(i != 0){
                arrayString.append("@");
            }
            if(Long.parseLong(array[i])<0){
                arrayString.append("-1");
            } else {
                arrayString.append(array[i]);
            }
        }

        return arrayString.toString();
    }

    public static String[] convertArrayStringToArray(String arrayString){
        String[] newArray = arrayString.split("@", -1);

        return newArray;
    }

    public static int[] convertArrayStringToArrayInt(String arrayString){
        String[] newArray = arrayString.split("@", -1);
        int[] newIntArray = new int[newArray.length];

        for(int i = 0; i<newArray.length; i++){
            newIntArray[i] = Integer.parseInt(newArray[i]);
        }

        return newIntArray;
    }

    public static String convertTimeToString(Long msDiff){
        long hours = msDiff/3600000;
        long mins = (msDiff-hours*3600000)/60000;
        long secs = (msDiff-hours*3600000-mins*60000)/1000;
        long mils = msDiff-hours*3600000-mins*60000-secs*1000;

        String totalTime = "";

        if(hours/10 == 0){
            totalTime += "0";
        }
        totalTime += hours + ":";
        if(mins/10 == 0){
            totalTime += "0";
        }
        totalTime +=  mins + ":";
        if(secs/10 == 0){
            totalTime += "0";
        }
        totalTime += secs + ":";
        if(mils/10 == 0){
            totalTime += "00";
        } else if(mils/100 == 0){
            totalTime += "0";
        }
        totalTime += mils;

        return totalTime;
    }

    public static void checkDate(SharedPreferences prefs){
        String currDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        if(!prefs.getString("last_date", "none").equals(currDate)){ // if it's the next day
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("game_status", Conversions.convertArrayToString(new int[GameData.subjects.length])); // reset eligibility
            edit.putString("last_date", currDate);
            edit.putString("starting_times", Conversions.convertArrayToStringTime(GameData.startingTimesDefault.clone()));
            edit.apply();
        }
    }

    public static void resetAll(SharedPreferences prefs){
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("game_status", Conversions.convertArrayToString(new int[GameData.subjects.length])); // reset eligibility
        edit.putString("starting_times", Conversions.convertArrayToStringTime(GameData.startingTimesDefault.clone()));
        edit.putBoolean("isSFX", true);
        edit.putBoolean("isNew", true);
        edit.putString("pref_name", "");
        edit.apply();
    }
}
