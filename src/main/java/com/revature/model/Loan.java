package com.revature.model;

public class Loan {
	private int accountNumber;
	private double remainingBalance;
	private boolean pending;
	private String reason;
	public int getAccountNumber() {
		return accountNumber;
	}
	public Loan(int accountNumber, double remainingBalance, boolean pending, String reason) {
		super();
		this.accountNumber = accountNumber;
		this.remainingBalance = remainingBalance;
		this.pending = pending;
		this.reason = reason;
	}
	
	public String getReason() {
		return this.reason;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getRemainingBalance() {
		return remainingBalance;
	}
	public void setRemainingBalance(double remainingBalance) {
		this.remainingBalance = remainingBalance;
	}
	public boolean isPending() {
		return pending;
	}
	public void setPending(boolean pending) {
		this.pending = pending;
	}
	
	public String toString() {
		String result = new String("Account Number: " + this.accountNumber + " Balance: " + this.remainingBalance);
		result += (" Reason for loan request: " + reason);
		return result;
	}
	
	
	}

