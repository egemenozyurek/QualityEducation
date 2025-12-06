/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.dashboard;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author egeme
 */
public class TimeTable {
    //demonstrates ArrayList of objects
    private final List<ClassSession> sessions = new ArrayList<>();

    public TimeTable() {
    }
    
    public void addSession(ClassSession s) {
        sessions.add(s);
    }
    
    public void removeSession(ClassSession s) {
        sessions.remove(s);
    }
    
    public boolean removeSessionByTopic(String topic) {
        ClassSession found = null;
        for (ClassSession s : sessions) {
            if (s.getTopic().equalsIgnoreCase(topic)) {
                found = s;
                break;
            }
        }
        if (found != null) {
                sessions.remove(found);
                return true;
            }
            return false;
    }
    
    public List<ClassSession> getSessions() {
        return sessions;
    }
    
    //testing
    public ClassSession findByTopic (String topic) {
        for (ClassSession s : sessions) {
            if (s.getTopic().equalsIgnoreCase(topic)) {
                return s;
            }
        }
        return null;
    }
}
