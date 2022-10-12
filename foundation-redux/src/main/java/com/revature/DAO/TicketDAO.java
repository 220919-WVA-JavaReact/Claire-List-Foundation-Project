package com.revature.DAO;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//call getByUsername, extract the user id, feed that to created_by
//also feed this user's user_name to getAllTickets() (--> manager's thing) !

import com.revature.models.Ticket;
public class TicketDAO implements TicketDAOint {
    @Override
    public Ticket createTicket(String reason, float amount, int created_by) {
        Ticket ticket = new Ticket();
        //User user = new User();
       // int created_by = 31; //change bt GET and SET ? //user.getUser_id()

        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO tickets (created_by, reason, amount) VALUES (?,?,?) RETURNING *;"; //I FOROT THE RETURNING IS NEEDED !!!!!~!!!!!!!!!!!!!!!!!!!!!!
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, created_by);
            stmt.setString(2, reason);
            stmt.setFloat(3, amount);

           // stmt.executeQuery();

//            String select = "SELECT * FROM tickets WHERE created_by = ?;";
//            PreparedStatement ps = conn.prepareStatement(select);
//            ps.setInt(1, created_by);

            ResultSet rs; //not returning query result BECAUSE we are not running a select against the database!

            if ((rs = stmt.executeQuery()) != null){ //lets try this...
                rs.next();

                int ticket_id = rs.getInt("ticket_id");
                int createdBy = rs.getInt("created_by"); //I am causing HEADACHE. Investigating...
                String rson = rs.getString("reason");
                float amnt = rs.getFloat("amount");
                String status = rs.getString("status");

               ticket = new Ticket(ticket_id, createdBy, rson, amnt, status);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Unable to create the ticket, please try again.");
        }

        return ticket;
    }

    @Override
    public List<Ticket> getUserTickets(String user_name) {
        Connection conn = ConnectionUtil.getConnection();
        List<Ticket> tickets = new ArrayList<>();

        try{
            String usSQL = "SELECT users.user_name, tickets.ticket_id, tickets.reason, tickets.amount, tickets.status FROM tickets LEFT JOIN users ON users.user_id = tickets.created_by WHERE users.user_name = ?;";

            PreparedStatement stmt = conn.prepareStatement(usSQL);

            stmt.setString(1, user_name);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) { // MUST iterate, ie while doing this. tickets.add(ticket) BELOW,

                int ticket_id = rs.getInt("ticket_id");
                String reason = rs.getString("reason");
                float amount = Float.parseFloat(rs.getString("amount"));
                String status = rs.getString("status");
                String username = rs.getString("user_name");

                Ticket ticket = new Ticket(ticket_id, reason, amount, status, username);
                tickets.add(ticket);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return tickets; //needed to use WHILE loop so that tickets.add() actually WORKED!
    }


    @Override
    public List<Ticket> getAllTickets(){
        Connection conn = ConnectionUtil.getConnection();
        List<Ticket> tickets = new ArrayList<>();

        try{

            String usSQL = "SELECT users.user_name, tickets.ticket_id, tickets.reason, tickets.amount, tickets.status FROM tickets LEFT JOIN users ON users.user_id = tickets.created_by;";
            PreparedStatement stmt = conn.prepareStatement(usSQL);

            ResultSet rs = stmt.executeQuery(); //

            while (rs.next()) {
                int ticket_id = rs.getInt("ticket_id");
                String reason = rs.getString("reason");
                float amount = Float.parseFloat(rs.getString("amount"));
                String status = rs.getString("status");
                String username = rs.getString("user_name");

                Ticket ticket = new Ticket(ticket_id, reason, amount, status, username);
                tickets.add(ticket);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return tickets;
    }
}
