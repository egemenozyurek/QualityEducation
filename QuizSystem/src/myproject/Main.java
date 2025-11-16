/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myproject;

/**
 *
 * @author aomof
 */
public class Main {
    
    
        AssessmentManager am = new AssessmentManager();

        int TeacherID = 3;

        //  Create quiz for the teachter
        int quizID = am.uploadQuiz("Algebra Quiz", 20, teacherID);

        //  Add 2 demo questions
        am.addQuestion(quizID, "What is 2 + 2?", "3", "4", "5", "6", "4");
        am.addQuestion(quizID, "What is 10 / 2?", "3", "5", "6", "10", "5");

        // Grade student (has to be in the database)
        am.gradeStudent(1, quizID, 18, teacherID);

        // Show grading summary
        am.showSummary(almiraTeacherID);
    }
    
}
