/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.dashboard;

import edutrackapp.progress.ProgressForm;
import edutrackapp.progress.ProgressTracker;
import edutrackapp.userauthorization.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author dhali, egeme
 */
public class StudentDashboard extends JFrame {

    private static final ProgressTracker PROGRESS_TRACKER = new ProgressTracker();
    private final User loggedUser;
    private final TimeTable timeTable;

    public StudentDashboard(User loggedUser, TimeTable timeTable) {
        this.loggedUser = loggedUser;
        this.timeTable = timeTable;
        setupUI();
    }      
    

    private void setupUI() {
        setTitle("Timetable - " + loggedUser);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JLabel header = new JLabel("Timetable for " + loggedUser, SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(header, BorderLayout.NORTH);

        // weekly grid panel (simple)
        JPanel gridPanel = new JPanel(new GridLayout(2, 5, 6, 6));
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (String d : days) {
            JLabel l = new JLabel(d, SwingConstants.CENTER);
            l.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            gridPanel.add(l);
        }
        for (String d : days) {
            JTextArea area = new JTextArea(getTextForDay(d));
            area.setEditable(false);
            area.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            gridPanel.add(new JScrollPane(area));
        }
        add(gridPanel, BorderLayout.CENTER);

        // bottom: close button
        JButton close = new JButton("Close");
        close.addActionListener(e -> dispose());
        JPanel bottom = new JPanel();
        bottom.add(close);
        add(bottom, BorderLayout.SOUTH);
    }

    private String getTextForDay(String day) {
        StringBuilder sb = new StringBuilder();
        for (ClassSession s : timeTable.getSessions()) {
            if (s.getDay().equalsIgnoreCase(day)) {
                sb.append(s.getTopic()).append(" ").append(s.getStartTime()).append("-").append(s.getEndTime());
                sb.append(" (").append(s.getSessionType()).append(")\n");
            }
        }
        return sb.length() == 0 ? "-" : sb.toString();
    }
}
