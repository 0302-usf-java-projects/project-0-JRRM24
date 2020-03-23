package com.revature.model;

public class Account {
	private double balance; 
	private int accountNumber; 
	
	public Account(double balance, int accountNumber) {
		this.balance = balance; 
		this.accountNumber = accountNumber; 
	}
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getAccountNumber() {
		return this.accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

}
