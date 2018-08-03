/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.beans.Ticket;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.EmployeeDao;
import java.io.PrintWriter;

/**
 *
 * @author Mark
 */
@WebServlet(name = "ListEmployeeFieldsServlet", urlPatterns = {"/profile"})
public class ListEmployeeFieldsServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Get: Profile Servlet"); 
        System.out.println("current username session is: " + request.getSession().getAttribute("username")); 
        System.out.println();
        
        // if username is null don't get employee
        if (request.getSession().getAttribute("username") == null) {
            //redirect to login page because username doesnt exist.
            System.out.println("ERROR: need to redirect to login page because session is over."); 
            System.out.println();
            response.sendRedirect(request.getContextPath() + "/login"); 
            
        //get employee info    
        } else {
            System.out.println("Your Employee info:");
            EmployeeDao ed = new EmployeeDao();
            Employee e = ed.getEmployee((String)request.getSession().getAttribute("username"));
            System.out.println(e.toString());
            System.out.println();
            
            PrintWriter out = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(e);
            System.out.println("sending this employee:");
            System.out.println(jsonString);
            System.out.println();
            
            out.println(jsonString);
            out.close();
            
        }
        
    }
}
