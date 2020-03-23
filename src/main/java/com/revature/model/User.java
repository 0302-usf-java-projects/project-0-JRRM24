package com.revature.model;
/**
 * This class is used to represent the person using the console.
 * It starts off with the user not being logged in (tracked with a boolean) 
 * and then once they log in the user's Account object is associated with it
 * to allow for manipulation
 * @author jordanmorgan
 *
 */
public class User {
	public boolean loggedIn = false; 
	private Account useraccount;
	
	public Account getUserAccount() {
		return this.useraccount;
	}
	
	public void setUseraccount(Account useraccount) {
		this.useraccount = useraccount;
	} 
}
