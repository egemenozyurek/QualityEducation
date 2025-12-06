/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.assessment;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author aomof
 */
public class AssessmentManager {
    private ArrayList<Assessment> list;
    
    public AssessmentManager() {
        list = new ArrayList<Assessment>();
        loadAssessmentFromDatabase();
        
        String sql = "SELECT * FROM exams";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int examId = rs.getInt("id");
                String title = rs.getString("title");
                int teacherId = rs.getInt("teacher_id");
                double totalPoints = rs.getDouble("total_points");
                
                // Bu exam için soruları yükle
                Question[] questions = loadQuestionsForExam(examId);
                
                // Assessment oluştur
                Assessment assessment = new Assessment(title, "TeacherID: " + teacherId, questions);
                list.add(assessment);
            }
            
            System.out.println("✅ Loaded " + list.size() + " assessments from database");
            
        } catch (SQLException e) {
            System.out.println("❌ Load assessments error: " + e.getMessage());
        }
    }
    
    private Question[] loadQuestionsForExam(int examId) {
        ArrayList<Question> questionsList = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE exam_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, examId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Question q = new Question(
                    rs.getString("question_text"),
                    rs.getString("option_a"),
                    rs.getString("option_b"),
                    rs.getString("option_c"),
                    rs.getString("option_d"),
                    rs.getString("correct_answer").charAt(0),
                    rs.getString("category")
                );
                questionsList.add(q);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Load questions error: " + e.getMessage());
        }
        
        return questionsList.toArray(new Question[0]);
    }
    
    private void loadAssessmentFromDatabase() {
        
    }
    
    public void addAssessment(Assessment a) {
        if (a != null) {
            list.add(a);
        }
    }
    
    public ArrayList<Assessment> getAllAssessment() {
        return list;
    }
    
    // creating the default quiz
    public Question[] createDefaultQuestions() {
        Question[] q = new Question[10];
        // putting throught the 10 default questions along with their options and category
        q[0] = new Question(
                "Which country has the capital city 'Paris'?",
                "Germany", "France", "Italy", "Spain",
                'B', "Geography");
        
        q[1] = new Question(
                "H2O is the chemical formula for:",
                "Carbon dioxide", "Hydrogen", "Oxygen", "Water",
                'D', "Science");
        
        q[2] = new Question(
                "Which planet is known as the Red Planet?",
                "Venus", "Mars", "Jupiter", "Saturn",
                'B', "Science");
        
        q[3] = new Question(
                "Who wrote the play 'Romeo and Juliet'?",
                "Charles Dickens", "William Shakespeare",
                "Mark Twain", "Jane Austen",
                'B', "English");

        q[4] = new Question(
                "Which of these is a mammal?",
                "Eagle", "Shark", "Dolphin", "Crocodile",
                'C', "Science");

        q[5] = new Question(
                "The largest ocean on Earth is the:",
                "Indian Ocean", "Atlantic Ocean",
                "Pacific Ocean", "Arctic Ocean",
                'C', "Geography");

        q[6] = new Question(
                "What is the value of 5 × 6?",
                "11", "25", "30", "56",
                'C', "Maths");

        q[7] = new Question(
                "Which language is mainly spoken in Brazil?",
                "Spanish", "Portuguese", "French", "English",
                'B', "General Knowledge");

        q[8] = new Question(
                "Which of the following is a renewable energy source?",
                "Coal", "Oil", "Natural gas", "Solar energy",
                'D', "Science");

        q[9] = new Question(
                "In the sentence 'She is reading a book', 'reading' is a:",
                "Noun", "Verb", "Adjective", "Adverb",
                'B', "English");

        return q;
    }
    
    public Assessment createAssessmentForStudent(String studentName) {
        Question[] questions = createDefaultQuestions();
        Assessment a = new Assessment("QUIZ1", studentName, questions);
        addAssessment(a);
        return a;
    }
}
