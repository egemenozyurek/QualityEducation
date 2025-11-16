/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrack;

/**
 *
 * @author egeme
 */
class ClassSession {
    private String topic;
    private String day;
    private String startTime;
    private String endTime;
    
    private boolean attended;
    private int difficultyLevel;
    
    public ClassSession(String topic, String day, String startTime, String endTime, boolean attended, int difficultyLevel) {
        this(topic, day, startTime, endTime);
    }

    public ClassSession(String topic, String day, String startTime, String endTime) {
        this.topic = topic;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attended = true;
        this.difficultyLevel = 1;
    }
    
    public boolean isChallenging() {
        return difficultyLevel >=4;
    }
    
    public boolean isMissed(){
        return !attended;
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

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    @Override
    public String toString() {
        return topic + " | " + day + " " + startTime + "-" + endTime + 
                " (difficulty=" + difficultyLevel + ", attended=" + attended + ")";
    }
    
    
}
