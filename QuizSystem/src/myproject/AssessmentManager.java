/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myprject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author aomof
 */
public class AssessmentManager {
    
     public int uploadQuiz(String title, int totalMarks, int teacherID) {

        String sql = "INSERT INTO Quiz (title, totalMarks, teacherID) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, title);
            ps.setInt(2, totalMarks);
            ps.setInt(3, teacherID);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int quizID = rs.getInt(1);
                System.out.println("Quiz Created (ID = " + quizID + ")");
                return quizID;
            }

        } catch (SQLException e) {
            System.out.println("uploadQuiz error: " + e.getMessage());
        }

        return -1;
    }

 
    // put a question to a quiz
     
    public void addQuestion(int quizID, String text, String a, String b, String c, String d, String answer) {

        String sql = "INSERT INTO Question (quizID, questionText, optionA, optionB, optionC, optionD, correctAnswer) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quizID);
            ps.setString(2, text);
            ps.setString(3, a);
            ps.setString(4, b);
            ps.setString(5, c);
            ps.setString(6, d);
            ps.setString(7, answer);

            ps.executeUpdate();

            System.out.println("Question Added");

        } catch (SQLException e) {
            System.out.println("addQuestion error: " + e.getMessage());
        }
    }


    // grade a student for a quiz
    
    public void gradeStudent(int studentID, int quizID, int score, int teacherID) {

        String sql = "INSERT INTO StudentResult (studentID, quizID, score, gradedBy) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentID);
            ps.setInt(2, quizID);
            ps.setInt(3, score);
            ps.setInt(4, teacherID);

            ps.executeUpdate();

            System.out.println("Student graded.");

        } catch (SQLException e) {
            System.out.println("gradeStudent error: " + e.getMessage());
        }
    }


    // the showing of grade and summary
 
    public void showSummary(int teacherID) {

        String sql = "SELECT r.resultID, s.name, q.title, r.score "
                   + "FROM StudentResult r "
                   + "JOIN Student s ON r.studentID = s.studentID "
                   + "JOIN Quiz q ON r.quizID = q.quizID "
                   + "WHERE q.teacherID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teacherID);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Grading Summary ---");

            while (rs.next()) {
                System.out.println(
                        "ResultID: " + rs.getInt("resultID") +
                        " | Student: " + rs.getString("name") +
                        " | Quiz: " + rs.getString("title") +
                        " | Score: " + rs.getInt("score")
                );
            }

        } catch (SQLException e) {
            System.out.println("showSummary error: " + e.getMessage());
        }
    }
    
}
