/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.login;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mark
 */
//@WebFilter(urlPatterns = "/list-todo.do") // addweb filter java ee servlet 
public class LoginRequiredFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        System.out.println("THIS WORKED!");
        System.out.println(httpRequest.getRequestURI());
        chain.doFilter(request, response);
        /*
        if (httpRequest.getSession().getAttribute("username") != null) {
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("/login.do").forward(request, response);
        }
        */
        
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
