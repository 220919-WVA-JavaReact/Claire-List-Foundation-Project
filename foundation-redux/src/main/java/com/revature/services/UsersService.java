package com.revature.services;

import java.util.Scanner;

import com.revature.models.User;
import com.revature.DAO.UserDAO;

public class UsersService {
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
    public User register(){
        System.out.println("Please enter your FIRST NAME.");
        String first_name = io.nextLine();

        System.out.println("Please enter your LAST NAME.");
        String last_name = io.nextLine();

        System.out.println("Please enter your USERNAME. Must be UNIQUE!");
        String user_name = io.nextLine();

        System.out.println("Please enter your EMAIL.");
        String email = io.nextLine();

        System.out.println("Please enter a new PASSWORD.");
        String password = io.nextLine();

        System.out.println("Please enter your ROLE. 1 for employee, 2 for management.");
        int role_num = Integer.parseInt(io.nextLine()); //Im in love

        User user = ud.createUser(first_name, last_name, user_name, password, email, role_num);




        //next, must check username is unique
        //if (GetUsername = null),,
        //proceed
        //else throw an error!

        //THEN NEXT,
        String fullname = first_name + " " + last_name;
        System.out.println("Your information is: " + fullname + " " + user_name );
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
