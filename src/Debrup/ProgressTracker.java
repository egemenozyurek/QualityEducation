/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Debrup;

/**
 *
 * @author dhali
 */
public class ProgressTracker {

    private final  Student[] studentList;
    private int studentCount;

    // Total leaderboard points accumulated by all students
    private int leaderboardPoints;

        public ProgressTracker() {
        studentList = new Student[50];
        studentCount = 0;
        leaderboardPoints = 0;
    }

    public void updateProgress(Student student, double score) {
        int index = findStudentIndex(student.getName());

        if (index == -1 && studentCount < 50) {
            // if new student found then add to list
            studentList[studentCount] = student;
            index = studentCount;
            studentCount++;
        }

        if (index != -1) {
            // update progress for existing student
            studentList[index].setProgress(score);
            leaderboardPoints += (int) score;
        }
    }
    // sorting the leaderboard according to leaderboardPoints
    public void calculateRankings() {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < studentCount - 1; i++) {
                if (studentList[i].getProgress() < studentList[i + 1].getProgress()) {
                    // creating temp Student
                    Student temp = studentList[i];
                    studentList[i] = studentList[i + 1];
                    studentList[i + 1] = temp;
                    swapped = true;
                }
            }
        } while (swapped);

        // assign ranks after sort
        for (int i = 0; i < studentCount; i++) {
            studentList[i].setRank(i + 1);
        }
    }
    public void viewLeaderboard() {
        System.out.println("LEADERBOARD");
        for (int i = 0; i < studentCount; i++) {
            Student s = studentList[i];
            System.out.println(s.getRank() + ". " + s.getName() +" - Progress: " + s.getProgress());
        }
        System.out.println("Total leaderboard points: " + leaderboardPoints);
    }
    public int getStudentRank(String studentName) {
        for (int i = 0; i < studentCount; i++) {
            if (studentList[i].getName().equalsIgnoreCase(studentName)) {
                return studentList[i].getRank();
            }
        }
        return -1;
    }
    public int getLeaderboardPoints() {
        return leaderboardPoints;
    }

    private int findStudentIndex(String studentName) {
        for (int i = 0; i < studentCount; i++) {
            if (studentList[i].getName().equalsIgnoreCase(studentName)) {
                return i;
            }
        }
        return -1;
    }
}