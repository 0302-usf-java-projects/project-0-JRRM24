package com.revature.model;

public class User {
	public boolean loggedIn = false; 
	private Account useraccount;
	public Account getUseraccount() {
		return useraccount;
	}
	
	public Account getUserAccount() {
		return this.useraccount;
	}
	
	public void setUseraccount(Account useraccount) {
		this.useraccount = useraccount;
	} 
}
