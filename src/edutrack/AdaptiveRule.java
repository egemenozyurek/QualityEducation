/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrack;

/**
 *
 * @author egeme
 */
class AdaptiveRule {
    public boolean shoulAddRepeat(ClassSession session){
        return session.isChallenging();
    }
    
    public boolean shoulAddMakeup(ClassSession session){
        return session.isMissed();
    }
    
    public ClassSession createRepeatSession(ClassSession session){
        return new ClassSession(
        session.getTopic() + " (REPEAT)",
        "Thursday",
        "15:00",
        "16:00"
        );
    }
    
    public ClassSession createMakeUpSession(ClassSession session){
        return new ClassSession(
        session.getTopic() + " (MAKE-UP",
        "Friday",
        "10:00",
        "11:00"
        );
    }
}
