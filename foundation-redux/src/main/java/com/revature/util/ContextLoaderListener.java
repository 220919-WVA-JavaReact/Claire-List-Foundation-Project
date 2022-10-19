package com.revature.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.servlets.TestServlet;
import com.revature.servlets.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import java.time.LocalDateTime;

    public class ContextLoaderListener implements ServletContextListener {
        //IT LOOKS LIKE I WILL HAVE TO CREATE MULTIPLE SERVLETS, BECAUSE YOU SEEM TO BE UNABLE TO ADD MULTIPLE MAPPINGS TO A SERVLET

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            System.out.println("[LOG] - The servlet context was initialized at " + LocalDateTime.now());

            // We can also programmatically define/register our servlets with the container here

            ObjectMapper mapper = new ObjectMapper();
            UserServlet userServlet = new UserServlet(mapper);

            // Now I need to register it with the context itself
            ServletContext context = sce.getServletContext();
            ServletRegistration.Dynamic registeredServlet = context.addServlet("UserServlet", userServlet);
            // Now I can affect the fields that I wanted to before
            registeredServlet.addMapping("/users");

            registeredServlet.setLoadOnStartup(3);



            //future syntax, creating a servlet (1st line), then adding mapping to servlet, and adding that servlet to context
//        TestServlet exampleServlet = new TestServlet(); //...lets see
//        context.addServlet("ExampleServlet", exampleServlet).addMapping("/examples");

        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            System.out.println("[LOG] - The servlet context was destroyed at " + LocalDateTime.now());
        }
}
