/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.dashboard;

import edutrackapp.progress.ProgressForm;
import edutrackapp.progress.ProgressTracker;
import edutrackapp.userauthorization.User;
import edutrackapp.assessment.AssessmentManager;
import edutrackapp.assessment.StudentAssessmentForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author dhali, egeme
 */
public class StudentDashboard extends JFrame {

    public static final ProgressTracker PROGRESS_TRACKER = new ProgressTracker();
    public static final AssessmentManager ASSESSMENT_MANAGER = new AssessmentManager();
    private final User loggedUser;

    // YENİ EKLENEN ALANLAR
    private TimeTable timeTable;
    private WeeklyGridPanel gridPanel;

    public StudentDashboard(User loggedUser) {
        this.loggedUser = loggedUser;
        this.timeTable = new TimeTable();  // YENİ: TimeTable oluştur
        loadStudentSchedule();  // YENİ: Öğrenci programını yükle
        setupUI();
    }

    // YENİ METOD: Öğrenci programını yükle
    private void loadStudentSchedule() {
        // Örnek dersler ekle (gerçek uygulamada database'den gelecek)
        timeTable.addSession(new ClassSession("Mathematics", "Monday", "09:00", "10:30"));
        timeTable.addSession(new ClassSession("Physics", "Tuesday", "11:00", "12:30"));
        timeTable.addSession(new ClassSession("Chemistry", "Wednesday", "14:00", "15:30"));
        timeTable.addSession(new ClassSession("Biology", "Thursday", "10:00", "11:30"));
        timeTable.addSession(new ClassSession("Computer Science", "Friday", "13:00", "14:30"));
    }

    private void setupUI() {
        setTitle("EduTrack - Student Dashboard");
        setSize(800, 600);  // Boyutu büyüttük
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ana panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(20, 26, 40));
        mainPanel.setLayout(new BorderLayout());

        // Welcome label
        JLabel lbl = new JLabel("Welcome, " + loggedUser.getName() + " (Student)",
                SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));

        mainPanel.add(lbl, BorderLayout.NORTH);

        gridPanel = new WeeklyGridPanel(timeTable);
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(20, 26, 40));
        buttonPanel.setLayout(new FlowLayout());

        JButton progressBtn = new JButton("Progress & Quiz");
        progressBtn.setBackground(new Color(40, 46, 60));
        progressBtn.setForeground(Color.WHITE);
        progressBtn.setFocusPainted(false);

        JButton assessBtn = new JButton("Take Assessment");
        assessBtn.setBackground(new Color(40, 46, 60));
        assessBtn.setForeground(Color.WHITE);
        assessBtn.setFocusPainted(false);

        JButton refreshBtn = new JButton("Refresh Schedule");
        refreshBtn.setBackground(new Color(40, 46, 60));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);

        // Action listeners
        progressBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProgressForm form
                        = new ProgressForm(loggedUser.getName(), PROGRESS_TRACKER);
                form.setVisible(true);
            }
        });

        assessBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentAssessmentForm form
                        = new StudentAssessmentForm(loggedUser.getName(), ASSESSMENT_MANAGER);
                form.setVisible(true);
            }
        });

        // YENİ: Refresh butonu action listener
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshSchedule();
            }
        });

        // Butonları panele ekle
        buttonPanel.add(progressBtn);
        buttonPanel.add(assessBtn);
        buttonPanel.add(refreshBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    // YENİ METOD: Programı yenile
    private void refreshSchedule() {
        // TimeTable'ı temizle
        // Not: TimeTable'da clear() metodu yok, bu yüzden yeni bir TimeTable oluştur
        timeTable = new TimeTable();
        loadStudentSchedule();

        // GridPanel'i güncelle
        getContentPane().remove(gridPanel);
        gridPanel = new WeeklyGridPanel(timeTable);
        ((BorderLayout) getContentPane().getLayout()).addLayoutComponent(gridPanel, BorderLayout.CENTER);

        revalidate();
        repaint();

        JOptionPane.showMessageDialog(this, "Schedule refreshed successfully!");
    }

    // YENİ: TimeTable'a erişim için getter (isteğe bağlı)
    public TimeTable getTimeTable() {
        return timeTable;
    }
}
