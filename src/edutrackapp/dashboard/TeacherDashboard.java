/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.dashboard;

import edutrackapp.userauthorization.User;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author dhali
 */
public class TeacherDashboard extends JFrame {

    private final User loggedUser;

    public TeacherDashboard(User loggedUser) {
        this.loggedUser = loggedUser;
        setupUI();
    }

    private void setupUI() {
        setTitle("EduTrack - Teacher Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 26, 40)); // dark blue-ish
        panel.setLayout(new BorderLayout());

        JLabel lbl = new JLabel(
                "Welcome, " + loggedUser.getName() + " (Teacher)",
                SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));

        panel.add(lbl, BorderLayout.CENTER);
        add(panel);
    }
}
