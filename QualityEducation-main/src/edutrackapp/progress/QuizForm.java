/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.progress;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Reference:
 * Java Swing Documentation  
 * https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html
 */

/**
 *
 * @author dhali
 */
public class QuizForm extends JFrame {

    private String studentName;
    private ProgressTracker tracker;

    private JTextField txtQuizId;
    private JTextField txtDate;
    private JSpinner spQuestions;

    public QuizForm(String studentName, ProgressTracker tracker) {
        this.studentName = studentName;
        this.tracker = tracker;
        setupUI();
    }

    private void setupUI() {
        setTitle("Attempt Quiz - " + studentName);
        setSize(430, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 26, 40));
        panel.setBorder(new EmptyBorder(16, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setContentPane(panel);

        JLabel logo = createLogoLabel();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Record Quiz Attempt");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(logo);
        panel.add(Box.createVerticalStrut(6));
        panel.add(title);
        panel.add(Box.createVerticalStrut(12));

        txtQuizId = new JTextField();
        txtDate = new JTextField("2025-01-01");
        spQuestions = new JSpinner(new SpinnerNumberModel(5, 1, 20, 1));

        panel.add(createRow("Quiz ID:", txtQuizId));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createRow("Questions answered:", spQuestions));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createRow("Date (optional):", txtDate));
        panel.add(Box.createVerticalStrut(14));

        JButton btnSubmit = new JButton("Submit Attempt");
        btnSubmit.setBackground(new Color(76, 175, 80));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });
        panel.add(btnSubmit);
    }

    private JLabel createLogoLabel() {
        JLabel logo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("edutrack_logo.png"));
            Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            logo.setText("EduTrack");
            logo.setForeground(Color.WHITE);
            logo.setFont(new Font("SansSerif", Font.BOLD, 18));
        }
        return logo;
    }

    private JPanel createRow(String text, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(new Color(20, 26, 40));

        JLabel label = new JLabel(text);
        label.setForeground(new Color(210, 210, 225));
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));

        if (field instanceof JTextField) {
            JTextField tf = (JTextField) field;
            tf.setBackground(new Color(30, 36, 50));
            tf.setForeground(Color.WHITE);
            tf.setBorder(BorderFactory.createLineBorder(new Color(80, 90, 120)));
        }
        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private void handleSubmit() {
        String idText = txtQuizId.getText().trim();
        int answered = (Integer) spQuestions.getValue();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Quiz ID cannot be empty.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String date = txtDate.getText().trim();
        QuizAttempt attempt = new QuizAttempt();
        try {
            attempt.setQuizId(Integer.parseInt(idText));
        } catch (Exception ex) {
            attempt.setQuizId(0);
        }

        if (date.isEmpty()) {
            attempt.setDateTaken("N/A");
        } else {
            attempt.setDateTaken(date);
        }
        for (int i = 0; i < answered; i++) {
            attempt.recordAnswer("Question " + (i + 1), "Answer");
        }
        attempt.submit();
        double score = attempt.getObtainedMarks();

        Student s = new Student(studentName);
        tracker.updateProgress(s, score);
        tracker.calculateRankings();
        JOptionPane.showMessageDialog(this,
                "Quiz submitted!\nScore: " + score,
                "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
