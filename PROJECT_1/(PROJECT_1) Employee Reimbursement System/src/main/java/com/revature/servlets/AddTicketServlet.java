/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Ticket;
import com.revature.dao.TicketDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mark
 */
@WebServlet(name = "AddTicketServlet", urlPatterns = {"/add-ticket"})
public class AddTicketServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("POST: add-ticket"); 
        System.out.println("current username session is: " + request.getSession().getAttribute("username")); 
        System.out.println();
        
        
        // if username is null don't add ticket
        if (request.getSession().getAttribute("username") == null) {
            //redirect to login page because username doesnt exist.
            System.out.println("ERROR add-ticket: need to redirect to login page because session is over."); 
            System.out.println();
            response.sendRedirect(request.getContextPath() + "/login"); 
            
        //add ticket    
        } else {
            ObjectMapper mapper = new ObjectMapper();
            TicketDao td = new TicketDao();
            Ticket t = mapper.readValue(request.getInputStream(), Ticket.class);
       
            //grab user id from session
            //add user id to this ticket
            t.setEmployeeId((int)request.getSession().getAttribute("employeeId"));
            System.out.println("about to send this ticket to be updated:");
            System.out.println(t);
            System.out.println();
            
            //add the ticket into the db
            td.addTicket(t);
        }
        
        
    }
}
