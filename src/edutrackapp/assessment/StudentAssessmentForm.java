/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.assessment;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import edutrackapp.dashboard.StudentDashboard;
import edutrackapp.progress.Student;
import edutrackapp.progress.ProgressTracker;

/**
 *
 * @author egeme
 */

/**
 * Reference:
 * Java Swing Documentation  
 * https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html
 * 
 * (42 Hours) Complete Java Developer Course from Scratch
 * https://www.udemy.com/course/sifirdan-ileri-seviyeye-komple-java-gelistirici-kursu/learn/lecture/8523596#overview
 */
public class StudentAssessmentForm extends JFrame {
    private String studentName;
    private AssessmentManager manager;
    private Assessment assessment;
    private Question[] questions;
    private int i;   // index variable for getting the questions
    
    // GUI components
    private JTextArea txtQuestion;
    private JRadioButton optA, optB, optC, optD;
    private ButtonGroup optionsGroup;
    private JButton btnNext;

    public StudentAssessmentForm(String studentName, AssessmentManager manager) {
        this.studentName = studentName;
        this.manager = manager;

        // creating assessment with 10 default questions
        assessment = manager.createAssessmentForStudent(studentName);
        questions = assessment.getQuestions();
        i = 0; 
        setupUI();
        loadQuestion(i);
    }
    private void setupUI() {
        setTitle("Assessment - " + studentName);
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(20, 26, 40));
        mainPanel.setBorder(new EmptyBorder(16, 20, 20, 20));
        setContentPane(mainPanel);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(20, 26, 40));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        JLabel logoLabel = createLogoLabel();

        JLabel titleLabel = new JLabel("Assessment Quiz");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(logoLabel);
        topPanel.add(Box.createVerticalStrut(6));
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // designing the question and option GUI
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setBackground(new Color(20, 26, 40));

        txtQuestion = new JTextArea("Question is displayed here");
        txtQuestion.setEditable(false);
        txtQuestion.setLineWrap(true);
        txtQuestion.setWrapStyleWord(true);
        txtQuestion.setBackground(new Color(20, 26, 40));
        txtQuestion.setForeground(Color.WHITE);
        txtQuestion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtQuestion.setBorder(null);

        centerPanel.add(txtQuestion, BorderLayout.NORTH);
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 4, 4));
        optionsPanel.setBackground(new Color(20, 26, 40));
        // option buttons
        optA = makeOptionButton();
        optB = makeOptionButton();
        optC = makeOptionButton();
        optD = makeOptionButton();

        optionsPanel.add(optA);
        optionsPanel.add(optB);
        optionsPanel.add(optC);
        optionsPanel.add(optD);
        centerPanel.add(optionsPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        optionsGroup = new ButtonGroup();
        optionsGroup.add(optA);
        optionsGroup.add(optB);
        optionsGroup.add(optC);
        optionsGroup.add(optD);

        // next/ finish button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(20, 26, 40));

        btnNext = new JButton("Next");
        btnNext.setBackground(new Color(76, 175, 80));
        btnNext.setForeground(Color.WHITE);
        btnNext.setFocusPainted(false);
        btnNext.setFont(new Font("SansSerif", Font.BOLD, 13));

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNext();
            }
        });
        bottomPanel.add(btnNext);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    // creating the radio button 
    private JRadioButton makeOptionButton() {
        JRadioButton rb = new JRadioButton();
        rb.setBackground(new Color(20, 26, 40));
        rb.setForeground(new Color(230, 230, 235));
        rb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return rb;
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
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return logo;
    }
    // loading the questions into the GUI
    private void loadQuestion(int index) {
        Question q = questions[index];
        txtQuestion.setText((index + 1) + ". " + q.getText());
        optA.setText("A) " + q.getOptionA());
        optB.setText("B) " + q.getOptionB());
        optC.setText("C) " + q.getOptionC());
        optD.setText("D) " + q.getOptionD());
        optionsGroup.clearSelection();
        if (index == questions.length - 1) {
            btnNext.setText("Finish");
        } else {
            btnNext.setText("Next");
        }
    }
    // functionality for next/finish button
    private void handleNext() {
        char chosen = getSelectedOption();
        if (chosen == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select an answer.",
                    "No selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // recording student's action
        assessment.recordAnswer(i, chosen);
        i++;
        if (i >= questions.length) {
            finishQuiz();
        } else {
            loadQuestion(i);
        }
    }
    private char getSelectedOption() {
        if (optA.isSelected()) return 'A';
        if (optB.isSelected()) return 'B';
        if (optC.isSelected()) return 'C';
        if (optD.isSelected()) return 'D';
        return 0;
    }
    // grading and displaying result 
    private void finishQuiz() {
    // grading the MCQ quiz
    assessment.autoGrade();
    int marks = assessment.getObtainedMarks();
    int max = assessment.getMaxMarks();

    ProgressTracker tracker = StudentDashboard.PROGRESS_TRACKER;
    // creating a Student object with this student's name
    Student s = new Student(studentName);
    // updating the progress along with the marks he received
    tracker.updateProgress(s, marks);
    tracker.calculateRankings();

    // displaying the result to the student 
    JOptionPane.showMessageDialog(this,
            """
            Quiz Completed!
            Marks: """ + marks + "/" + max +
            "\nPercentage: " + String.format("%.1f", assessment.getPercentage()) + "%" +
            "\nGrade: " + assessment.getGrade(),
            "Result",JOptionPane.INFORMATION_MESSAGE);
    dispose(); // closing the quiz window
}
}
