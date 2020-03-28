package com.revature.model;
import com.revature.exception.*;
public class Account {
	private String username;
	private String password;
	private int accountNumber; 
	private double balance;
	String firstName;
	String lastName;
	private boolean admin_status = false; 
	public boolean pending;

	
	public Account(String userName, String password, double initialDeposit, int accountNumber, String firstName, String lastName)  {
		this.password = password;
		this.setUserName(userName);
		this.balance = initialDeposit;
		this.accountNumber = accountNumber;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Account(String userName, String password, double initialDeposit, int accountNumber, String firstName, String lastName, boolean pending, boolean admin_status)  {
		this.password = password;
		this.setUserName(userName);
		this.balance = initialDeposit;
		this.accountNumber = accountNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pending = pending;
		this.admin_status = admin_status;
	}
	public Account() {
		
	}
	
	public void setpending() {
		this.pending = true;
	}
	public boolean getAdminStatus() {
		return this.admin_status;
	}
	
	public void setAdminStatus() {
		this.admin_status = true;
	}
	
	public String getfirstName() {
		return this.firstName;
	}
	
	public String getlastName() {
		return this.lastName;
	}
	
	 public boolean authenticate(String username, String password) {
		    return this.username.equals(username) && this.password.equals(password);
		  }
	
	public void increaseBalance(double amount) {
		this.balance += amount;
	}
	
	public void decreaseBalance(double amount) {
		this.balance -= amount;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public String getUserName() {
		return username;
	}
	private void setUserName(String userName)  {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}

	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
}
