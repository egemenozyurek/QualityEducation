/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrack;

/**
 *
 * @author egeme
 */
class Teacher extends User implements IViewable, IEditable {
    public Teacher(String name, String email, String password, String role) {
        super(name, email, password, role);
    }

    public void login(String email, String password) {
        if (getEmail().equals(email) && getPassword().equals(password)) {
            System.out.println("Teacher login successful.");
        } else {
            System.out.println("Teacher login failed.");
        }    }

    @Override
    public void viewTimeTable(TimeTable timetable) {
        System.out.println("----- Teacher Timetable -----");
        for (ClassSession s : timetable.getSessions()) {
            System.out.println(s);
        }
    }

    @Override
    public void addSession(TimeTable timetable, ClassSession session) {
        timetable.addSession(session);
    }

    @Override
    public void removeSession(TimeTable timetable, ClassSession session) {
        timetable.removeSession(session);
    }

    @Override
    public void modifySession(TimeTable timetable, ClassSession oldSession, ClassSession newSession) {
        timetable.removeSession(oldSession);
        timetable.addSession(newSession);
    }
}
