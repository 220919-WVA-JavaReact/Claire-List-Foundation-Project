package com.revature.DAO;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//call getByUsername, extract the user id, feed that to created_by
//also feed this user's user_name to getAllTickets() (--> manager's thing) !

import com.revature.models.Ticket;
public class TicketDAO implements TicketDAOint { // TODO: CREATE GETALLTICKETS(), GETTICKETSBYSTATUS(DENIED, APPROVED)
    @Override
    public Ticket createTicket(String reason, double amount, int created_by) {
        Ticket ticket = new Ticket();

        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO tickets (created_by, reason, amount) VALUES (?,?,?) RETURNING *;"; //Let's see if we can't extract out the info...
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, created_by);
            stmt.setString(2, reason);
            stmt.setDouble(3, amount);

            ResultSet rs;

            if ((rs = stmt.executeQuery()) != null){
                rs.next();

                int ticket_id = rs.getInt("ticket_id");
                int createdBy = rs.getInt("created_by");
                String rson = rs.getString("reason");
                double amnt = rs.getDouble("amount");
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

    public Ticket updateStatus(int id, String update){
        //I take both a ticket id (int), and an update string which will update the record matching that id.
        //"UPDATE tickets SET status = ? WHERE ticket_id = ? RETURNING *;"

        Connection conn = ConnectionUtil.getConnection();
        Ticket ticket = new Ticket();
        try{
            String sql = "UPDATE tickets SET status = ? WHERE ticket_id = ? RETURNING *;";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, update);
            stmt.setInt(2,id);

            ResultSet rs;
            if ((rs = stmt.executeQuery()) != null){
                rs.next();

                int ticket_id = rs.getInt("ticket_id");
                int createdBy = rs.getInt("created_by");
                String rson = rs.getString("reason");
                double amnt = rs.getDouble("amount");
                String status = rs.getString("status");

                ticket = new Ticket(ticket_id, createdBy, rson, amnt, status);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public List<Ticket> getTixByStatus(String status){
        //SELECT users.user_name, tickets.ticket_id, tickets.reason, tickets.amount, tickets.status FROM tickets LEFT JOIN users ON users.user_id = tickets.created_by WHERE tickets.status = ?;
        Connection conn = ConnectionUtil.getConnection();
        List<Ticket> tickets = new ArrayList<>();
        try{

            String usSQL = "SELECT users.user_name, tickets.ticket_id, tickets.reason, tickets.amount, tickets.status FROM tickets LEFT JOIN users ON users.user_id = tickets.created_by WHERE tickets.status = ?;";
            PreparedStatement stmt = conn.prepareStatement(usSQL);
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int ticket_id = rs.getInt("ticket_id");
                String reason = rs.getString("reason");
                float amount = Float.parseFloat(rs.getString("amount"));
                String resStatus = rs.getString("status");
                String username = rs.getString("user_name");

                Ticket ticket = new Ticket(ticket_id, reason, amount, resStatus, username);
                tickets.add(ticket);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return tickets;

    }
}
