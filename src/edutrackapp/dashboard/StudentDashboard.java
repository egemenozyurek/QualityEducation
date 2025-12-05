/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.dashboard;

import edutrackapp.progress.ProgressForm;
import edutrackapp.progress.ProgressTracker;
import edutrackapp.userauthorization.User;
import edutrackapp.assessment.AssessmentManager;
import edutrackapp.assessment.StudentAssessmentForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author dhali, egeme
 */
public class StudentDashboard extends JFrame {
    public static final ProgressTracker PROGRESS_TRACKER = new ProgressTracker();
    public static final AssessmentManager ASSESSMENT_MANAGER = new AssessmentManager();
    private final User loggedUser;
    
    public StudentDashboard(User loggedUser) {
        this.loggedUser = loggedUser;
        setupUI();
    }

    private void setupUI() {
        setTitle("EduTrack - Student Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //button
        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 26, 40));
        panel.setLayout(new BorderLayout());

        // creating the welcome label
        JLabel lbl = new JLabel("Welcome, " + loggedUser.getName() + " (Student)",
                SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));

        panel.add(lbl, BorderLayout.NORTH);

        // creating the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(20, 26, 40));
        buttonPanel.setLayout(new FlowLayout());

        JButton progressBtn = new JButton("Progress & Quiz");
        progressBtn.setBackground(new Color(40, 46, 60));
        progressBtn.setForeground(Color.WHITE);
        progressBtn.setFocusPainted(false);

        // recording the action of the user
        progressBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProgressForm form =
                        new ProgressForm(loggedUser.getName(), PROGRESS_TRACKER);
                form.setVisible(true);
            }
        });
        //assessment button for students
        JButton assessBtn = new JButton("Take Assessment");
        assessBtn.setBackground(new Color(40, 46, 60));
        assessBtn.setForeground(Color.WHITE);
        assessBtn.setFocusPainted(false);

        assessBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentAssessmentForm form =
                        new StudentAssessmentForm(loggedUser.getName(), ASSESSMENT_MANAGER);
                form.setVisible(true);
            }
        });
        // adding the button to the panel
        buttonPanel.add(progressBtn);
        buttonPanel.add(assessBtn);
        panel.add(buttonPanel, BorderLayout.CENTER);
        add(panel);
    }
}