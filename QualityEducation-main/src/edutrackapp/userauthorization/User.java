/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.userauthorization;

/**
 *
 * @author evan2
 */
public abstract class User {
    private int id;
    protected String name;
    protected String email;
    protected String password;
    protected String role; //  "Student" or "Teacher"

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    
    public String getDisplayName() {
        return name + " (" + role + ")";
    }
    
    public void login() {
        System.out.println(getDisplayName() + " has logged in.");
    }

    public void logout() {
        System.out.println(getDisplayName() + " has logged out.");
    }
    
    public abstract String getSummary();
}

