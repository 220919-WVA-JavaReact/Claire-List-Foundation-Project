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
    protected void doGet (HttpServletRequest req, HttpServletResponse resp){
        //get methods::
        //get ALL tickets
        //get tickets by USER
        //get tickets by STATUS
    }
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp){
        //CREATE ticket
    }


    @Override
    public void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
