/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.servlets;

import com.revature.beans.Ticket;
import com.revature.dao.TicketDao;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintWriter;

/**
 *
 * @author Mark
 */
@WebServlet(name = "ListAllResolvedTicketServlet", urlPatterns = {"/list-all-resolved-tickets"})
public class ListAllResolvedTicketServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("POST: list-all-reslved-tickets"); 
        System.out.println("current username session is: " + request.getSession().getAttribute("username")); 
        System.out.println();
        
        // if username is null don't list tickets
        if (request.getSession().getAttribute("username") == null) {
            //redirect to login page because username doesnt exist.
            System.out.println("ERROR: need to redirect to login page because session is over."); 
            System.out.println();
            response.sendRedirect(request.getContextPath() + "/login"); 
            
        //list all resolved tickets    
        } else {
            System.out.println("All resolved tickets are:");
      
            TicketDao td = new TicketDao();
            ArrayList<Ticket> tickets = td.getAllResolvedTicketsFromAllEmployee();
            for (Ticket t : tickets) {
                System.out.println(t.toString());
            }
            System.out.println();
           
            //put in jsonstring and printWriter out
            PrintWriter out = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(tickets);
            System.out.println("sending this:");
            System.out.println("jsonString");
            System.out.println();
            out.println(jsonString);
            out.close();
        }
        
    }
}
