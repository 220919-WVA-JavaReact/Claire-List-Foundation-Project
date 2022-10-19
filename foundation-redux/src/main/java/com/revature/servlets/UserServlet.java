package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User; //CREATE ME!
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: CHANGE THE FUNCTIONALITY OF THIS GET TO GET USERS INSTEAD!
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
        // POST requests are generally used for the creation of data in an application
        System.out.println("[LOG] - UserServlet received a POST request at " + LocalDateTime.now());

        //create a hashmap to store the values
        HashMap newUser = mapper.readValue(req.getInputStream(), HashMap.class); //current understanding:: "inputstream will take in value passed to it and we can do LOGIC with it." Building...
        // At this point newUser could be sent to a service layer for validation which would then send it to
        // the DAO layer to be created in the DB
        String route = req.getHeader("type"); //req.getRequestURI(); deprecated. Testing... now, if type is 'login' or 'register'...

        UsersService us; //declare here so UserSErvice is available in the below scope!
        String error; //we will set different values to error, depending on fail state of below logic.
        us = new UsersService();

        //refactor the below to be a switch statement with multiple cases.
        //case "/users/register", etc etc
        switch (route) {
            case "register":
            error = "That username and/or email already exist in the register! Please try again."; //but you aren't ARE YOU JAVA????????
            User created = us.register(newUser);
            if (created == null){ //register checks value, and if there is a mtching record in the database, returns null.
                resp.setStatus(403);
                resp.setContentType("application/json");
                resp.getWriter().write(error);
            } else {
                //set response payload to a JSON string
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
                    //do soemthing with this session. Configuring...
                    session.setAttribute("auth-user", loggedIn);
                    resp.setStatus(200);
                    resp.getWriter().write(mapper.writeValueAsString("Welcome, " + loggedIn.getUser_name())); //lets hope this works. In tickets servlet, we will check for the "auth-user" attribute. Continuing...
                    // be sure to set  HttpSession session = req.getSession(false); ,, otherwise it will create a new session, which we do not want :-)
                    //something like:: Employee loggedInEmploy = (Employee) session.getAttribute("auth-user");
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
        super.doDelete(req, resp);
    }
}
