/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.servlets;

import com.revature.dao.EmployeeDao;
import com.revature.services.LoginService;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mark
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private LoginService userValidationService = new LoginService();
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("GET, loginServlet");
        System.out.println();
        //request.getSession().invalidate();
        
        System.out.println("current username session is: " + request.getSession().getAttribute("username"));
        
        //this does not work because jsp are treated like servlets.
        //request.getRequestDispatcher("/index.jsp").forward(request, response);
        request.getRequestDispatcher("index.jsp").include(request, response);
        //response.sendRedirect("index.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        System.out.println("POST, loginServlet");
        System.out.println();
        
        boolean isUserValid = userValidationService.isUserValid(username, password);
        
        if(isUserValid) {
            EmployeeDao ed = new EmployeeDao();
            
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("employeeId", ed.getId(username));
            if (ed.isManager(username)) {
                System.out.println("IS A MANGER!!!!!!!!!!!!!!!!");
                System.out.println();

                response.sendRedirect(request.getContextPath() + "/manager-home"); 
            } else {
      
                response.sendRedirect(request.getContextPath() + "/employee-home"); 
            }
            
        } else {
            request.setAttribute("errorMessage", "Invalid Credentials!");
            
            //don't send anywhere because i want the error message to show on current page.
            request.getRequestDispatcher("index.jsp").include(request, response);
            //response.sendRedirect("index.jsp");
        }
        
    }
    
    

  

}
