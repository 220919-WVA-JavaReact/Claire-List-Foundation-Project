package com.revature.services;

import com.revature.DAO.TicketDAO;
import com.revature.models.Ticket;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class TicketService {
    TicketDAO td = new TicketDAO();

    public Ticket create(HashMap newTicket){


        String reason = (String) newTicket.get("reason");
        double amount = (double) newTicket.get("amount"); //might have to change to FLOAT again. Why did I not like floats...? (it broke at some point and I don't remember why
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

    public List<Ticket> viewByStatus(String status){
        List<Ticket> tix = td.getTixByStatus(status);
        if (tix.size() == 0){
            return null;
        }
        return tix;
    }
    public Ticket updateStatus(int id, String update) {
        String checkStatus;
        checkStatus = td.getStatustById(id);
        Ticket updated = null;
        if (!checkStatus.equals("pending")) {
            return null;
        } else {
            //check we have good data passed to our update string
            switch (update) {
                case "denied":
                case "approved":
                    updated = td.updateStatus(id, update);
                    if (updated.getTicket_id() == 0) {
                        updated = null;
                    }
                    break;
                case "*":
                    break;
            }
        }
        return updated;
    }
    public List<Ticket> view(String username){

        List<Ticket> allTix = td.getUserTickets(username);
        if ( allTix.size() == 0 ){
            return null;
        } else {
            return allTix;
        }
    }

}



//    public List<Ticket> viewAll(){
//
//        List<Ticket> all = td.getAllTickets();
//
//        System.out.println("Type your role level to continue.");
//        int roleCheck = Integer.parseInt(io.nextLine());
//
//        if(roleCheck == 1){
//            System.out.println("Invalid role, must be a manager to view ALL tickets.");
//            return null;
//        } else if (roleCheck == 2){
//            System.out.println("All users TICKETS: ");
//            System.out.println(all);
//            return all;
//        } else {
//            System.out.println("Unknown ROLE, please try again.");
//            return null;
//        }
//
//    }