/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.dashboard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author egeme
 */
public class WeeklyGridPanel extends JPanel{
    private static final String[] DAYS = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
    
    public WeeklyGridPanel(TimeTable table) {
        setLayout(new GridLayout(2, DAYS.length, 6, 6));
        setBorder(BorderFactory.createEmptyBorder(6,6,6,6));

        // header
        for (String d : DAYS) {
            JLabel lbl = new JLabel(d, SwingConstants.CENTER);
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
            lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            add(lbl);
        }

        // content cells
        for (String d : DAYS) {
            JTextArea area = new JTextArea(getTextForDay(table.getSessions(), d));
            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            add(new JScrollPane(area));
        }
    }
    
     private String getTextForDay(List<ClassSession> list, String day) {
        StringBuilder sb = new StringBuilder();
        for (ClassSession s : list) if (s.getDay().equalsIgnoreCase(day)) {
            sb.append(s.getTopic()).append(" ").append(s.getStartTime()).append("-").append(s.getEndTime());
            sb.append(" (").append(s.getSessionType()).append(")\n");
        }
        return sb.length() == 0 ? "-" : sb.toString();
    }
}
