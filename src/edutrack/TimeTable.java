/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrack;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author egeme
 */
class TimeTable {
    private List<ClassSession> sessions = new ArrayList<>();
    
    public void addSession(ClassSession s){
        sessions.add(s);
    }
    
    public void removeSession(ClassSession s){
        sessions.remove(s);
    }
    
    public List<ClassSession> getSessions() {
        return sessions;
    }
}
