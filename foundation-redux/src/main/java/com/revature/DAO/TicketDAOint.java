package com.revature.DAO;

import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface TicketDAOint {
    Ticket createTicket(String reason, float amount, int createdBy); //CHANGE ME TOO!

    List<Ticket> getUserTickets(String user_name); //"I am an employee, I can only see MY tickets". If user_name = user.getUser_name() , allow
                                                                                                    // else, disallow

    List<Ticket> getAllTickets();
}
