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

        String url = "jdbc:postgresql://rev-database2.cfaqpigutsgt.us-west-2.rds.amazonaws.com:5432/postgres"; //"no suitable driver found" error. Investigating...
        //research how to set up env variables on tomcat server! System.getenv("username"); System.getenv("password");
        String username = "postgresrev";
        String password = "meister3321";
        try {
            Class.forName("org.postgresql.Driver"); //...be as explicit about this as we can?
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("GOT connection to the specified database!");
        } catch (SQLException e) {
            System.out.println("Failed connection to the database! :( Try again?");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }
}