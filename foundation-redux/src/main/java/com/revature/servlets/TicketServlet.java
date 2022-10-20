package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Ticket;
import com.revature.services.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class TicketServlet extends PatchServlet {
    private final ObjectMapper mapper;

    public TicketServlet(ObjectMapper mapper){
        this.mapper = mapper;
    }
    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - TicketServlet Instantiated!");
    }
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse res){
        //get methods::
        //get ALL tickets
        //get tickets by USER
        //get tickets by STATUS
    }
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException {
        //CREATE ticket
        System.out.println("[LOG] - TicketServlet received a POST request at " + LocalDateTime.now());
        HashMap newTicket = mapper.readValue(req.getInputStream(), HashMap.class); //I NEED: USER_ID, REASON, AMOUNT from client!

        TicketService ts = new TicketService();
        String error;
        Ticket created = ts.create(newTicket); //WHAT IF instead, we returned a getTicketById, which returned the user's ticket, as in that long ass SQL we wrote?
                                              //TODO tomorrow. Today, we will get MVP.

        if (created == null){
            error = "Unable to create your ticket, please try again.";
            res.setContentType("application/json");
            res.setStatus(400);
            res.getWriter().write(error);
        } else {
            String respPayload = mapper.writeValueAsString(created);
            res.setContentType("application/json");
            res.setStatus(201);
            res.getWriter().write(respPayload);
        }
    }


    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //DOCUMENTATION: https://technology.amis.nl/software-development/java/handle-http-patch-request-with-java-servlet/
        //for how I did this. I do not want to have to update entire record, so I am hacking a bit here. Let's hope this works...
        res.getWriter().write("I am proof that the patch request hack is at least communicating with servlet.");

        //I take both a ticket id (int), and an update string which will update the record matching that id.
    }
}
