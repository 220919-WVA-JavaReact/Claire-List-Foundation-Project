package com.revature.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TicketServlet extends HttpServlet {
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
    protected void doPatch (HttpServletRequest req, HttpServletResponse resp){
        //UPDATE ticket STATUS here.
    }
}
