/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Mark
 */
@MultipartConfig()
@WebServlet(name = "UploadImageServlet", urlPatterns = {"/upload-image"})
public class UploadImageServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("POST: upload-image"); 
        System.out.println("current username session is: " + request.getSession().getAttribute("username")); 
        System.out.println();
        
        
        // if username is null don't upload image
        if (request.getSession().getAttribute("username") == null) {
            //redirect to login page because username doesnt exist.
            System.out.println("ERROR upload-image: need to redirect to login page because session is over."); 
            System.out.println();
            response.sendRedirect(request.getContextPath() + "/login"); 
            
        //upload image   
        } else {
            //get the file chosen by the user
            Part filePart = request.getPart("fileToUpload");

            //get the InputStream to store the file somewhere
            InputStream fileInputStream = filePart.getInputStream();

            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            
            System.out.println(fileInputStream);
            System.out.println();
            System.out.println(fileName);
            System.out.println();
	
            
            /*
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
            */
        }
        
        
    }
}
