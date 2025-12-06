/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.userauthorization;

import database.DBConnection;
import java.sql.*;

/**
 *
 * @author dhali
 */
public class UserDataAcess {

    public User registerUser(String username, String email, String password, String role) throws SQLException {

        String sqlUser = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, username);
            ps.setString(2, password);   
            ps.setString(3, email);
            ps.setString(4, role);
            ps.executeUpdate();

            int userId = -1;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    userId = rs.getInt(1);
                }
            }
            if (userId != -1) {
                if ("student".equalsIgnoreCase(role)) {
                    insertStudentRow(c, userId);
                } else if ("teacher".equalsIgnoreCase(role)) {
                    insertTeacherRow(c, userId);
                }
            }
            User u = new User(username, email, password, role) {
                public String getSummary() {
                    return "User: " + username + " (" + role + ")";
                }
            };
            u.setId(userId);
            return u;
        }
    }
    private void insertStudentRow(Connection conn, int userId) throws SQLException {
        String sql = "INSERT INTO students (user_id, student_no, grade, points) VALUES (?, ?, ?, 0)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, null);
            ps.setString(3, null);
            ps.executeUpdate();
        }
    }
    private void insertTeacherRow(Connection conn, int userId) throws SQLException {
        String sql = "INSERT INTO teachers (user_id, department, subject) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, null);
            ps.setString(3, null);
            ps.executeUpdate();
        }
    }
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String email = rs.getString("email");
                    String role = rs.getString("role");
                    User u = new User(username, email, password, role) {
                        @Override
                        public String getSummary() {
                            return "User: " + username + " (" + role + ")";
                        }
                    };
                    u.setId(id);
                    return u;
                }
            }
        }
        return null;
    }
}
    