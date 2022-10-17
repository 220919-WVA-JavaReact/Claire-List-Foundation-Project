package com.revature.services;

import java.util.Scanner;

import com.revature.models.User;
import com.revature.DAO.UserDAO;

public class UsersService {
    //TODO:: CREATE GET ALL USERS METHOD SO WE CAN XOMPARE USERNAMES ON REGISTER, REJECT IF USERNAME IS NOT UNIQUE!
    UserDAO ud = new UserDAO(); //instanceOf the user DAO we created! :-)
    Scanner io = new Scanner(System.in); //"input/output" ie

    public User login(){
        System.out.println("Please enter your username");
        String username = io.nextLine();
        System.out.println("Please enter your password");
        String password = io.nextLine();

        // Call the database
        User user = ud.getByUsername(username);

        //password checking
        if(user.getPassword().equals(password)){
            System.out.println("Welcome, " + username);
            System.out.println("type 't' to view ticket menu options, or 'l' to logout.");
            //System.out.println(user);

            user.setUser_name(username);
            return user;
        } else {
            System.out.println("Invalid Login");
            return null;
        }

    }
    public User register(User usr){ //I take an object, and register that object in the database!
        //be sure to deprecate console functionality!
//        System.out.println("Please enter your FIRST NAME.");
//        String first_name = io.nextLine();
//
//        System.out.println("Please enter your LAST NAME.");
//        String last_name = io.nextLine();
//
//        System.out.println("Please enter your USERNAME. Must be UNIQUE!");
//        String user_name = io.nextLine();
//
//        System.out.println("Please enter your EMAIL.");
//        String email = io.nextLine();
//
//        System.out.println("Please enter a new PASSWORD.");
//        String password = io.nextLine();
//
//        System.out.println("Please enter your ROLE. 1 for employee, 2 for management.");
//        int role_num = Integer.parseInt(io.nextLine()); //Im in love

        User user = ud.createUser(usr.getFirst_name(), usr.getLast_name(), usr.getUser_name(), usr.getPassword(), usr.getEmail(), usr.getRole_num());

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
