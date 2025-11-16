/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edutrack;

/**
 *
 * @author egeme
 */
interface IEditable {
    void addSession(TimeTable timeTable, ClassSession session);
    void modifySession(TimeTable timeTable, ClassSession oldSession, ClassSession newSession);
    void removeSession(TimeTable timeTable, ClassSession session);
}
