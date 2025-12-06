/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.progress;

/**
 *
 * @author dhali
 */
public class Student {
    private String name;
    private double progress;
    private int rank;
    
    public Student (String name){
        this.name = name;     
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public double getProgress() {
        return progress;
    }

    public int getRank() {
        return rank;
    }
    }
