package com.revature.services;

import java.util.HashMap;
import java.util.Scanner;

import com.revature.models.User;
import com.revature.DAO.UserDAO;

public class UsersService {
    //TODO:: CREATE GET ALL USERS METHOD SO WE CAN XOMPARE USERNAMES ON REGISTER, REJECT IF USERNAME IS NOT UNIQUE!
    UserDAO ud = new UserDAO(); //instanceOf the user DAO we created! :-)
    Scanner io = new Scanner(System.in); //"input/output" ie

    public User login(HashMap usrL){ //UPDATE:: we need to return the user's role_num as well.

        //extract the username, password
        String reqUsername = (String) usrL.get("user_name"); //REMEMBER we must CAST using the (Type) notation, here. JAVA. BEHAVE!!!!
        String reqPassword = (String) usrL.get("password");

        // Call the database
        User userLogin = ud.getByUsername(reqUsername);

        //password checking
        if(userLogin.getPassword().equals(reqPassword)){

            userLogin.setUser_name(reqUsername);
            return userLogin; //this SHOULD have role_num (use int role = userLogin.getRoleNum(),, then DO something with taht role in users servlets~! Ie, set this to an attrebute, and then check that attreibute in the tickets servlet!
        } else {
            System.out.println("Invalid Login");
            return null;
        }

    }
    public User register(HashMap usr){

        //get values from above hashmap !

        String reqFirstName = (String) usr.get("first_name");
        String reqLastName = (String) usr.get("last_name");
        String reqUsername = (String) usr.get("user_name");
        String reqEmail = (String) usr.get("email");
        String reqPassword = (String) usr.get("password");
        int reqRolenum = (int) usr.get("role_num");

        User user;
        user = ud.createUser(reqFirstName, reqLastName, reqUsername, reqEmail, reqPassword, reqRolenum);

        if(user.getUser_id() == 0){
            return null;
        } else {
            return user;
        }
    }
}
