/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.assessment;

/**
 *
 * @author aomof
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

    public void recordAnswer(int index, char option) {
        if (studentAnswers == null) {
            return;
        }
        if (index < 0 || index >= questionCount) {
            return;
        }
        studentAnswers[index] = Character.toUpperCase(option);
    }

    public void autoGrade() {
        if (questions == null || studentAnswers == null) {
            return;
        }
        int correct = 0;

        for (int i = 0; i < questionCount; i++) {
            char chosen = studentAnswers[i];
            if (chosen != 0 && chosen == questions[i].getCorrectOption()) {
                correct++;
            }
        }
        obtainedMarks = correct;
    }

    public double getPercentage() {
        if (maxMarks <= 0) {
            return 0.0;
        }
        return (obtainedMarks * 100.0) / maxMarks;
    }

    public String getGrade() {
        double p = getPercentage();
        if (p >= 70) {
            return "A";
        }
        if (p >= 60) {
            return "B";
        }
        if (p >= 50) {
            return "C";
        }
        if (p >= 40) {
            return "D";
        }
        return "F";
    }

    @Override
    public String toString() {
        return "Assessment{" + "id='" + id + '\'' + ", studentName='" + studentName + '\'' + ", maxMarks=" + maxMarks + ", obtainedMarks=" + obtainedMarks + ", percentage=" + String.format("%.1f", getPercentage()) + ", grade='" + getGrade() + '\'' + '}';
    }
}
