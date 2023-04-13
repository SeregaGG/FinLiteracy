package com.example.test;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {
    public static Question getQuestion(int coins, String location){
        return questions_.stream().filter(question -> question.coins == coins).findFirst().get();
    }

    public static void putQuestions(List<Question> list){
        questions_ = list;
    }

    private static List<Question> questions_ = new ArrayList<>();
}
