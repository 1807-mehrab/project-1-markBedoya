/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.beans.Ticket;
import com.revature.dao.EmployeeDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.net.aso.t;

/**
 *
 * @author Mark
 */
@WebServlet(name = "UpdateEmployeeServlet", urlPatterns = {"/update-employee-profile"})
public class UpdateEmployeeServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("POST: update-employee-profile"); 
        System.out.println("current username session is: " + request.getSession().getAttribute("username")); 
        System.out.println();
        
        
        // if username is null don't update-employee-profile
        if (request.getSession().getAttribute("username") == null) {
            //redirect to login page because username doesnt exist.
            System.out.println("ERROR update-employee-profile: need to redirect to login page because session is over."); 
            System.out.println();
            response.sendRedirect(request.getContextPath() + "/login"); 
            
        //update-employee-profile    
        } else {
            ObjectMapper mapper = new ObjectMapper();
            EmployeeDao ed = new EmployeeDao();
            Employee ne = mapper.readValue(request.getInputStream(), Employee.class);
            Employee e = ed.getEmployee((String)request.getSession().getAttribute("username"));
       
            //check which values need to be updated. if no change, then keep exsisting info. 
            if (ne.getPhone() != "" || ne.getPhone().equals(null)) {
                //updated new values.
                e.setPhone(ne.getPhone());
            }
            if (ne.getEmail() != "" || ne.getEmail().equals(null)) {
                //updated new values.
                e.setEmail(ne.getEmail());
            }
            if (ne.getAddress() != "" || ne.getAddress().equals(null)) {
                //updated new values.
                e.setAddress(ne.getAddress());
            }
            if (ne.getCity() != "" || ne.getCity().equals(null)) {
                //updated new values.
                e.setCity(ne.getCity());
            }
            if (ne.getState() != "" || ne.getState().equals(null)) {
                //updated new values.
                e.setState(ne.getState());
            }
            if (ne.getCountry() != "" || ne.getCountry().equals(null)) {
                //updated new values.
                e.setCountry(ne.getCountry());
            }
            if (ne.getPostalCode() != "" || ne.getPostalCode().equals(null)) {
                //updated new values.
                e.setPostalCode(ne.getPostalCode());
            }
           
            System.out.println("about to send update employe profile info:");
            System.out.println(e);
            System.out.println();
            
            //update the employee info in the db
            ed.updateEmployee(e);
            
        }
        
        
    }
}
