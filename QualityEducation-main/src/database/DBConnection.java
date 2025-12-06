/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.*;

/**
 *
 * @author egeme
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/edutrack";
    private static final String USER = "root";     
    private static final String PASSWORD = "Debrup@2005";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

