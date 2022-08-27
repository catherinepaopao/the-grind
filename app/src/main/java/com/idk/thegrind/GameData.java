package com.idk.thegrind;

public class GameData {
    public static int currentPage = 1; // 1 questions, 2 leaderboard, 3 settings
    public static String[] subjects = {"Algebra", "Grammar", "Chemistry", "US History"}; // replace with images for buttons later
    public static String[] subjectQuestions = {"alg q", "gram q", "chem q", "hist q"}; // initialize with questions for each later
    public static String[] subjectAnswers = {}; // initialize with answers based on questions, take from server if we use
    public static boolean[] eligible = {true, true, true, true}; // if user hasn't viewed subject yet
}
