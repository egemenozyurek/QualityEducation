/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrack;

/**
 *
 * @author evan2
 */

//Abstract class representing a generic user.
public abstract class User {
    protected String name;
    protected String email;
    protected String password;
    
    protected String role;

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
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

    public void login() {
        System.out.println(name + " has logged in.");
    }

    public void logout() {
        System.out.println(name + " has logged out.");
    }
}


