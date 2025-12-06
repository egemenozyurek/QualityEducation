/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.dashboard;

/**
 *
 * @author egeme
 */
public class ClassSession {

    private String topic;
    private String day;
    private String startTime;
    private String endTime;
    private int difficultyLevel = 1;
    private boolean attended = true;
    private String sessionType = "Normal"; // Normal | Repeat | MakeUp

    public ClassSession(String topic, String day, String startTime, String endTime) {
        this.topic = topic;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ClassSession(String topic, String day, String startTime, String endTime, String sessionType) {
        this(topic, day, startTime, endTime);
        this.sessionType = sessionType;
    } 
    
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public boolean isChallenging() {
        return difficultyLevel >= 4;
    }

    public boolean isMissed() {
        return !attended;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] %s %s-%s (diff=%d attended=%b)",
                topic, sessionType, day, startTime, endTime, difficultyLevel, attended);
    }

}
