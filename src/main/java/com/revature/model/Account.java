package com.revature.model;

import com.revature.exception.AccountNameAlreadyTakenException;
import com.revature.exception.BalanceTooLowException;
import com.revature.exception.PasswordTooShortException; 
public class Account {
	/**
	 * Represent the user's account. It has a username and a password associated with it, as well as an account balance.
	 */
	private static final int required_password_length = 8; 
	private double balance = 0; 
	private String username; 
	private String password; 
	
	
	public Account(String username, String password) throws PasswordTooShortException, AccountNameAlreadyTakenException {
		this.setUsername(username);
		this.setPassword(password); 
		 
	}
	
	 public boolean authenticate(String username, String password) {
		    return this.username.equals(username) && this.password.equals(password);
		  }
	
	public double getBalance() {
		return this.balance;
	}
	public void reduceBalance(double amount) {
		this.balance -= amount;
	}
	
	private void setBalance(double balance) {
		this.balance = balance; 
	}
	
	public void increaseBalance(double amount) {
		this.balance += amount; 
	}


	public String getUsername() {
		return username;
	}
	private void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	
	private void setPassword(String password) throws PasswordTooShortException {
		if (password.length() < Account.required_password_length) {
			throw new PasswordTooShortException(); 
		}
		this.password = password;
	}
	
	
}
