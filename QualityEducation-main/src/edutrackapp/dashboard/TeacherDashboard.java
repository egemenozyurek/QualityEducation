/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.dashboard;

import edutrackapp.userauthorization.User;
import edutrackapp.assessment.TeacherAssessmentForm;
import database.DBConnection;
import edutrackapp.assessment.AssessmentManager;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 *
 * @author dhali, egeme
 */
public class TeacherDashboard extends JFrame {

    private final User loggedUser;
    private TimeTable timeTable;
    private final RepeatSessionGenerator generator = new RepeatSessionGenerator();
    private JTable tableView;
    private WeeklyGridPanel gridPanel;

    public TeacherDashboard(User loggedUser) {
        this.loggedUser = loggedUser;
        this.timeTable = new TimeTable();
        
        System.out.println("Teacher Dashboard created for: " + loggedUser.getName());
        
        loadTeacherDataFromDatabase();
        setupUI();
        setVisible(true);
    }
    
    private void loadTeacherDataFromDatabase() {
        System.out.println("Loading teacher data from database...");
        
        try {
            Connection conn = DBConnection.getConnection();
            if(conn != null) {
                String sql = "select * from schedule where teacher_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                
                ResultSet rs = stmt.executeQuery();
                int count = 0;
                
                while (rs.next()) {
                    String topic = rs.getString("topic");
                    String day = rs.getString("day");
                    String startTime = rs.getString("start_time");
                    String endTime = rs.getString("end_time");
                    
                    ClassSession session = new ClassSession(topic, day, startTime, endTime);
                     timeTable.addSession(session);
                     count++;
                }
                
                System.out.println("Loaded " + count + " sessions from database");
                conn.close();
            } else {
                System.out.println("No database connection, using empty timetable");
                
            }
        } catch (SQLException e) {
            System.out.println("Database load error: " + e.getMessage());
        }
    }
    
    private void doAdd() {
        String topic = JOptionPane.showInputDialog(this, "Topic?");
        if (topic == null || topic.isBlank()) return;
        String day = JOptionPane.showInputDialog(this, "Day?");
        String start = JOptionPane.showInputDialog(this, "Start time?");
        String end = JOptionPane.showInputDialog(this, "End time?");
        ClassSession s = new ClassSession(topic, day, start, end);
        timeTable.addSession(s);
        refreshUI();
    }

