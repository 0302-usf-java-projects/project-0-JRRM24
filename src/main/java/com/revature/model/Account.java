package com.revature.model;
import com.revature.exception.*;
public class Account {
	private String username;
	private String password;
	private int accountNumber; 
	private double balance;
	public static int minimumPasswordSize = 8;

	
	public Account(String userName, String password, double initialDeposit, int accountNumber) throws PasswordTooShortException {
		this.setUserName(userName);
		this.setPassword(password);
		this.balance = initialDeposit;
		this.accountNumber = accountNumber;
	}
	public Account() {
		
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
	private void setPassword(String password) throws PasswordTooShortException {
		if (password.length() < Account.minimumPasswordSize) {
			throw new PasswordTooShortException();
		}
		this.password = password;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
}
