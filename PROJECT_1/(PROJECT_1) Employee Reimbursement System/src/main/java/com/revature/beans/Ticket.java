/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.beans;

import javax.print.DocFlavor;

/**
 *
 * @author Mark
 */
public class Ticket {
    private int id;
    private String ticketType;
    private String ticketDescription;
    private String ticketStatus;
    private double totalAmount;
    private int employeeId;
    private int managerId;
    private String image;

    public Ticket() {
    }

    
    public Ticket(String ticketType, String ticketDescription, double totalAmount) {
        this.ticketType = ticketType;
        this.ticketDescription = ticketDescription;
        this.totalAmount = totalAmount;
    }

    public Ticket(int id, String ticketType, String ticketDescription, String ticketStatus, double totalAmount, int employeeId) {
        this.id = id;
        this.ticketType = ticketType;
        this.ticketDescription = ticketDescription;
        this.ticketStatus = ticketStatus;
        this.totalAmount = totalAmount;
        this.employeeId = employeeId;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void printImage() {
        System.out.println(this.image);
    }
    
    
    @Override
    public String toString() {
        return "Ticket{" + "id=" + id + ", ticketType=" + ticketType + ", ticketDescription=" + ticketDescription + ", ticketStatus=" + ticketStatus + ", totalAmount=" + totalAmount + ", employeeId=" + employeeId + ", managerId=" + managerId + '}';
    }

    

    
    
   
    
    
}
