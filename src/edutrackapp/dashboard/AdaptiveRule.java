/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.dashboard;

/**
 *
 * @author egeme
 */
public class AdaptiveRule {
    public boolean shouldCreateRepeat(ClassSession s) {
        return s.isChallenging();
    }
    
    public boolean shouldCreateMakeup(ClassSession s) {
        return s.isMissed();
    }
    
    public ClassSession makeRepeat(ClassSession s){
        return new ClassSession(s.getTopic() + " (REPEAT)", "Thursday", "15:00", "16:00", "REPEAT");        
    }
    
     public ClassSession makeMakeup(ClassSession s) {
        return new ClassSession(s.getTopic() + " (MAKE-UP)", "Friday", "10:00", "11:00", "MAKEUP");
    }
}
