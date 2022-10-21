package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.services.TicketService;

import javax.servlet.ServletException;
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

        HashMap newReq = mapper.readValue(req.getInputStream(), HashMap.class); //USE mne to get from body of request.

        HttpSession session = req.getSession(false); //do NOT create a new session, check auth if exists
        if (session != null) {
            User auth = (User) session.getAttribute("auth-user");

            String route = req.getHeader("route");

            TicketService ts;
            String error;
            ts = new TicketService();

            switch (route) {
                case "getstatus":

                    if (auth.getRole_num() != 2){
                        res.setStatus(403); //forbidden?
                        res.getWriter().write("You do not have permission to view this page!");
                        break;
                    }

                    error = "Unrecognized status, please supply 'pending', 'approved', or 'denied' only. \n If you have supplied one of these, perhaps no matching records exist in the database.";
                    String query = (String) newReq.get("status");
                    List<Ticket> view = ts.viewByStatus(query);

                    if (view == null) {
                        res.setStatus(422); //unprocessable entity
                        res.getWriter().write(error);
                    } else {
                        res.setStatus(200);
                        res.setContentType("application/json");
                        String payload = mapper.writeValueAsString(view);
                        res.getWriter().write(payload);
                    }
                    break;

                case "getuser":
                    error = "Unable to find that user's tickets, please try again.";
                    String authUser = auth.getUser_name();

                        List<Ticket> userTix = ts.view(authUser);

                        if (userTix == null) {
                            res.setStatus(422);
                            res.getWriter().write(error);

                        } else {
                            res.setStatus(200);
                            String payload = mapper.writeValueAsString(userTix);
                            res.getWriter().write(payload);
                        }

                    break;
            }
        } else {
            String authError = "You do not have access to view this page.";
            res.setStatus(401);
            res.getWriter().write(authError);
        }
    }
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException {
        //CREATE ticket
          HttpSession session = req.getSession(false); //do NOT create a new session, check auth if exists
        if (session != null){
            User auth = (User) session.getAttribute("auth-user");

            System.out.println("[LOG] - TicketServlet received a POST request at " + LocalDateTime.now());

            HashMap newTicket = mapper.readValue(req.getInputStream(), HashMap.class);
            TicketService ts = new TicketService();
            String error;
            Ticket created = ts.create(newTicket, auth.getUser_id());

            if (created == null){
                error = "Unable to create your ticket, please try again. \n Be sure to enter both a reason and an amount.";
                res.setContentType("application/json");
                res.setStatus(400);
                res.getWriter().write(error);
            } else {
                String respPayload = mapper.writeValueAsString(created);
                res.setContentType("application/json");
                res.setStatus(201);
                res.getWriter().write(respPayload);
            }
        } else {
            String authError = "You do not have access to view this page.";
            res.setStatus(401);
            res.getWriter().write(authError);
        }
    }


    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        TicketService ts = new TicketService();

        HttpSession session = req.getSession(false);
        if (session != null) {
            User auth = (User) session.getAttribute("auth-user");

            if (auth.getRole_num() == 2) {


                HashMap newTicket = mapper.readValue(req.getInputStream(), HashMap.class);
                int tickId = (int) newTicket.get("id");
                String statUpdate = (String) newTicket.get("update");

                String error;
                Ticket updated = ts.updateStatus(tickId, statUpdate);

                if (updated == null) {
                    error = "Unable to update your ticket, please provide an existing ticket ID, and \n either 'approved' or 'denied'. \n Note that you cannot currently change a ticket after it has been processed.";
                    res.setStatus(400);
                    res.getWriter().write(error);
                } else {
                    String respPayload = mapper.writeValueAsString(updated);
                    res.setStatus(200);
                    res.getWriter().write(respPayload);
                }
            } else {
                String authError = "You do not have access to approve or deny tickets.";
                res.setStatus(403);
                res.getWriter().write(authError);
            }
        } else {
            String authError = "You do not have access to view this page.";
            res.setStatus(401);
            res.getWriter().write(authError);
        }
    }
}
