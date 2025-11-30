/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.userauthorization;

/**
 *
 * @author evan02
 */
public class Student extends User {
    private int points; 
    
    public Student(String name, String email, String password) {
        super(name, email, password, "Student");
    }
    public int getPoints() {
        return points;
    }
    
    public void Pointsadder (int value){
        points+= value;
    }
    
    @Override
    public String getSummary() {
        return "Student: " + name + ", points: " + points;
    }
}
