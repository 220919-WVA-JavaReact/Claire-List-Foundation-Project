package com.revature.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties; //jproperties refactor

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


        String url;
        String username;
        String password;
//        Properties prop = new Properties();
        try {
//            prop.load(new FileReader("C:\\Users\\Claire\\Desktop\\revature-training\\Foundation-Actual\\foundation-redux\\src\\main\\resources\\application.properties"));
//            url = prop.getProperty("url");
//            username = prop.getProperty("username");
//            password = prop.getProperty("password");

            conn = DriverManager.getConnection(url, username, password);
            System.out.println("GOT connection to the specified database!");
        } catch (SQLException e) {
            System.out.println("Failed connection to the database! :( Try again?");
            e.printStackTrace();
        }

        return conn;
    }
}