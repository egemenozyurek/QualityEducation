/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrack;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author egeme
 */
public class TeacherDashboard extends JFrame {

    private User teacher;
    private TimeTable timetable = new TimeTable();
    private ScheduleManager scheduler = new ScheduleManager();
    private JTextArea text;

    public TeacherDashboard(User teacher) {
        this.teacher = teacher;

        setTitle("Teacher Dashboard - " + teacher.getName());
        setSize(520, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        text = new JTextArea();
        text.setEditable(false);

        JButton addBtn = new JButton("Add Session");
        JButton delBtn = new JButton("Delete Session");
        JButton adaptiveBtn = new JButton("Adaptive Schedule");


        JPanel btns = new JPanel();
        btns.add(addBtn);
        btns.add(delBtn);
        btns.add(adaptiveBtn);

        add(new JScrollPane(text), BorderLayout.CENTER);
        add(btns, BorderLayout.SOUTH);
    }
}
