/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.dao.EmployeeDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mark
 */
@WebServlet(name = "GetAllEmployeeNamesServlet", urlPatterns = {"/get-all-employee-names"})
public class GetAllEmployeeNamesServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("POST: get-all-employee-names"); 
        System.out.println("current username session is: " + request.getSession().getAttribute("username")); 
        System.out.println();
        
        
        // if username is null don't get-employee-name
        if (request.getSession().getAttribute("username") == null) {
            //redirect to login page because username doesnt exist.
            System.out.println("ERROR get-all-employee-names: need to redirect to login page because session is over."); 
            System.out.println();
            response.sendRedirect(request.getContextPath() + "/login"); 
            
        //get-employee-name   
        } else {
            EmployeeDao ed = new EmployeeDao();

            //get employee names from db
            ArrayList<Employee> employees = ed.getAllEmployeeNames();
            
            //send emplaoyee names
            PrintWriter out = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(employees);
            System.out.println("sending these employees with their field names:");
            System.out.println(jsonString);
            System.out.println();
            out.println(jsonString);
            out.close();
        }
        
        
    }
}
