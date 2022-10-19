package com.revature.services;

import java.util.HashMap;
import java.util.Scanner;

import com.revature.models.User;
import com.revature.DAO.UserDAO;

public class UsersService {
    //TODO:: CREATE GET ALL USERS METHOD SO WE CAN XOMPARE USERNAMES ON REGISTER, REJECT IF USERNAME IS NOT UNIQUE!
    UserDAO ud = new UserDAO(); //instanceOf the user DAO we created! :-)
    Scanner io = new Scanner(System.in); //"input/output" ie

    public User login(HashMap usr){

        //extract the username, password
        String reqUsername = (String) usr.get("user_name"); //REMEMBER we must CAST using the (Type) notation, here. JAVA. BEHAVE!!!!
        String reqPassword = (String) usr.get("password");

        // Call the database
        User user = ud.getByUsername(reqUsername);

        //password checking
        if(user.getPassword().equals(reqPassword)){

            user.setUser_name(reqUsername);
            return user; //instead, can we set some session infos?
        } else {
            System.out.println("Invalid Login");
            return null;
        }

    }
    public User register(HashMap usr){

        //get values from above hashmap !

        String reqFirstName = (String) usr.get("first_name"); //lets dig into why this is apparenly not assigning any values. Investigating...
        String reqLastName = (String) usr.get("last_name");
        String reqUsername = (String) usr.get("user_name");
        String reqEmail = (String) usr.get("email");
        String reqPassword = (String) usr.get("password");
        int reqRolenum = (int) usr.get("role_num");

        //check that username is unique HERE !
        //call getByUsername(), pass in reqUsername
        //if return null, allow user creation below
        //else, reject

        User user = ud.getByUsername(reqUsername); //let's test this. Digging...
        if(user != null){ //'not null' is error. Oooooh Java...
            System.out.println("USERNAME is taken!");
            return null;
        }
        User user = ud.createUser(reqFirstName, reqLastName, reqUsername, reqEmail, reqPassword, reqRolenum);

        return user;
    }

    public int viewTix(User user){
        //I control whether user can see all tickets (role_num == 2) or only their own (rn == 1) !
        int check = user.getRole_num();
        if (check == 1){
            return 1; //'if this method call == 1, display own tickets
        } else if (check == 2){
            return 2;
        }
        return 0;
    }
}
