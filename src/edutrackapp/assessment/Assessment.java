/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.assessment;

/**
 *
 * @author dhali
 */
public class Assessment {
    private String id;
    private String studentName;
    private int maxMarks;
    private int obtainedMarks;
    
    private Question[] questions;
    private char[] studentAnswers;
    private int questionCount;

    // constructor

    public Assessment(String id, String studentName, int maxMarks, int obtainedMarks, Question[] questions, char[] studentAnswers, int questionCount) {
        this.id = id;
        this.studentName = studentName;
        this.maxMarks = maxMarks;
        this.obtainedMarks = obtainedMarks;
        this.questions = null;
        this.studentAnswers = null;
        this.questionCount = 0;
    }
    // constructor for MCQ questions
    public Assessment(String id, String studentName, Question[] questions) {
        this.id = id;
        this.studentName = studentName;
        this.questions = questions;
        if (questions != null) {
            this.questionCount = questions.length;
        } else {
            this.questionCount = 0;
        }
        this.maxMarks = this.questionCount;
        this.obtainedMarks = 0;
        this.studentAnswers = new char[this.questionCount];
    }
    // setters

    public void setObtainedMarks(int obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }
    
    //getters

    public String getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public int getObtainedMarks() {
        return obtainedMarks;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public char[] getStudentAnswers() {
        return studentAnswers;
    }

    public int getQuestionCount() {
        return questionCount;
    }
    
}
