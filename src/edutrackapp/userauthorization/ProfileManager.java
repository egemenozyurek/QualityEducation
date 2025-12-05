/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.userauthorization;

import java.sql.*;
import database.DBConnection;

/**
 *
 * @author evan02
 */
public class ProfileManager {
    private User[] userList = new User[100];
    private int userCount = 0;
    private User currentUser;
    
    public User createProfile(String name, String email, String password, String role){
        //checking for duplicate email
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
            
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, role);
            stmt.executeUpdate();
            
            // Database'in verdiği ID'yi al
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1);
                User newUser;
                if ("teacher".equalsIgnoreCase(role)) {
                    newUser = new Teacher(name, email, password, role);
                } else {
                    newUser = new Student(name, email, password);
                }
                
                System.out.println("User had been created: " + name + "(ID: " + newId + ")");
                return newUser;
            }
            
        } catch (SQLException e) {
            System.out.println("User creation error: " + e.getMessage());
        }
        return null;
    }
    
    //returning the logged-in user or null if invalid credentials found
    public User login(String email, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT id, name, role FROM users WHERE email = ? AND password = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String role = rs.getString("role");
                
                User user;
                if ("teacher".equalsIgnoreCase(role)) {
                    user = new Teacher(name, email, password, role);
                } else {
                    user = new Student(name, email, password);
                }
                
                currentUser = user;
                System.out.println("Login Successfull: " + name + " (" + role + ")");
                return currentUser;
            }
            
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }
    public void logout() {
        if (currentUser != null) {
            currentUser.logout();
            currentUser = null;
        }
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public User getUserByEmail(String email) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT id, name, password, role FROM users WHERE email = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String role = rs.getString("role");
                
                User user;
                if ("teacher".equalsIgnoreCase(role)) {
                    user = new Teacher(name, email, password, "General");
                } else {
                    user = new Student(name, email, password);
                }
                
                return user;
            }
            
        } catch (SQLException e) {
            System.out.println("User cannot be found: " + e.getMessage());
        }
        return null;
    }
    public int getUserCount() {
        return userCount;
    }  
}
