/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.userauthorization;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import edutrackapp.dashboard.StudentDashboard;
import edutrackapp.dashboard.TeacherDashboard;
import java.sql.SQLException;

/**
 * Reference:
 * Java Swing Documentation  
 * https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html
 */


/**
 *
 * @author evan02, dhali
 */

public class LoginScreen extends JFrame implements ActionListener {
       
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCreate;
    private final UserDataAcess userdata = new UserDataAcess();
    private User currentUser;

    public LoginScreen() {
        setupUI();
    }
    private void setupUI() {
        setTitle("Edutrack Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Welcome message at the top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE); 

        //EduTrack logo 
        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("edutrack_logo.png"));
            logoLabel.setIcon(logoIcon);
        } catch (Exception ex) {
            logoLabel.setText("EduTrack");
            logoLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        }

        JLabel welcomeLabel = new JLabel("Welcome to Edutrack!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel promptLabel = new JLabel("Please log in with your username");
        promptLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        promptLabel.setForeground(Color.DARK_GRAY);
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // build top panel
        topPanel.add(logoLabel);              
        topPanel.add(Box.createVerticalStrut(8));
        topPanel.add(welcomeLabel);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(promptLabel);
        topPanel.add(Box.createVerticalStrut(15));

        // Form layout with labels on the left
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(Color.WHITE);
        txtUsername = new JTextField(18);
        styleField(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.WHITE);
        txtPassword = new JPasswordField(18);
        styleField(txtPassword);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUsername, gbc);
        gbc.gridx = 1;
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblPassword, gbc);
        gbc.gridx = 1;
        formPanel.add(txtPassword, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE); 
        btnLogin = new JButton("Login");
        styleButton(btnLogin);
        btnLogin.addActionListener(this);

        btnCreate = new JButton("Create Account");
        styleButton(btnCreate);
        btnCreate.setBackground(new Color(100, 180, 100));
        btnCreate.addActionListener(e -> showCreateAccountDialog());

        buttonPanel.add(btnLogin);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(btnCreate);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setBorder(new CompoundBorder(
                new LineBorder(Color.GRAY, 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(66, 135, 245));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(button.getBackground(), 1, true));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (validateLogin()) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            launchDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean validateLogin() {
    String username = txtUsername.getText();
    String password = new String(txtPassword.getPassword());
    try {
        currentUser = userdata.login(username, password);
        return currentUser != null;
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Database error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    private void launchDashboard() {
        if (currentUser.getRole().equalsIgnoreCase("student")) {
            new StudentDashboard(currentUser).setVisible(true);
        } else {
            new TeacherDashboard(currentUser).setVisible(true);
        }
        dispose(); 
    }
   
    public void showError() {
        JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
    }
    private void startAppWithCurrentUser() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Login error.");
            return;
        }
        if (currentUser.getRole().equalsIgnoreCase("student")) {
            new StudentDashboard(currentUser).setVisible(true);
            dispose();
        } else {
            new TeacherDashboard(currentUser).setVisible(true);
            dispose();
        }
    }
    private void showCreateAccountDialog() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"student", "teacher"});

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Username:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleBox);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Create New Account", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String username = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String role = String.valueOf(roleBox.getSelectedItem());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Username and Password are required!",
                            "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                userdata.registerUser(username, email, password, role);
                JOptionPane.showMessageDialog(this, "Account Created Successfully!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error creating account: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


