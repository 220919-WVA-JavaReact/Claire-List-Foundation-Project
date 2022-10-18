package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User; //CREATE ME!
import com.revature.services.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;

public class UserServlet extends HttpServlet {
    // TODO what if the other servlets need an ObjectMapper? How do we share this reference across classes?
    private final ObjectMapper mapper;

    public UserServlet(ObjectMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - UserServlet Instantiated!");
        System.out.println("[LOG] - Init param user-servlet-key: " + this.getServletConfig().getInitParameter("user-servlet-key"));
        System.out.println("[LOG] - Init param test-init-key: " + this.getServletConfig().getInitParameter("test-init-key"));
        System.out.println("[LOG] - Context param test-init-key: " + this.getServletContext().getInitParameter("test-context-key"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        //String providedUsername = (String) newUser.get("user_name");
        UsersService us = new UsersService();
        us.register(newUser); //I will have to change this of course. Can 'register' take a hashmap ?

        resp.setStatus(204);


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
