/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author egeme
 */
class ScheduleManager implements IEditable, IAdaptiveScheduler {
    private AdaptiveRule rule = new AdaptiveRule();

    @Override
    public void addSession(TimeTable timeTable, ClassSession session) {
        timeTable.addSession(session);
    }

    @Override
    public void modifySession(TimeTable timeTable, ClassSession oldSession, ClassSession newSession) {
        timeTable.removeSession(oldSession);
        timeTable.addSession(newSession);
    }

    @Override
    public void removeSession(TimeTable timeTable, ClassSession session) {
        timeTable.removeSession(session);
    }

    @Override
    public void generateAdaptiveSchedule(TimeTable timeTable, Student student) {
        List<ClassSession> extraSessions = new ArrayList<>();
        
        for (ClassSession s: timeTable.getSessions()) {
            if (rule.shoulAddRepeat(s)){
                extraSessions.add(rule.createRepeatSession(s));
            }
            
            if (rule.shoulAddMakeup(s)) {
                extraSessions.add(rule.createMakeUpSession(s));
            }
        }
        
        for (ClassSession e: extraSessions){
            timeTable.addSession(e);
        }
    }
    
    
}
