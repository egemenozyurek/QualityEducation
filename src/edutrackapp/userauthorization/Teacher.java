/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.userauthorization;

/**
 *
 * @author evan02
 */
public class Teacher extends User {
    private String subject;
    
    public Teacher(String name, String email, String password, String subject) {
        super(name, email, password, "Teacher");
        this.subject = subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
    
    @Override
    public String getSummary() {
        return "Teacher: " + name + ", subject: " + subject;
    }
    
}
