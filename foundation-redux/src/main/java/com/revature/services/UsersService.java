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
    public User register(HashMap usr){ //MAY HAVE TO CHAGNE THIS SO WE PRESERVE ORDER OF VARIABLES PASSED.

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
//              WE HAVE TO FIX THIS! If user is found,
        //User user = ud.getByUsername(reqUsername); //let's test this. Digging...
//        if(user != null){ //'not null' is error. Oooooh Java...
//            System.out.println("USERNAME is taken!");
//            return null;
//        } else {
//            user = ud.createUser(reqFirstName, reqLastName, reqUsername, reqEmail, reqPassword, reqRolenum);
//            return user;
//        }
        User user;
        user = ud.createUser(reqFirstName, reqLastName, reqUsername, reqEmail, reqPassword, reqRolenum);
        //TODO: RETURN DIF ERROR IF USERNAME IS TAKEN, EMAIL IS TAKEN (both will error out)
        Boolean emailTaken; //can I use this since i hvae to return an object? Let's find out!
        String regError;
        if(user.getUser_id() == 0){ //this happens if we pass a user with the same username. Investigaing that this throws correct erorr....
            regError = "Username and Email MUST be unique";
            return null; //set above regError here
        } else {
            return user;
        }
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
