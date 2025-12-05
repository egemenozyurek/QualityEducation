/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.userauthorization;

/**
 *
 * @author evan02
 */
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import edutrackapp.dashboard.StudentDashboard;
import edutrackapp.dashboard.TeacherDashboard;
import edutrackapp.dashboard.TimeTable;

/**
 * Login screen UI with account creation option.
 */
public class LoginScreen extends JFrame implements ActionListener {
       
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCreate;
    private final ProfileManager profileM;
    private User loggedUser;

    public LoginScreen() {
        profileM = new ProfileManager();
        profileM.createProfile("Test", "test@example.com", "123456", "student");
        setupUI();
    }

    private void setupUI() {
        setTitle("Edutrack Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // keep Evan's background
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

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(18);
        styleField(txtEmail);

        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(18);
        styleField(txtPassword);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblEmail, gbc);
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);

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

        // Assemble all panels
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

    public boolean validateLogin() {
    String email = txtEmail.getText();
    String password = new String(txtPassword.getPassword());
    return profileM.login(email, password) != null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        System.out.println("Login denemesi: " + email);
        
        // 1. Login yap
        loggedUser = profileM.login(email, password);
        
        if (loggedUser != null) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            
            // DEBUG: Database'deki son durumu göster
            profileM.getCurrentUser();
            
            startApp();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Invalid credentials. Please try again.\n" +
                "Test credentials:\n" +
                "Email: test@example.com\n" +
                "Password: 123456");
        }
    }

    public void showError() {
        JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
    }

    public void startApp() {
        if (loggedUser == null) {
            JOptionPane.showMessageDialog(this, "No user logged in!");
            return;
        }
        
        System.out.println("🚀 Dashboard is opening: " + 
                          loggedUser.getName() + " (" + loggedUser.getRole() + ")");
        
        // Dispose login screen first
        dispose();
        
        // Open appropriate dashboard
        SwingUtilities.invokeLater(() -> {
            if (loggedUser.getRole().equalsIgnoreCase("student")) {
                System.out.println("Opening Student Dashboard...");
                StudentDashboard studentDash = new StudentDashboard(loggedUser);
                studentDash.setVisible(true);
            } else {
                System.out.println("Opening Teacher Dashboard...");
                TeacherDashboard teacherDash = new TeacherDashboard(loggedUser);
                teacherDash.setVisible(true);
            }
        });
    }

    /**
     * Opens a dialog to create a new user account.
     */
    private void showCreateAccountDialog() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();
        String[] roles = {"student", "teacher"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        JPanel form = new JPanel(new GridLayout(0, 1));
        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Email:"));
        form.add(emailField);
        form.add(new JLabel("Password:"));
        form.add(passField);
        form.add(new JLabel("Role:"));
        form.add(roleBox);

        int result = JOptionPane.showConfirmDialog(this, form, "Create New Account",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                profileM.createProfile(name, email, password, role);
                JOptionPane.showMessageDialog(this, "Account created successfully");
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required.");
            }
        }
    }
}


