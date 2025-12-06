/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.progress;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
/**
 * Reference:
 * Java Swing Documentation  
 * https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html
 */

/**
 *
 * @author dhali
 */
public class LeaderboardForm extends JFrame {

    private JTable table;
    private LeaderboardDataAccess leaderboardD = new LeaderboardDataAccess();

    public LeaderboardForm() {
        setupUI();
        loadData();
    }

    private void setupUI() {
        setTitle("EduTrack - Leaderboard");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(20, 26, 40));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // setting up the panel and title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(20, 26, 40));

        JLabel logoLabel = createLogoLabel();
        topPanel.add(logoLabel, BorderLayout.WEST);

        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        topPanel.add(title, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // leaderboard table
        String[] cols = {"Rank", "Student", "Points"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

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
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            logo.setText("EduTrack");
            logo.setForeground(Color.WHITE);
            logo.setFont(new Font("SansSerif", Font.BOLD, 18));
        }
        return logo;
    }
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            List<Student> list = leaderboardD.getLeaderboard();
            int rank = 1;
            for (Student s : list) {
                Object[] row = {rank, s.getName(), (int) s.getProgress()};
                model.addRow(row);
                rank++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading leaderboard: " + ex.getMessage(),
                    "Database error", JOptionPane.ERROR_MESSAGE);
        }
    }
}