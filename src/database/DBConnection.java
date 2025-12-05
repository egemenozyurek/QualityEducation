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

    public static Connection getConnection() {
        try {
            // 1. Driver'ı yükle
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Bağlantı bilgileri
            String url = "jdbc:mysql://localhost:3306/edutrack";
            String user = "root";
            String password = "Impala67Kansas."; // XAMPP'ta varsayılan boş
            
            System.out.println("Connecting to Database " + url);
            
            // 3. Bağlantıyı kur
            Connection conn = DriverManager.getConnection(url, user, password);
            
            if (conn != null) {
                System.out.println("Database connection SUCCESSFUL!");
                return conn;
            } else {
                System.out.println("Database conenction FAILED!");
                return null;
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("MySQLc cannot be found");
            System.out.println("Add MySQL Connector/J JAR file to the project");
            System.out.println("   - https://dev.mysql.com/downloads/connector/j/");
            e.printStackTrace();
            return null;
            
        } catch (Exception e) {
            System.out.println("Database conenction error " + e.getMessage());
            System.out.println("Please Check");
            System.out.println("Is MySQL running ?");
            System.out.println("Is edutrack database exist ?");
            System.out.println("Is username/password correct ?");
            return null;
        }
    }
    
    // Test metodu
    public static void testConnection() {
        System.out.println("\n=== DATABASE TEST ===");
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Test SUCCESSFUL! Database IS CONNECTED");
            try {
                conn.close();
            } catch (Exception e) {
                // ignore
            }
        } else {
            System.out.println("Test FAILED! couldnt connected to database");
        }
        System.out.println("====================\n");
    }
}
