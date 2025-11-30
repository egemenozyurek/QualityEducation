/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.progress;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author dhali
 */
public class LeaderboardForm extends JFrame {
    private ProgressTracker tracker;
    
    public LeaderboardForm(ProgressTracker tracker) {
        this.tracker = tracker;
        setupUI();
    }

    private void setupUI() {
        setTitle("EduTrack - Leaderboard");
        setSize(520, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(20, 26, 40));
        mainPanel.setBorder(new EmptyBorder(16, 20, 20, 20));
        setContentPane(mainPanel);

        JPanel header = new JPanel();
        header.setBackground(new Color(20, 26, 40));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel logo = createLogoLabel();
        JLabel title = new JLabel("Student Leaderboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(logo);
        header.add(Box.createVerticalStrut(8));
        header.add(title);
        header.add(Box.createVerticalStrut(8));

        mainPanel.add(header, BorderLayout.NORTH);

        String[] columns = {"Rank", "Student Name", "Progress Score"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        tracker.calculateRankings();
        ArrayList<Student> list = tracker.getStudentList();

        for (int i = 0; i < list.size(); i++) {
            Student s = list.get(i);
            Object[] row = {s.getRank(), s.getName(), s.getProgress()};
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setBackground(new Color(30, 36, 50));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(80, 90, 120));
        table.setRowHeight(22);
        table.getTableHeader().setBackground(new Color(40, 46, 60));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(20, 26, 40));

        mainPanel.add(scroll, BorderLayout.CENTER);
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
}