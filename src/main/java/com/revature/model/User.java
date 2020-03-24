package com.revature.model;
import java.util.*;
public class User {
	public boolean loggedIn = false; 
	public Account account;
	List<String> transactionHistory = new ArrayList<>();
}
