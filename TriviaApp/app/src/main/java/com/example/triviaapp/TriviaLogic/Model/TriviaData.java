package com.example.triviaapp.TriviaLogic.Model;

import java.util.ArrayList;
import java.util.Random;

public class TriviaData {

    private String question;

    private String answer;
    private String incorrect1;
    private String incorrect2;
    private String incorrect3;

    private ArrayList<String> answers_ALL;

    public TriviaData(String question, String answer, String incorrect1, String incorrect2, String incorrect3) {

        this.question = question;
        this.answer = answer;
        this.incorrect1 = incorrect1;
        this.incorrect2 = incorrect2;
        this.incorrect3 = incorrect3;

        answers_ALL = new ArrayList<>();

        answers_ALL.add(incorrect1);
        answers_ALL.add(incorrect2);
        answers_ALL.add(incorrect3);

        int randomNumber;
        randomNumber = 0;
        Random random = new Random();

        randomNumber = random.nextInt(4);
        answers_ALL.add(randomNumber, answer);

    }

    public ArrayList<String> getAnswers_ALL() {
        return answers_ALL;
    }

    public void setAnswers_ALL(ArrayList<String> answers_ALL) {
        this.answers_ALL = answers_ALL;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIncorrect1() {
        return incorrect1;
    }

    public void setIncorrect1(String incorrect1) {
        this.incorrect1 = incorrect1;
    }

    public String getIncorrect2() {
        return incorrect2;
    }

    public void setIncorrect2(String incorrect2) {
        this.incorrect2 = incorrect2;
    }

    public String getIncorrect3() {
        return incorrect3;
    }

    public void setIncorrect3(String incorrect3) {
        this.incorrect3 = incorrect3;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
