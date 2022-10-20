package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.services.TicketService;
import com.revature.services.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

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
    protected void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException {
        //get methods::
        //get ALL tickets --> header "route": "getall" (AND auth, spec'd below
        //// -> check if role_num = 2 in AUTHORIZATION HEADER, if not, disallow

        //get tickets by USER --> header "type": "getuser" (BODY MUST HAVE USERNAME, below)
        //// -> pass a USERNAME in HEADER

        //get tickets by STATUS --> header "type": "getstatus" (AND AUTH, same as above^)
        //// -> pass a string in HEADER, run query


        String route = req.getHeader("route");

        TicketService ts; //declare here so UserSErvice is available in the below scope!
        String error; //we will set different values to error, depending on fail state of below logic.
        ts = new TicketService();

        switch(route){
            case "getstatus":
                //TODO: ADD ERROR HANDLING -- IF 'view', below, is EMPTY, do something ELSE!
                String query = req.getHeader("status");
                List<Ticket> view = ts.viewByStatus(query);
                res.setStatus(200);
                res.setContentType("application/json");
                String payload = mapper.writeValueAsString(view);
                res.getWriter().write(payload); //Redeploy! I have implemented mapper, above. Testing...
                break;
            case "getuser":
                break;
        }
    }
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException {
        //CREATE ticket
        //TODO: maybe deprecated the below if-else if-else tree, it's a bit messy.
        HttpSession session = req.getSession(false); //do NOT create a new session, check auth if exists
        User auth = (User) session.getAttribute("auth-user"); //let's see if this is working.
        String authError;
        if (auth == null){ //'you are not logged in ie"
            authError = "You must log in to create a ticket.";
            res.setStatus(401);
            res.setContentType("application/json");
            res.getWriter().write(authError);
        } else {
           // User loggedIn = (User) session.getAttribute("auth-user");
            System.out.println("[LOG] - TicketServlet received a POST request at " + LocalDateTime.now());
            HashMap newTicket = mapper.readValue(req.getInputStream(), HashMap.class); //I NEED: USER_ID, REASON, AMOUNT from client!

            TicketService ts = new TicketService();
            String error;
            Ticket created = ts.create(newTicket);
            //TODO: MAKE THIS ACCESSIBLE ONLY TO LOGGED IN USERS!
            //must have a session ie

            if (created == null){
                error = "Unable to create your ticket, please try again. \n Be sure to enter a decimal amount, even for whole numbers (24.00 ie).";
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
    }


    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //DOCUMENTATION: https://technology.amis.nl/software-development/java/handle-http-patch-request-with-java-servlet/
        //for how I did this. I do not want to have to update entire record, so I am hacking a bit here. Let's hope this works...
        res.getWriter().write("I am proof that the patch request hack is at least communicating with servlet.");

        //I take both a ticket id (int), and an update string which will update the record matching that id.
    }
}
