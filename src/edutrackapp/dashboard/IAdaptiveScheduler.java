/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edutrackapp.dashboard;

/**
 *
 * @author egeme
 */
public interface IAdaptiveScheduler {
    //Apply adaptive scheduling rules to the given timetable.
    //Implementation may add extra sessions (repeats or makeups)
    void applyAdaptiveRules(TimeTable table);
}
