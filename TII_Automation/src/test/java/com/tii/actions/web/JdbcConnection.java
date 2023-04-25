package com.tii.actions.web;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcConnection {
 
    public static void main(String[] args) {
        // create a connection to the database
        Connection conn = null;
        try {
            String dbURL = "jdbc:postgresql:IntelliDB?user=postgres&password=Telus@123";
            conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                System.out.println("Connected to database");
            }
 
         // check if table already exists
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rsTables = meta.getTables(null, null, "intelligent", new String[] {"TABLE"});
            if (rsTables.next()) {
                System.out.println("Table already exists");
            } else {
                // create a new table with a column named Bot_ID
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE intelligent (Bot_ID INTEGER)";
                stmt.executeUpdate(sql);
                System.out.println("Table created successfully");
            }

         // read data from the table
            Statement selectStmt = conn.createStatement();
            ResultSet rs = selectStmt.executeQuery("SELECT * FROM intelligent");
            while (rs.next()) {
                int botID = rs.getInt("Bot_ID");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                System.out.println("Bot_ID: " + botID + ", Name: " + name + ", Email: " + email);
            }

            /*// create a new table with a column named Bot_ID
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE intelligent (Bot_ID INTEGER)";
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully");*/
            
 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
