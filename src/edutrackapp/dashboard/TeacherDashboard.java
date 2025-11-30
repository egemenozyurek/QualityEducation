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
 * @author dhali, egeme
 */
public class TeacherDashboard extends JFrame {

    private final User loggedUser;
    private final TimeTable timeTable;
    private final RepeatSessionGenerator generator = new RepeatSessionGenerator();
    private JTable tableView;
    private WeeklyGridPanel gridPanel;

    public TeacherDashboard(User loggedUser, TimeTable timeTable) {
        this.loggedUser = loggedUser;
        this.timeTable = timeTable;
        
        setupUI();
    }
    
    private void doAdd() {
        String topic = JOptionPane.showInputDialog(this, "Topic?");
        if (topic == null || topic.isBlank()) return;
        String day = JOptionPane.showInputDialog(this, "Day?");
        String start = JOptionPane.showInputDialog(this, "Start time?");
        String end = JOptionPane.showInputDialog(this, "End time?");
        ClassSession s = new ClassSession(topic, day, start, end);
        timeTable.addSession(s);
        refreshUI();
    }

    private void doDelete() {
        String topic = JOptionPane.showInputDialog(this, "Topic to delete?");
        if (topic == null || topic.isBlank()) return;
        boolean ok = timeTable.removeSessionByTopic(topic);
        if (!ok) JOptionPane.showMessageDialog(this, "Topic not found");
        refreshUI();
    }

    private void doMarkDiff() {
        String topic = JOptionPane.showInputDialog(this, "Which topic to mark as challenging?");
        ClassSession s = timeTable.findByTipic(topic);
        if (s != null) { s.setDifficultyLevel(5); JOptionPane.showMessageDialog(this, "Marked challenging"); }
        else JOptionPane.showMessageDialog(this, "Not found");
        refreshUI();
    }

    private void doMarkMissed() {
        String topic = JOptionPane.showInputDialog(this, "Which topic was missed?");
        ClassSession s = timeTable.findByTipic(topic);
        if (s != null) { s.setAttended(false); JOptionPane.showMessageDialog(this, "Marked missed"); }
        else JOptionPane.showMessageDialog(this, "Not found");
        refreshUI();
    }

    private void doAdaptive() {
        generator.applyAdaptiveRules(timeTable);
        JOptionPane.showMessageDialog(this, "Adaptive sessions added (if rules matched).");
        refreshUI();
    }

    private void refreshUI() {
        getContentPane().remove(gridPanel);
        gridPanel = new WeeklyGridPanel(timeTable);
        add(gridPanel, BorderLayout.NORTH);
        tableView.setModel(new TimeTableModel(timeTable.getSessions()));
        revalidate();
        repaint();
    }

    private void setupUI() {
        setTitle("Teacher Dashboard - " + loggedUser);
        setSize(1000,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8,8));

        gridPanel = new WeeklyGridPanel(timeTable);
        add(gridPanel, BorderLayout.NORTH);

        tableView = new JTable(new TimeTableModel(timeTable.getSessions()));
        add(new JScrollPane(tableView), BorderLayout.CENTER);

        JButton addBtn = new JButton("Add Session");
        JButton delBtn = new JButton("Delete by Topic");
        JButton diffBtn = new JButton("Mark Challenging");
        JButton missBtn = new JButton("Mark Missed");
        JButton adaptiveBtn = new JButton("Generate Adaptive Sessions");
        JButton refresh = new JButton("Refresh View");

        addBtn.addActionListener(e -> doAdd());
        delBtn.addActionListener(e -> doDelete());
        diffBtn.addActionListener(e -> doMarkDiff());
        missBtn.addActionListener(e -> doMarkMissed());
        adaptiveBtn.addActionListener(e -> doAdaptive());
        refresh.addActionListener(e -> refreshUI());

        JPanel p = new JPanel();
        p.add(addBtn); p.add(delBtn); p.add(diffBtn); p.add(missBtn); p.add(adaptiveBtn); p.add(refresh);
        add(p, BorderLayout.SOUTH);
    }
}
