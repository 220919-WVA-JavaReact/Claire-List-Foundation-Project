package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.services.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class UserServlet extends HttpServlet {
    private final ObjectMapper mapper;

    public UserServlet(ObjectMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - UserServlet Instantiated!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //CURRENTLY, NOT SUPPORTED.
        System.out.println("[LOG] - UserServlet received a GET request at " + LocalDateTime.now());
        // When we did our POST request before we basically parsed a JSON string into a java object now to
        // do the reverse process

        // Let's emulate a value that may come from some data source
        User someUser = new User(123, "John", "Smithye", "jsmithy", "password", "e@mail.com", 2); //FIX this constructor!

        // We want to convert this Java Object into some sort of JSON string
        String respPayload = mapper.writeValueAsString(someUser);

        resp.setStatus(200); // This is the default return status
        resp.setContentType("application/json");
        resp.getWriter().write(respPayload);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CREATE A USER ON 'REGISTER', LOG IN A USER ON 'LOGIN'
        System.out.println("[LOG] - UserServlet received a POST request at " + LocalDateTime.now());

        //create a hashmap to store the values
        HashMap newUser = mapper.readValue(req.getInputStream(), HashMap.class);
        String route = req.getHeader("route"); // this is the magic line ! :-)

        UsersService us;
        String error;
        us = new UsersService();

        switch (route) {
            case "register":
            error = "That username and/or email already exists in the register! Please try again.";
            User created = us.register(newUser);
            if (created == null){ //register checks value, and if there is a mtching record in the database, returns null.
                resp.setStatus(403);
                resp.setContentType("application/json");
                resp.getWriter().write(error);
            } else {
                //remember to se content type!
                String respPayload = mapper.writeValueAsString(created);
                resp.setStatus(201);
                resp.setContentType("application/json");
                resp.getWriter().write(respPayload);
            }
            break;

            case "login":
                error = "Your credentials do not match the register, please try again.";
                User loggedIn = us.login(newUser); //login returns null if the password and username do not match. So, if (null),

                if (loggedIn == null){
                    resp.setStatus(401); //UNAUTHORIZED
                    resp.setContentType("application/json");
                    resp.getWriter().write(error);
                } else {
                    //create a session, which we will use for all authorization
                    HttpSession session = req.getSession(); //creates a sessoin ie, so we can do something with it
                    session.setAttribute("auth-user", loggedIn);
                    resp.setStatus(200);
                    resp.getWriter().write(mapper.writeValueAsString("Welcome, " + loggedIn.getUser_name()));
                }
                break;
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Send a DELETE request to /users in order to log OUT.
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
            resp.getWriter().write("You have been logged out successfully.");
            resp.setStatus(200);
        }
    }
}
