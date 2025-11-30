/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.dashboard;
import javax.swing.*;
import edutrackapp.userauthorization.Student;
import edutrackapp.userauthorization.Teacher;

/**
 *
 * @author egeme
 */
public class TimeTableModuleDemo {
    public static void main(String[] args) {
        // Create a sample TimeTable and sample sessions (instantiable classes + ArrayList)
        TimeTable timeTable = new TimeTable();
        timeTable.addSession(new ClassSession("Math", "Monday", "09:00", "10:00"));
        timeTable.addSession(new ClassSession("Physics", "Tuesday", "11:00", "12:00"));
        timeTable.addSession(new ClassSession("English", "Wednesday", "13:00", "14:00"));
        
        Student demoStudent = new Student("Egemen Özyürek", "demo@student.com", "student");
        Teacher demoTeacher = new Teacher("Egemen Özyürek", "demo@teacher.com", "password123", "teacher");
        
        // simple role choice dialog (so it can run standalone)
        SwingUtilities.invokeLater(() -> {
            String[] options = {"Student view", "Teacher view"};
            int sel = JOptionPane.showOptionDialog(null,
                    "Open as:",
                    "Timetable demo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            if (sel == 0) {
                StudentDashboard sv = new StudentDashboard(demoStudent, timeTable);
                sv.setVisible(true);
            } else if (sel == 1) {
                TeacherDashboard tv = new TeacherDashboard(demoTeacher, timeTable);
                tv.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}
