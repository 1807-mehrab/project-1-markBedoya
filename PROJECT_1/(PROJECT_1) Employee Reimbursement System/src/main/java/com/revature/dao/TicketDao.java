/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.dao;

import com.revature.beans.Ticket;
import com.revature.util.ConnectionUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class TicketDao {
    
    public ArrayList<Ticket> getAllTicketsFromEmployee(String username) {
		PreparedStatement ps = null;
		Ticket t = null;
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		
		try {
                        Connection conn = ConnectionUtil.getConnection(); 
			String sql = "SELECT * FROM Ticket INNER JOIN Employee ON Ticket.EmployeeId = Employee.employeeid WHERE Employee.employeeusername = ?";
			ps = conn.prepareStatement(sql);
                        ps.setString(1, username);
                        
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("TicketId");
				String ticketType = rs.getString("ticketType");
                                String ticketDescription = rs.getString("ticketDescription");
                                String ticketStatus = rs.getString("ticketStatus");
				double totalAmount = rs.getDouble("totalAmount");
				int employeeId = rs.getInt("EmployeeId");
                                String ticketImage = rs.getString("Image");
				
				t = new Ticket(id, ticketType, ticketDescription, ticketStatus, totalAmount, employeeId);
                                t.setImage(ticketImage);
				tickets.add(t);
			}
			
			rs.close();
			ps.close();
                        conn.close(); //this wroked. ask why?
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return tickets;
    }
     
    public void addTicket(Ticket t) {
		CallableStatement cs = null;
		
		try {
                        Connection conn = ConnectionUtil.getConnection();
			String sql = "{CALL SP_ADD_TICKET(?, ?, ?, ?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, t.getTicketType());
			cs.setString(2, t.getTicketDescription());
			cs.setDouble(3, t.getTotalAmount());
                        cs.setInt(4, t.getEmployeeId());
                        cs.setString(5, t.getImage());
                        
			
			Boolean result = cs.execute(); // need this line to execute
                        //returns false if true i know! crazy
			if (!result) {
				System.out.println("Ticket Added in DAO!");
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
    
    public void deleteTicket(int ticketId) {
		CallableStatement cs = null;
		
		try {
                        Connection conn = ConnectionUtil.getConnection();
			String sql = "{CALL  DELETE FROM TICKET WHERE TICKETID = ?";
			cs = conn.prepareCall(sql);
			cs.setInt(1, ticketId);

			Boolean result = cs.execute(); // need this line to execute
                        //returns false if true i know! crazy
			if (!result) {
				System.out.println("Ticket Deleted in DAO!");
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
    
    public ArrayList<Ticket> getAllPendingTicketsFromAllEmployee() {
		PreparedStatement ps = null;
		Ticket t = null;
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		
		try {
                        Connection conn = ConnectionUtil.getConnection(); 
			String sql = "SELECT * FROM Ticket WHERE TicketStatus = 'Pending'";
			ps = conn.prepareStatement(sql);
                        
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("TicketId");
				String ticketType = rs.getString("ticketType");
                                String ticketDescription = rs.getString("ticketDescription");
                                String ticketStatus = rs.getString("ticketStatus");
				double totalAmount = rs.getDouble("totalAmount");
				int employeeId = rs.getInt("EmployeeId");
                                String ticketImage = rs.getString("Image");
				
				t = new Ticket(id, ticketType, ticketDescription, ticketStatus, totalAmount, employeeId);
                                t.setImage(ticketImage);
				tickets.add(t);
			}
			
			rs.close();
			ps.close();
                        conn.close();
                        //conn.close(); //this wroked. ask why?
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return tickets;
    }
    
    public ArrayList<Ticket> getAllResolvedTicketsFromAllEmployee() {
		PreparedStatement ps = null;
		Ticket t = null;
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		
		try {
                        Connection conn = ConnectionUtil.getConnection(); 
			String sql = "SELECT * FROM Ticket WHERE TicketStatus = 'Accepted' OR TicketStatus = 'Denied'";
			ps = conn.prepareStatement(sql);
                        
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("TicketId");
				String ticketType = rs.getString("ticketType");
                                String ticketDescription = rs.getString("ticketDescription");
                                String ticketStatus = rs.getString("ticketStatus");
				double totalAmount = rs.getDouble("totalAmount");
				int employeeId = rs.getInt("EmployeeId");
                                int managerId = rs.getInt("ManagerId");
                                String ticketImage = rs.getString("Image");
				
				t = new Ticket(id, ticketType, ticketDescription, ticketStatus, totalAmount, employeeId);
                                t.setManagerId(managerId);
                                t.setImage(ticketImage);
				tickets.add(t);
			}
			
			rs.close();
			ps.close();
                        conn.close();
                        //conn.close(); //this wroked. ask why?
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return tickets;
    }
    
    public ArrayList<Ticket> getAllTicketsFromEmployeeId(int employeeIdTemp) {
		PreparedStatement ps = null;
		Ticket t = null;
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		
		try {
                        Connection conn = ConnectionUtil.getConnection(); 
			String sql = "SELECT * FROM Ticket INNER JOIN Employee ON Ticket.EmployeeId = Employee.employeeid WHERE Employee.employeeid = ?";
			ps = conn.prepareStatement(sql);
                        ps.setInt(1, employeeIdTemp);
                        
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("TicketId");
				String ticketType = rs.getString("ticketType");
                                String ticketDescription = rs.getString("ticketDescription");
                                String ticketStatus = rs.getString("ticketStatus");
				double totalAmount = rs.getDouble("totalAmount");
				int employeeId = rs.getInt("EmployeeId");
                                int managerId = rs.getInt("ManagerId");
                                String ticketImage = rs.getString("Image");
				
				t = new Ticket(id, ticketType, ticketDescription, ticketStatus, totalAmount, employeeId);
                                t.setManagerId(managerId);
                                t.setImage(ticketImage);
				tickets.add(t);
			}
			
			rs.close();
			ps.close();
                        conn.close();
                        //conn.close(); //this wroked. ask why?
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return tickets;
    }
    
    public void acceptTicket(int ticketId, int ticketManagerId) {
		CallableStatement cs = null;
		
		try {
                        Connection conn = ConnectionUtil.getConnection();
			String sql = "{CALL SP_Update_Accepted_Ticket(?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, ticketId);
                        cs.setInt(2, ticketManagerId);

			Boolean result = cs.execute(); // need this line to execute
                        //returns false if true i know! crazy
			if (!result) {
				System.out.println("Ticket Accepted in DAO!");
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
    
    public void pendingTicket(int ticketId) {
		CallableStatement cs = null;
		
		try {
                        Connection conn = ConnectionUtil.getConnection();
			String sql = "{CALL SP_Update_Pending_Ticket(?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, ticketId);

			Boolean result = cs.execute(); // need this line to execute
                        //returns false if true i know! crazy
			if (!result) {
				System.out.println("Ticket Pendeing in DAO!");
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
    
    public void declinedTicket(int ticketId, int ticketManagerId) {
		CallableStatement cs = null;
		
		try {
                        Connection conn = ConnectionUtil.getConnection();
			String sql = "{CALL SP_Update_Denied_Ticket(?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, ticketId);
                        cs.setInt(2, ticketManagerId);

			Boolean result = cs.execute(); // need this line to execute
                        //returns false if true i know! crazy
			if (!result) {
				System.out.println("Ticket Denied in DAO!");
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
     
     
     
}
