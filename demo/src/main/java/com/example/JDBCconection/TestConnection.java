package com.example.JDBCconection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConnection {

    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");

            while (rs.next()) {
                System.out.println("User: " + rs.getString("Username"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
