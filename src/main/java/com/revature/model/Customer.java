package com.revature.model;
import java.util.*; 
import com.revature.exception.*; 
public class Customer  {
	private String username; 
	private String password; 
	public boolean loggedin = false; 
	private static int requiredPassWordLength = 8; 
	private HashMap<Integer, Account> accounts = new HashMap<Integer, Account>(); 
	
	public Customer(String username, String password) throws PasswordTooShortException, UsernameAlreadyTakenException {
		this.setPassword(password);
		this.setUsername(username);
		this.loggedin = true;
	}
	
	public Customer() {
		this.loggedin = false; 
	}
	
	public String getUsername() {
		return this.username; 
	}
	
	private void setUsername(String username) {
		this.username = username;
	}
	
	private void setPassword(String password) throws PasswordTooShortException {
		if (password.length() < Customer.requiredPassWordLength) {
			throw new PasswordTooShortException(); 
		}
		this.password = password;
	}
	
	 public boolean authenticate(String username, String password) {
		    return this.username.equals(username) && this.password.equals(password);
		  }
	
	 public Account getAccount(int accountNumber) {
		 return accounts.get(accountNumber);
	 }
	 
	 public HashMap<Integer, Account> getAccounts() {
		 return this.accounts; 
		 
	 }
	 
	 public void setAccounts (HashMap<Integer, Account> accounts) {
		 this.accounts = accounts;
	 }
	 
	 public boolean accountExists(int num) {
		 return accounts.containsKey(num);
	 }
	
	 public void addAccount(int accountNumber, Account account) {
		 accounts.put(accountNumber, account);
	 }
	
	
}
