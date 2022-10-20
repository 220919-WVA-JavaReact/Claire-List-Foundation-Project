package com.revature.services;

import com.revature.DAO.TicketDAO;
import com.revature.models.Ticket;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TicketService {
    TicketDAO td = new TicketDAO();

    Scanner io = new Scanner(System.in);
    //I need:: "user_name" to feed to the below. Can i just do a
    //if user.role_num == 2, get all tix. else, only user's own tickets.
    public Ticket create(HashMap newTicket){

        String reason = (String) newTicket.get("reason");
        double amount = (double) newTicket.get("amount");
        int createdBy = (int) newTicket.get("user_id");

        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);

        Ticket tix = td.createTicket(reason, Double.parseDouble(df.format(amount)), createdBy); //jesus CHRIST JAVA D:<
        if (tix.getCreated_by() == 0){
            return null;
        } else {
            return tix;
        }


    }

    public List<Ticket> view(){ //List<Ticket> view(User user
        System.out.println("Here you can VIEW the tickets.");
        System.out.println("Enter your USERNAME for verification.");
        String user = io.nextLine();

        List<Ticket> allTix = td.getUserTickets(user); //I am creating PROBLEMS at runtime
        System.out.println(allTix); //user is still NULL ??????????????
       // System.out.println("This USER's tickets: \n" + allTix);
        return allTix; //in case we want to DO something with this...
    }

    public List<Ticket> viewAll(){

        List<Ticket> all = td.getAllTickets();

        System.out.println("Type your role level to continue.");
        int roleCheck = Integer.parseInt(io.nextLine());

        if(roleCheck == 1){
            System.out.println("Invalid role, must be a manager to view ALL tickets.");
            return null;
        } else if (roleCheck == 2){
            System.out.println("All users TICKETS: ");
            System.out.println(all);
            return all;
        } else {
            System.out.println("Unknown ROLE, please try again.");
            return null;
        }

    }

    public Ticket updateStatus(int id, String update){
        return null;
        //I call the ticket dao -- td.updateStatus() ie,
        //then return the updated ticket
    }

}
