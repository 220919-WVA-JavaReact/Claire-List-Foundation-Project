package com.revature.DAO;

import java.sql.*;

import com.revature.models.User;
import com.revature.util.ConnectionUtil;

import static java.util.Objects.nonNull;

public class UserDAO implements UserDAOint { //need to create a User INTERFACE, then implement here!
    @Override
    public User getByUsername(String user_name){
        //create user object we will get data back to
        User user = new User();

        //connect with resources fed to us !
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM users WHERE user_name = ?";

            //PREPARE it, to prevent SQL injection attacks (scary...)
            PreparedStatement stmt = conn.prepareStatement(sql);

            //EYE set the string to the values
            stmt.setString(1, user_name);
            ResultSet rs;

            if ((rs = stmt.executeQuery()) != null){
                //results not blank? GREAT! we found the user...
                rs.next();

                // Now we can pull the information out and store it in the USER object

                int id = rs.getInt("user_id");
                String first = rs.getString("first_name");
                String last = rs.getString("last_name");
                String recUserN = rs.getString("user_name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                int role = rs.getInt("role_num");


                // NOW we create the user object!
                user = new User(id,first,last,recUserN,password,email,role);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User createUser(String first_name, String last_name, String user_name, String password, String email, int role_num){
        User user = new User(); //new user object to INSERT into the database :-)
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO users (first_name, last_name, user_name, email, password, role_num) VALUES (?,?,?,?,?,?) RETURNING *";

            //...remmber to PREPARE the statement !
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, user_name);
            stmt.setString(4, email);
            stmt.setString(5, password);
            stmt.setInt(6, role_num);

            ResultSet rs;

            String empty = ""; //JAVA! ENOUGH!!!!!!!!!!!!!
            if(user_name.equals(empty)) {
                System.out.println("Must provide the requested info for the record, \n please try again.");
            } else {
                if ((rs = stmt.executeQuery()) != null) {

                    //........ move cursor FORWARD!
                    rs.next();

                    // Obtain values
                    int id = rs.getInt("user_id"); //...resX ie, result or from "response". I miss res.json...
                    String resFirst = rs.getString("first_name");
                    String resLast = rs.getString("last_name");
                    String resUsername = rs.getString("user_name");
                    String resEmail = rs.getString("email");
                    String resPassword = rs.getString("password");
                    int resRole = rs.getInt("role_num");

                    user = new User(id, resFirst, resLast, resUsername, resEmail, resPassword, resRole); //RESOLVE me!
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't register user to the database");
        }

        return user;
    }
}
