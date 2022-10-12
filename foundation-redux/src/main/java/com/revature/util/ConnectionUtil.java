package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil{

    private static Connection conn = null;

    private ConnectionUtil(){}

    // I check if a connectioh exists an dif so, use it, or create a new connection!
    public static Connection getConnection(){
        try{
            if (conn != null && !conn.isClosed()){
                System.out.println("Use a previously made connection");
                return conn;
            }
        } catch (SQLException e){
            // Print out the stack trace and just return null
            e.printStackTrace();
            return null;
        }

        //setup env variables by ! the 'run' menu and choosing uhhh edit configurations!
        String url = System.getenv("url");
        String username = System.getenv("username");
        String password = System.getenv("password");

        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("GOT connection to the specified database!");
        } catch (SQLException e) {
            System.out.println("Failed connection to the database! :( Try again?");
            e.printStackTrace();
        }

        return conn;
    }
}