    private void doDelete() {
        String topic = JOptionPane.showInputDialog(this, "Topic to delete?");
        if (topic == null || topic.isBlank()) return;
        
        boolean ok = timeTable.removeSessionByTopic(topic);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Topic deleted");
            // Database'den de sil
            deleteSessionFromDatabase(topic);
        } else {
            JOptionPane.showMessageDialog(this, "Topic not found");
        }
        refreshUI();
    }
    
    private void deleteSessionFromDatabase(String topic) {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                String sql = "DELETE FROM schedule WHERE teacher_id = ? AND topic = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);;
                stmt.setString(2, topic);
                stmt.executeUpdate();
                conn.close();
                
                System.out.println("🗑️ Session deleted from database: " + topic);
            }
        } catch (SQLException e) {
            System.out.println("❌ Delete session error: " + e.getMessage());
        }
    }

    private void doMarkDiff() {
        String topic = JOptionPane.showInputDialog(this, "Which topic to mark as challenging?");
        ClassSession s = timeTable.findByTopic(topic);
        if (s != null) { 
            s.setDifficultyLevel(5); 
            JOptionPane.showMessageDialog(this, "Marked challenging"); 
        }
        else JOptionPane.showMessageDialog(this, "Not found");
        refreshUI();
    }

    private void doMarkMissed() {
        String topic = JOptionPane.showInputDialog(this, "Which topic was missed?");
        ClassSession s = timeTable.findByTopic(topic);
        if (s != null) { 
            s.setAttended(false); 
            JOptionPane.showMessageDialog(this, "Marked missed"); 
        }
        else JOptionPane.showMessageDialog(this, "Not found");
        refreshUI();
    }

    private void doAdaptive() {
        generator.applyAdaptiveRules(timeTable);
        JOptionPane.showMessageDialog(this, "Adaptive sessions added (if rules matched).");
        refreshUI();
    }
    
    private void doAssessment() {
         System.out.println("Opening Teacher Assessment Form...");
    
        try {
            // 1. AssessmentManager oluştur
            AssessmentManager manager = new AssessmentManager();
        
            // 2. TeacherAssessmentForm'u aç
            TeacherAssessmentForm assessmentForm = new TeacherAssessmentForm(loggedUser);
            assessmentForm.setVisible(true);
        
        } catch (Exception e) {
            System.out.println("Assessment form error: " + e.getMessage());
        }
        
        showAssessmentForm();
    }
    
    private void showAssessmentForm() {
    JFrame simpleForm = new JFrame("Create Assessment - " + loggedUser.getName());
    simpleForm.setSize(500, 400);
    simpleForm.setLocationRelativeTo(this);
    
    JPanel panel = new JPanel(new BorderLayout());
    
    JLabel titleLabel = new JLabel("Create New Assessment", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    
    JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    formPanel.add(new JLabel("Assessment Title:"));
    JTextField titleField = new JTextField(20);
    formPanel.add(titleField);
    
    formPanel.add(new JLabel("Total Points:"));
    JTextField pointsField = new JTextField(10);
    formPanel.add(pointsField);
    
    formPanel.add(new JLabel("Number of Questions:"));
    JSpinner questionSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 50, 1));
    formPanel.add(questionSpinner);
    
    JButton createBtn = new JButton("Create Assessment");
    createBtn.addActionListener(e -> {
        String title = titleField.getText();
        String points = pointsField.getText();
        int questions = (int) questionSpinner.getValue();
        
        if (!title.isEmpty() && !points.isEmpty()) {
            JOptionPane.showMessageDialog(simpleForm, 
                "Assessment Created!\n" +
                "Title: " + title + "\n" +
                "Points: " + points + "\n" +
                "Questions: " + questions);
            
            // Database'e kaydet
            saveAssessmentToDatabase(title, points, questions);
            
            simpleForm.dispose();
        }
    });
    
    panel.add(titleLabel, BorderLayout.NORTH);
    panel.add(formPanel, BorderLayout.CENTER);
    panel.add(createBtn, BorderLayout.SOUTH);
    
    simpleForm.add(panel);
    simpleForm.setVisible(true);
}
    
    private void saveAssessmentToDatabase(String title, String points, int questions) {
    try {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            String sql = "INSERT INTO exams (title, teacher_id, total_points, created_at) VALUES (?, ?, ?, NOW())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(3, points);
            stmt.executeUpdate();
            
            System.out.println("Assessment saved to database: " + title);
            conn.close();
        }
    } catch (SQLException e) {
        System.out.println("Save assessment error: " + e.getMessage());
    }
}

    private void refreshUI() {
        if (gridPanel != null) {
            getContentPane().remove(gridPanel);
        }
        
        gridPanel = new WeeklyGridPanel(timeTable);
        add(gridPanel, BorderLayout.NORTH);
        
        if (tableView != null) {
            tableView.setModel(new TimeTableModel(timeTable.getSessions()));
        }
        
        revalidate();
        repaint();
    }

    private void setupUI() {
        setTitle("Teacher Dashboard - " + loggedUser);
        setSize(1000,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8,8));

        gridPanel = new WeeklyGridPanel(timeTable);
        add(gridPanel, BorderLayout.NORTH);

        tableView = new JTable(new TimeTableModel(timeTable.getSessions()));
        add(new JScrollPane(tableView), BorderLayout.CENTER);

        JButton addBtn = new JButton("Add Session");
        JButton delBtn = new JButton("Delete by Topic");
        JButton diffBtn = new JButton("Mark Challenging");
        JButton missBtn = new JButton("Mark Missed");
        JButton adaptiveBtn = new JButton("Generate Adaptive Sessions");
        JButton refresh = new JButton("Refresh View");
       
        JButton assessmentBtn = new JButton("Create Assessment");

        addBtn.addActionListener(e -> doAdd());
        delBtn.addActionListener(e -> doDelete());
        diffBtn.addActionListener(e -> doMarkDiff());
        missBtn.addActionListener(e -> doMarkMissed());
        adaptiveBtn.addActionListener(e -> doAdaptive());
        assessmentBtn.addActionListener(e -> doAssessment());
        refresh.addActionListener(e -> refreshUI());

        JPanel p = new JPanel();
        p.add(addBtn); p.add(delBtn); p.add(diffBtn); p.add(missBtn); p.add(adaptiveBtn); p.add(refresh); p.add(assessmentBtn);
        add(p, BorderLayout.SOUTH);
    }
}