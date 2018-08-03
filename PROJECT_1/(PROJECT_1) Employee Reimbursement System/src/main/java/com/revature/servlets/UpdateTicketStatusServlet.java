/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Ticket;
import com.revature.dao.TicketDao;
import com.revature.services.LoginService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mark
 */
@WebServlet(name = "UpdateTicketStatusServlet", urlPatterns = {"/update-ticket-status"})
public class UpdateTicketStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("POST: update-ticket-status"); 
        System.out.println("current username session is: " + request.getSession().getAttribute("username")); 
        System.out.println();
        
        
        // if username is null don't add ticket
        if (request.getSession().getAttribute("username") == null) {
            //redirect to login page because username doesnt exist.
            System.out.println("ERROR update-ticket-status: need to redirect to login page because session is over."); 
            System.out.println();
            response.sendRedirect(request.getContextPath() + "/login"); 
            
        //check incoming ticket status change can call the dao to make the change.   
        } else {
            ObjectMapper mapper = new ObjectMapper();
            TicketDao td = new TicketDao();
            
            Ticket ticket = mapper.readValue(request.getInputStream(), Ticket.class);
            System.out.println("Recieved ticket to change status: ");
            System.out.println(ticket);
            System.out.println();
            
            
            if (ticket.getTicketStatus().equals("Accept")) {
                ticket.setManagerId((int)request.getSession().getAttribute("employeeId"));
                System.out.println("about to accept ticket id: " + ticket.getId());
                System.out.println(ticket);
                System.out.println();
                td.acceptTicket(ticket.getId(), ticket.getManagerId());
            } else if (ticket.getTicketStatus().equals("Pending")) {
                System.out.println("about to set pending ticket id: " + ticket.getId());
                System.out.println(ticket);
                System.out.println();
                td.pendingTicket(ticket.getId());
            } else if (ticket.getTicketStatus().equals("Denied")) {
                ticket.setManagerId((int)request.getSession().getAttribute("employeeId"));
                System.out.println("about to decline ticket id: " + ticket.getId());
                System.out.println(ticket);
                System.out.println();
                td.declinedTicket(ticket.getId(), ticket.getManagerId());
            } else {
                System.out.println("ERROR READING NEW TICKET STATUS! ");
                System.out.println();
            }
            
        }
        
    }
}
