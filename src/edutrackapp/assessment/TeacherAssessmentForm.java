/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.assessment;

import edutrackapp.userauthorization.User;
import database.DBConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

/**
 *
 * @author egeme
 * 
 * 
 */
public class TeacherAssessmentForm extends JFrame {
    private User teacher; 

    public TeacherAssessmentForm(User teacher) {
        this.teacher = teacher;
        
        setTitle("Create Exam - " + teacher.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setupUI();
    }
    
    private void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(20, 26, 40));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);
        
        // Üst panel - Başlık ve logo
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(20, 26, 40));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel logoLabel = createLogoLabel();

        JLabel titleLabel = new JLabel("CREATE NEW EXAM");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(logoLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Merkez panel - Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(30, 36, 50));
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(60, 70, 100), 2, true));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Exam Title
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel titleLabel2 = new JLabel("Exam Title:");
        titleLabel2.setForeground(Color.WHITE);
        titleLabel2.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(titleLabel2, gbc);
        
        gbc.gridx = 1;
        JTextField examTitleField = new JTextField(25);
        examTitleField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        examTitleField.setBackground(new Color(50, 56, 70));
        examTitleField.setForeground(Color.WHITE);
        examTitleField.setCaretColor(Color.WHITE);
        examTitleField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 90, 120)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        formPanel.add(examTitleField, gbc);
        
        // Number of Questions
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel questionsLabel = new JLabel("Number of Questions:");
        questionsLabel.setForeground(Color.WHITE);
        questionsLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(questionsLabel, gbc);
        
        gbc.gridx = 1;
        JSpinner questionSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 50, 1));
        questionSpinner.setFont(new Font("SansSerif", Font.PLAIN, 14));
        questionSpinner.setBackground(new Color(50, 56, 70));
        questionSpinner.setForeground(Color.WHITE);
        formPanel.add(questionSpinner, gbc);
        
        // Buton paneli
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(30, 36, 50));
        
        JButton createBtn = new JButton("Create Exam & Add Questions");
        styleButton(createBtn, new Color(0, 150, 100));
        
        JButton cancelBtn = new JButton("Cancel");
        styleButton(cancelBtn, new Color(180, 50, 50));
        
        createBtn.addActionListener(e -> {
            String title = examTitleField.getText().trim();
            int questionCount = (int) questionSpinner.getValue();
            
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter exam title!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 1. Database'e exam kaydet
            int examId = saveExamToDatabase(title);
            
            if (examId > 0) {
                // 2. Soru ekleme formunu aç
                openQuestionForm(examId, title, questionCount);
                dispose(); // Bu formu kapat
            }
        });
        
        cancelBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(createBtn);
        buttonPanel.add(cancelBtn);
        formPanel.add(buttonPanel, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Alt panel - Bilgi
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(20, 26, 40));
        JLabel infoLabel = new JLabel("Teacher: " + teacher.getName() + " | Create exams for your students");
        infoLabel.setForeground(new Color(180, 180, 200));
        infoLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        bottomPanel.add(infoLabel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 2),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
    
    private JLabel createLogoLabel() {
        JLabel logo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("edutrack_logo.png"));
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            logo.setText("📝 EXAM CREATOR");
            logo.setForeground(Color.WHITE);
            logo.setFont(new Font("SansSerif", Font.BOLD, 18));
        }
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return logo;
    }
    
    private int saveExamToDatabase(String title) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
            
            String sql = "INSERT INTO exams (title, teacher_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, title);
            stmt.executeUpdate();
            
            // Oluşan exam ID'sini al
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int examId = rs.getInt(1);
                System.out.println("Exam created: " + title + " (ID: " + examId + ")");
                
                // Kullanıcıya bilgi ver
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, 
                        "Exam created successfully!\n" +
                        "Title: " + title + "\n" +
                        "Exam ID: " + examId + "\n\n" +
                        "Now add questions...",
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                });
                
                return examId;
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error saving exam: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error creating exam: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
    
    private void openQuestionForm(int examId, String examTitle, int questionCount) {
        // Soru ekleme formunu aç
        QuestionForm questionForm = new QuestionForm(examId, examTitle, questionCount, teacher);
        questionForm.setVisible(true);
    }
    
    // İç sınıf: Soru ekleme formu
    class QuestionForm extends JFrame {
        private int examId;
        private String examTitle;
        private int questionCount;
        private User teacher;
        private int currentQuestion = 0;
        
        private JTextField questionField;
        private JTextField[] optionFields = new JTextField[4];
        private JComboBox<String> answerCombo;
        private JLabel progressLabel;
        
        public QuestionForm(int examId, String examTitle, int questionCount, User teacher) {
            this.examId = examId;
            this.examTitle = examTitle;
            this.questionCount = questionCount;
            this.teacher = teacher;
            
            setTitle("Add Questions - " + examTitle + " (1/" + questionCount + ")");
            setSize(700, 600);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            setupQuestionUI();
        }
        
        private void setupQuestionUI() {
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(20, 26, 40));
            mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            
            // Üst panel - İlerleme
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBackground(new Color(20, 26, 40));
            
            progressLabel = new JLabel("Question " + (currentQuestion + 1) + " of " + questionCount);
            progressLabel.setForeground(Color.WHITE);
            progressLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            
            JLabel titleLabel = new JLabel("Exam: " + examTitle);
            titleLabel.setForeground(new Color(180, 180, 200));
            titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            
            topPanel.add(progressLabel, BorderLayout.NORTH);
            topPanel.add(titleLabel, BorderLayout.SOUTH);
            
            // Soru formu
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(new Color(30, 36, 50));
            formPanel.setBorder(BorderFactory.createLineBorder(new Color(60, 70, 100), 2, true));
            formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            // Question Text
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
            JLabel qLabel = new JLabel("Question:");
            qLabel.setForeground(Color.WHITE);
            qLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            formPanel.add(qLabel, gbc);
            
            gbc.gridy = 1;
            questionField = new JTextField(40);
            styleTextField(questionField);
            formPanel.add(questionField, gbc);
            
            // Options
            String[] optionLabels = {"Option A:", "Option B:", "Option C:", "Option D:"};
            gbc.gridwidth = 1;
            
            for (int i = 0; i < 4; i++) {
                gbc.gridx = 0; gbc.gridy = i + 2;
                JLabel label = new JLabel(optionLabels[i]);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("SansSerif", Font.BOLD, 14));
                formPanel.add(label, gbc);
                
                gbc.gridx = 1;
                optionFields[i] = new JTextField(30);
                styleTextField(optionFields[i]);
                formPanel.add(optionFields[i], gbc);
            }
            
            // Correct Answer
            gbc.gridx = 0; gbc.gridy = 6;
            JLabel answerLabel = new JLabel("Correct Answer:");
            answerLabel.setForeground(Color.WHITE);
            answerLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            formPanel.add(answerLabel, gbc);
            
            gbc.gridx = 1;
            String[] answers = {"A", "B", "C", "D"};
            answerCombo = new JComboBox<>(answers);
            answerCombo.setFont(new Font("SansSerif", Font.PLAIN, 14));
            answerCombo.setBackground(new Color(50, 56, 70));
            answerCombo.setForeground(Color.WHITE);
            formPanel.add(answerCombo, gbc);
            
            // Butonlar
            gbc.gridx = 0; gbc.gridy = 7;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            buttonPanel.setBackground(new Color(30, 36, 50));
            
            JButton nextBtn = new JButton(currentQuestion < questionCount - 1 ? "Next Question" : "Finish & Save");
            styleButton(nextBtn, new Color(0, 120, 180));
            
            JButton cancelBtn = new JButton("Cancel");
            styleButton(cancelBtn, new Color(180, 50, 50));
            
            nextBtn.addActionListener(e -> saveCurrentQuestion());
            cancelBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Cancel adding questions? All unsaved questions will be lost.",
                    "Confirm Cancel", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                }
            });
            
            buttonPanel.add(nextBtn);
            buttonPanel.add(cancelBtn);
            formPanel.add(buttonPanel, gbc);
            
            mainPanel.add(topPanel, BorderLayout.NORTH);
            mainPanel.add(formPanel, BorderLayout.CENTER);
            
            add(mainPanel);
        }
        
        private void styleTextField(JTextField field) {
            field.setFont(new Font("SansSerif", Font.PLAIN, 14));
            field.setBackground(new Color(50, 56, 70));
            field.setForeground(Color.WHITE);
            field.setCaretColor(Color.WHITE);
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 90, 120)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
        }
        
        private void saveCurrentQuestion() {
            String questionText = questionField.getText().trim();
            String optionA = optionFields[0].getText().trim();
            String optionB = optionFields[1].getText().trim();
            String optionC = optionFields[2].getText().trim();
            String optionD = optionFields[3].getText().trim();
            String correctAnswer = (String) answerCombo.getSelectedItem();
            
            // Validation
            if (questionText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter question text!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (optionA.isEmpty() || optionB.isEmpty()) {
                JOptionPane.showMessageDialog(this, "At least option A and B must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Database'e kaydet
            saveQuestionToDatabase(questionText, optionA, optionB, optionC, optionD, correctAnswer);
            
            // Clear fields for next question
            questionField.setText("");
            for (int i = 0; i < 4; i++) {
                optionFields[i].setText("");
            }
            
            currentQuestion++;
            
            if (currentQuestion < questionCount) {
                // Next question
                progressLabel.setText("Question " + (currentQuestion + 1) + " of " + questionCount);
                setTitle("Add Questions - " + examTitle + " (" + (currentQuestion + 1) + "/" + questionCount + ")");
            } else {
                // All questions saved
                JOptionPane.showMessageDialog(this, 
                    "✅ All questions saved successfully!\n" +
                    "Exam is ready for students.",
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }
        
        private void saveQuestionToDatabase(String questionText, String a, String b, String c, String d, String correct) {
            Connection conn = null;
            try {
                conn = DBConnection.getConnection();
                if (conn == null) return;
                
                String sql = "INSERT INTO questions (exam_id, question_text, option_a, option_b, option_c, option_d, correct_answer) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, examId);
                stmt.setString(2, questionText);
                stmt.setString(3, a);
                stmt.setString(4, b);
                stmt.setString(5, c);
                stmt.setString(6, d);
                stmt.setString(7, correct);
                stmt.executeUpdate();
                
                System.out.println("✅ Question saved for exam ID: " + examId);
                
            } catch (SQLException e) {
                System.out.println("❌ Error saving question: " + e.getMessage());
                JOptionPane.showMessageDialog(this, 
                    "Error saving question: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

