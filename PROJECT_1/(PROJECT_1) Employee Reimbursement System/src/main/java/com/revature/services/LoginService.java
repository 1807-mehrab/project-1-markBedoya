/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.services;

import com.revature.dao.EmployeeDao;

/**
 *
 * @author Mark
 */
public class LoginService {
    
    public boolean isUserValid(String username, String password) {
        EmployeeDao ed = new EmployeeDao();
        if (ed.checkUsername(username) && ed.checkPassword(password, username)) {
            System.out.println("Login Successful from isUserValid!");
            System.out.println();
            return true;
        } 
        System.out.println("Unsuccessful login from isUserValid!");
        return false;
    }
    
}
