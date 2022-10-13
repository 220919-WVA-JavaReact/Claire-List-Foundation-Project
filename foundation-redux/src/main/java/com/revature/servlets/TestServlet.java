package com.revature.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class TestServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - SanityServlet Instantiated!");
        System.out.println("[LOG] - Init param test-init-key: " + this.getServletConfig().getInitParameter("test-init-key"));
        System.out.println("[LOG] - Context param test-init-key: " + this.getServletContext().getInitParameter("test-context-key"));
    }

    @Override
    public void destroy() {
        System.out.println("[LOG] - SanityServlet Destroyed");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // We'll just some println statements to check some information about the request
        System.out.println("[LOG] - SanityServlet received a request at " + LocalDateTime.now());
        System.out.println("[LOG] - Request URI: " + req.getRequestURI());
        System.out.println("[LOG] - Request Method: " + req.getMethod());
        System.out.println("[LOG] - Request Header, example: " + req.getHeader("example"));
        System.out.println("[LOG] - Request Query String: " + req.getQueryString());

        System.out.println("[LOG] - was filtered? " + req.getAttribute("was-filtered"));

        // We'll formulate a basic response to send back
        // Set the status code
        resp.setStatus(200); // It is 200 by default
        resp.setHeader("Content-type", "text/plain");
        resp.setHeader("example-response-header", "some-example-value");
        resp.getWriter().write("This is the response payload");
    }

}
