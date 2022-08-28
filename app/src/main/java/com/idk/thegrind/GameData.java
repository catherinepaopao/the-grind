package com.idk.thegrind;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class GameData {
    public static int currentPage = 1; // 1 questions, 2 leaderboard, 3 settings
    public static String[] subjects = {"Algebra", "Grammar", "Chemistry", "US History"};
    public static int[] subjectButtons = {R.drawable.algebra2, R.drawable.grammar2, R.drawable.chemistry2, R.drawable.history2}; // replace with images for buttons later
    public static int[] subjectButtonsLeaderboard = {R.drawable.algebradone, R.drawable.grammardone, R.drawable.chemistrydone, R.drawable.historydone}; // replace with images for buttons later

    public static String[] subjectQuestions = {"If 5x+2=22, x=?\na. 1 \nb. 2\nc. 3\nd. 4",
            "Let's go ____ Grandma! \na. eat \nb. ate, \nc. eat, \nd. eating,",
            "What is the chemical symbol for iron? \na. Ir \nb. In \nc. Fr \nd. Fe",
            "Who was the first US President? \na. George Waterbowl \nb. Georgia Washington \nc. George Washington \nd. Gregorian Calendar"}; // initialize with questions for each later
    public static String[] subjectAnswers = {"d", "c", "d", "c"}; // initialize with answers based on questions, take from server if we use
    public static String[] startingTimes = {"-1", "-1", "-1", "-1"}; // set to starting time/viewing time of each question, set to negative if not started
    public static String[] startingTimesDefault = {"-1", "-1", "-1", "-1"};
    public static int[] eligible = {0, 0, 0, 0}; // 0 if eligible, 1 if not, 2 if answered correctly
}
