/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.dao;

import com.revature.beans.Employee;
import com.revature.util.ConnectionUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mark
 */
public class EmployeeDao {
    

    public boolean checkUsername(String username) {
		PreparedStatement ps = null;
		List<String> employeeUsernames = new ArrayList<String>();
                boolean checkUsername = false;
		
		try {
                        Connection conn = ConnectionUtil.getConnection(); 
			String sql = "SELECT EmployeeUsername FROM Employee";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String usernameDb = rs.getString("EmployeeUsername");
				employeeUsernames.add(usernameDb);
			}
                        
                        if (employeeUsernames.contains(username)) 
                                checkUsername = true;
			
			rs.close();
			ps.close();
                        conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return checkUsername;
	}
        
    public boolean checkPassword(String password, String username) {
		PreparedStatement ps = null;
		String employeePassword = new String();
                boolean checkPassword = false;
		
		try {
                        Connection conn = ConnectionUtil.getConnection();              
			String sql = "SELECT EmployeePassword FROM Employee WHERE EmployeeUSERNAME = ?";                
			ps = conn.prepareStatement(sql);
                        ps.setString(1, username);
                        
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                          
                            employeePassword = rs.getString("EmployeePassword");
				
			}
			
                        if(employeePassword.equals(password))
                            checkPassword = true;
                        
			rs.close();
			ps.close();
                        conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return checkPassword;
	}
        
    public int getId(String username) {
		PreparedStatement ps = null;
                int employeeId = 0;
                
		try {
                        Connection conn = ConnectionUtil.getConnection();              
			String sql = "SELECT EmployeeID FROM Employee WHERE EmployeeUSERNAME = ?";                
			ps = conn.prepareStatement(sql);
                        ps.setString(1, username);
                        
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                          
                            employeeId = rs.getInt("EmployeeID");
				
                        }
                        
			rs.close();
			ps.close();
                        conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return employeeId;
	}
    
    public Employee getEmployee(String username) {
		PreparedStatement ps = null;
                Employee employee = new Employee();
                
		try {
                        Connection conn = ConnectionUtil.getConnection();              
			String sql = "SELECT * FROM Employee WHERE EmployeeUSERNAME = ?";                
			ps = conn.prepareStatement(sql);
                        ps.setString(1, username);
                        
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
                          
                            employee.setId(rs.getInt("EmployeeID"));
                            employee.setFirstName(rs.getString("firstName"));
                            employee.setLastName(rs.getString("lastName"));
                            employee.setTitle(rs.getString("title"));
                            employee.setAddress(rs.getString("address"));
                            employee.setCity(rs.getString("city"));
                            employee.setState(rs.getString("state"));
                            employee.setCountry(rs.getString("country"));
                            employee.setPostalCode(rs.getString("postalcode"));
                            employee.setPhone(rs.getString("phone"));
                            employee.setEmail(rs.getString("email"));
                            employee.setUsername(rs.getString("employeeUsername"));
				
                        }
                        
			rs.close();
			ps.close();
                        conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return employee;
	}
    
    
    public void updateEmployee(Employee e) {
		CallableStatement cs = null;
		
		try {
                        Connection conn = ConnectionUtil.getConnection();
			String sql = "{CALL SP_Update_Employee(?, ?, ?, ?, ?, ?, ?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, e.getId());
			cs.setString(2, e.getPhone());
			cs.setString(3, e.getEmail());
                        cs.setString(4, e.getAddress());
                        cs.setString(5, e.getCity());
                        cs.setString(6, e.getState());
                        cs.setString(7, e.getCountry());
                        cs.setString(8, e.getPostalCode());
                        
			
			Boolean result = cs.execute(); // need this line to execute
                        //returns false if true i know! crazy
			if (!result) {
				System.out.println("Employee updated in data base from DAO!");
                                System.out.println();
			} else {
				//System.out.println("Failed to create account.");
			}
                        
			
			cs.close();
                        conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    public boolean isManager(String username) {
		PreparedStatement ps = null;
                boolean isManager = false;
                String employeeTitle = "";
		
		try {
                        Connection conn = ConnectionUtil.getConnection(); 
			String sql = "SELECT Title FROM Employee where EmployeeUsername = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, username);
			
                        ResultSet rs = ps.executeQuery();
			while (rs.next()) {
                                
                                System.out.println("Value in the title: " + rs.getString("Title"));
                                System.out.println();
				try {
                                    employeeTitle = rs.getString("Title");
                                    if (employeeTitle.equals("Manager"))  {
                                    isManager = true;
                                    }
                                } catch (NullPointerException e) {
                                    //e.printStackTrace();
                                    System.out.println("Caught null pointer exception in employeeDAO.isManager() ");
                                    System.out.println();
                                }
				
			}
                        
			rs.close();
			ps.close();
                        conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return isManager;
    }
    
    
    public ArrayList<Employee> getAllEmployeeNames() {
        PreparedStatement ps = null;
        Employee e = null;
        ArrayList<Employee> employees = new ArrayList<Employee>();

        try {
                Connection conn = ConnectionUtil.getConnection(); 
                String sql = "SELECT * FROM Employee";
                ps = conn.prepareStatement(sql);
                
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                        int id = rs.getInt("EmployeeId");
                        String firstName = rs.getString("firstName");
                        String lastName = rs.getString("lastName");

                        e = new Employee();
                        e.setId(id);
                        e.setFirstName(firstName);
                        e.setLastName(lastName);
                        employees.add(e);
                }

                rs.close();
                ps.close();
                conn.close();
                //conn.close(); //this wroked. ask why?
        } catch (Exception ex) {
                ex.printStackTrace();
        }

        return employees;
    }
   
        
}
