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
public class ProgressForm extends JFrame {
    private String studentName;
    private ProgressTracker tracker;

    public ProgressForm(String studentName, ProgressTracker tracker) {
        this.studentName = studentName;
        this.tracker = tracker;
        setupUI();
    }        
    private void setupUI() {
        setTitle("EduTrack - Student Progress");
        setSize(500, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(20, 26, 40));
        mainPanel.setBorder(new EmptyBorder(16, 20, 20, 20));
        setContentPane(mainPanel);
        
        // setting up the logo and labels
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(20, 26, 40));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel logoLabel = createLogoLabel();
        JLabel titleLabel = new JLabel("Student Progress & Engagement");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Logged in as: " + studentName);
        nameLabel.setForeground(new Color(200, 200, 210));
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(logoLabel);
        topPanel.add(Box.createVerticalStrut(8));
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(4));
        topPanel.add(nameLabel);
        topPanel.add(Box.createVerticalStrut(10));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        //buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(20, 26, 40));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JButton btnQuiz = createMainButton("Attempt Quiz");
        JButton btnRank = createSecondaryButton("My Rank");
        JButton btnLeaderboard = createSecondaryButton("View Leaderboard");

        btnQuiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizForm quizForm = new QuizForm(studentName, tracker);
                quizForm.setVisible(true);
            }
        });
        btnRank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMyRank();
            }
        });        
        btnLeaderboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LeaderboardForm lbForm = new LeaderboardForm();
                lbForm.setVisible(true);
            }
        });
        centerPanel.add(btnQuiz);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(btnRank);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(btnLeaderboard);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
    }
     private JLabel createLogoLabel() {
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("edutrack_logo.png"));
            Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            logoLabel.setText("EduTrack");
            logoLabel.setForeground(Color.WHITE);
            logoLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        }
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return logoLabel;
    }
    private JButton createMainButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(76, 175, 80)); 
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setMaximumSize(new Dimension(230, 40));
        return button;
    }
    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(40, 46, 60));
        button.setForeground(new Color(230, 230, 235));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(85, 95, 130)));
        button.setFont(new Font("SansSerif", Font.PLAIN, 13));
        button.setMaximumSize(new Dimension(230, 36));
        return button;
    }
    // rank 
    private void showMyRank() {
        tracker.calculateRankings();
        int rank = tracker.getStudentRank(studentName);
        int totalPoints = tracker.getLeaderboardPoints();

        if (rank == -1) {
            JOptionPane.showMessageDialog(this,
                    "No quiz attempts recorded yet.\nPlease attempt a quiz first.",
                    "No Data",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Hi " + studentName + "!\n" +
                            "Your current rank is: #" + rank + "\n" +
                            "Total leaderboard points (all students): " + totalPoints,
                    "My Rank",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

