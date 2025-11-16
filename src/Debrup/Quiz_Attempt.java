/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Debrup;

/**
 *
 * @author dhali
 */
public class Quiz_Attempt {

    private int quizId;
    private double obtainedMarks;
    private String dateTaken;
    private boolean isSubmitted;

    private final String[] questions;
    private final String[] answers;
    private int totalQuestions;

    public Quiz_Attempt() {
        questions = new String[20];
        answers = new String[20];
        totalQuestions = 0;
        obtainedMarks = 0.0;
        isSubmitted = false;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public int getQuizId() {
        return quizId;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void recordAnswer(String question, String answer) {
        if (totalQuestions < 20) {
            questions[totalQuestions] = question;
            answers[totalQuestions] = answer;
            totalQuestions++;
        }
    }

    public void submit() {
        isSubmitted = true;
        calculateScore();
    }

    public double calculateScore() {
        obtainedMarks = totalQuestions; 
        return obtainedMarks;
    }

    public double getObtainedMarks() {
        return obtainedMarks;
    }

    public void viewResult() {
        System.out.println("Quiz ID: " + quizId);
        System.out.println("Date: " + dateTaken);
        System.out.println("Questions answered: " + totalQuestions);
        System.out.println("Score: " + obtainedMarks);
    }
}


