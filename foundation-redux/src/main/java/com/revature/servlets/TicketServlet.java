package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    protected void doPost (HttpServletRequest req, HttpServletResponse res){
        //CREATE ticket
    }


    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //DOCUMENTATION: https://technology.amis.nl/software-development/java/handle-http-patch-request-with-java-servlet/
        //for how I did this. I do not want to have to update entire record, so I am hacking a bit here. Let's hope this works...
        res.getWriter().write("I am proof that the patch request hack is at least communicating with servlet.");
    }
}
