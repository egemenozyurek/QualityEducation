/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edutrackapp.progress;

import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author dhali
 */
public class LeaderboardDataAccess {
    public void addPointsForUsername(String username, int pointsToAdd) throws SQLException {

        String findUserIdSql = "SELECT id FROM users WHERE username = ?";
        String updatePointsSql = "UPDATE students SET points = points + ? WHERE user_id = ?";

        try (Connection c = DBConnection.getConnection();
            PreparedStatement findPs = c.prepareStatement(findUserIdSql)) {
            findPs.setString(1, username);
            try (ResultSet rs = findPs.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");

                    try (PreparedStatement updatePs = c.prepareStatement(updatePointsSql)) {
                        updatePs.setInt(1, pointsToAdd);
                        updatePs.setInt(2, userId);
                        updatePs.executeUpdate();
                    }
                }
            }
        }
    }
    public List<Student> getLeaderboard() throws SQLException {
        List<Student> list = new ArrayList<Student>();
        String sql = "SELECT u.username, s.points " +
                     "FROM students s JOIN users u ON s.user_id = u.id " +
                     "ORDER BY s.points DESC";
        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                String username = rs.getString("username");
                int points = rs.getInt("points");
                Student s = new Student(username);
                s.setProgress(points); 
                list.add(s);
            }
        }
        return list;
    }
}
