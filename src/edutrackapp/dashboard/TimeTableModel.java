/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.dashboard;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author egeme
 */
public class TimeTableModel extends AbstractTableModel {
    private final List<ClassSession> sessions;
    private final String[] cols = {"Topic", "Day", "Start", "End", "Type", "Diff", "Attended"};

    public TimeTableModel(List<ClassSession> sessions) {
        this.sessions = sessions;
    }
    
    @Override
    public int getRowCount() {
        return sessions.size();
    }
    
    @Override
    public int getColumnCount(){
        return cols.length;
    }
    
    @Override
    public String getColumnName(int c) {
        return cols[c];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         ClassSession s = sessions.get(rowIndex);
         return switch (columnIndex) {
             case 0 -> s.getTopic();
            case 1 -> s.getDay();
            case 2 -> s.getStartTime();
            case 3 -> s.getEndTime();
            case 4 -> s.getSessionType();
            case 5 -> s.getDifficultyLevel();
            case 6 -> s.isAttended();
            default -> null;
         };
    }
}
