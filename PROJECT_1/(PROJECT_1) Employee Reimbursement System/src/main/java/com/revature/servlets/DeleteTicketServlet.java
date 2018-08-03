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
@WebServlet(name = "DeleteTicketServlet", urlPatterns = {"/delete-ticket"})
public class DeleteTicketServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("POST: delete-ticket"); 
        System.out.println("current username session is: " + request.getSession().getAttribute("username")); 
        System.out.println();
        
        
        // if username is null don't add ticket
        if (request.getSession().getAttribute("username") == null) {
            //redirect to login page because username doesnt exist.
            System.out.println("ERROR delete-ticket: need to redirect to login page because session is over."); 
            System.out.println();
            response.sendRedirect(request.getContextPath() + "/login"); 
            
        //delete ticket    
        } else {
            ObjectMapper mapper = new ObjectMapper();
            TicketDao td = new TicketDao();
            
            int ticketId = (int)mapper.readValue(request.getInputStream(), Integer.class);
            System.out.println("about to delete ticket id: " + ticketId);
            System.out.println();

            //delete the ticket in the db
            td.deleteTicket(ticketId);
            
        }
        
    }
}
