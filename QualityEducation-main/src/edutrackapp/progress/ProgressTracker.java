/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.progress;

import java.util.ArrayList;

/**
 *
 * @author dhali
 */
public class ProgressTracker {

    private ArrayList<Student> studentList;
    private int leaderboardPoints;

    public ProgressTracker() {
        studentList = new ArrayList <Student>();
        leaderboardPoints = 0;
    }

    public void updateProgress(Student student, double score) {
        int index = findStudentIndex(student.getName());

        if (index == -1) {
            student.setProgress(score);
            studentList.add(student);
        } else {
            Student s = studentList.get(index);
            s.setProgress(score);
        }

        leaderboardPoints += (int) score;
    }
  
    public void calculateRankings() {
        try {
            // sorting for leaderboard in descending order
            for (int i = 0; i < studentList.size(); i++) {
                for (int j = i + 1; j < studentList.size(); j++) {

                    Student a = studentList.get(i);
                    Student b = studentList.get(j);

                    if (a.getProgress() < b.getProgress()) {
                        // swap
                        studentList.set(i, b);
                        studentList.set(j, a);
                    }
                }
            }

            // assigning ranks for the leaderboard
            for (int i = 0; i < studentList.size(); i++) {
                studentList.get(i).setRank(i + 1);
            }

        } catch (Exception e) {
            System.out.println("Error calculating rankings: " + e.getMessage());
        }
    }

    public void viewLeaderboard() {
        System.out.println("LEADERBOARD");
        for (Student s : studentList) {
            System.out.println(s.getRank() + ". " + s.getName()
                    + " - Progress: " + s.getProgress());
        }
        System.out.println("Total leaderboard points: " + leaderboardPoints);
    }

    public int getStudentRank(String studentName) {
        for (Student s : studentList) {
            if (s.getName().equalsIgnoreCase(studentName)) {
                return s.getRank();
            }
        }
        return -1;
    }

    public int getLeaderboardPoints() {
        return leaderboardPoints;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public int getStudentCount() {
        return studentList.size();
    }

    private int findStudentIndex(String studentName) {
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            if (s.getName().equalsIgnoreCase(studentName)) {
                return i;
            }
        }
        return -1;
    }
}
