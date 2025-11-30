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
public class RepeatSessionGenerator implements IAdaptiveScheduler {
    private final AdaptiveRule rule = new AdaptiveRule();

    @Override
    public void applyAdaptiveRules(TimeTable table) {
        List <ClassSession> newOnes = new ArrayList<>();
        
        for (ClassSession s : table.getSessions()) {
            if (rule.shouldCreateRepeat(s)) {
                newOnes.add(rule.makeRepeat(s));
            }
            if (rule.shouldCreateMakeup(s)){
                newOnes.add(rule.makeMakeup(s));
            }
        }
        
        for (ClassSession x: newOnes) {
            table.addSession(x);
        }
    }
    
    
}
