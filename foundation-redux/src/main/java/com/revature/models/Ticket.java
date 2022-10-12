package com.revature.models;

import java.util.Objects; //I will override
public class Ticket {
    private int ticket_id;
    private int created_by; //int, relates to user_id (must write a custom request for this!)
    private String reason;
    private float amount;
    private String status;

    private String user;

    public Ticket(int ticket_id, int created_by, String reason, float amount, String status) { //I am used to CREATE a ticket
        this.ticket_id = ticket_id;
        this.created_by = created_by;
        this.reason = reason;
        this.amount = amount;
        this.status = status;
    }

    public Ticket() {
    }


    public Ticket(int ticket_id, String reason, float amount, String status, String user) { //I am used to VIEW TICKETS ie
        this.ticket_id = ticket_id;
        //this.created_by = created_by;
        this.reason = reason;
        this.amount = amount;
        this.status = status;
        this.user = user;
    }


    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Your Ticket: {" +
                "ticket_id=" + ticket_id +
                ", created by='" + user + '\'' +
                ", amount='" + amount + '\'' +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                 '}' + "\n";
    }
}