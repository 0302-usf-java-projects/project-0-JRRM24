package com.revature.controller;
import com.revature.model.*;
import com.revature.repository.AccountDAO;

import java.util.*;
import com.revature.exception.*;
import com.revature.service.BankServices;
/**
 * This class runs the Menu for the bank. 
 * Each instance has a user object used to determine if someone is logged in
 * a scanner, and a service object that holds the logic for functions like account creation/manipulation 
 * @author jordanmorgan
 *
 */
public class MenuRunner {
	User user = new User();  
	BankServices bs = new BankServices(); 
	Scanner sc = new Scanner(System.in); 
	
	
	public void runMenu() {
		while (!user.loggedIn) {
			String input = runWelcomeMenu(); 
			switch (input) {
			case "1" : 
				runAccountCreation();
				break;
			case "2": 
				runLogIn(); 
				break; 
			case "3":
				System.out.println("Thanks, please come again."); 
				System.exit(0);
			}
		
		
		}	
		boolean runMainMenu = true;
		while (runMainMenu) {
			String input = runMainMenu(); 
			switch (input) {
			case "1": 
				makeDeposit();
				break;
			case "3":
				printAccountBalance();
				break; 
			case "4":
				logOut(); 
				runMainMenu = false; 
				break;
			case "2":
				makeWithdrawal();
				break; 
			default:
				System.out.println("I'm sorry, I didn't understand that. Can you try again?");
				
			}
		}
		
		runMenu();
		
	}
	
	public void logOut() {
		System.out.println("Thank, you will be returned to the welcome menu.");
		System.out.println("---------------------------------");
		this.user = new User(); 
	}
	
	public void runAccountCreation() {
		bs.createAccount(); 
		System.out.println("---------------------");
	}
	
	public void runLogIn() {
		System.out.println("Please enter your username");
		String username = sc.nextLine(); 
		System.out.println("Please enter your password");
		String password = sc.nextLine();
		if (bs.attemptLogIn(username, password, this.user)) {
			System.out.println("You are now logged in, taking you to the main menu."); 
			System.out.println("---------------------");
			this.user.loggedIn = true; 
		} else {
			System.out.println("I couldn't log you in with that username and password. Please try again.");
			System.out.println("---------------------");

		}
	}
	
	public String runWelcomeMenu() {
		System.out.println("Welcome to the bank, please choose from one of the following options:");
		System.out.println("1: Create a new account");
		System.out.println("2: Log in to an existing account");
		System.out.println("3: Quit"); 

		String input = sc.nextLine(); 
		String result; 
		switch (input) {
		case "1" : result = "1";
				break; 
		case "2" : result = "2"; 
				break;
		case "3" : result = "3"; 
				break; 
		default:
			System.out.println("I'm sorry, but that is not an option. Please try again."); 
				result = "0"; 
		}
		return result; 
		
	}
	
	public void makeWithdrawal() {
		System.out.println("Please enter the amount that you would like to withdraw.");
		Double input = Double.parseDouble(sc.nextLine());
		Account userAccount = this.user.getUseraccount(); 
		try  {
			Account updatedAccount = bs.withdrawMoney(input, userAccount);
			this.user.setUseraccount(updatedAccount);
			System.out.println("Thank you. Your current balance is: " + this.user.getUseraccount().getBalance());
			
		} catch (BalanceTooLowException e) {
			System.out.println("I'm sorry, but your balance is too low to withdraw that much money.");
			System.out.println("Please deposit more money and then try again.");
			System.out.println("---------------------");

		}
	}
	
	public void makeDeposit() {
		Account userAccount = this.user.getUseraccount();
		System.out.println("Please enter the amount of money you wish to deposit.");
		double input = Double.parseDouble(sc.nextLine());
		if (input > 0) {
			userAccount = this.bs.depositMoney(input, userAccount);
			this.user.setUseraccount(userAccount);
			System.out.println("Your current balance is: " + userAccount.getBalance());
			System.out.println("You may now make another transaction");
			System.out.println("---------------------");

		} else {
			System.out.println("You must deposit more money to update your balance. Please try again.");
			System.out.println("---------------------");

		}

	}
	
	public void printAccountBalance() {
		String balance = Double.toString(this.user.getUserAccount().getBalance());
		System.out.println("Your current balance is: " + balance);
		System.out.println("---------------------");

	}
	
	public String runMainMenu() {
		System.out.println("Welcome to the main menu. Please select from the following options.");
		System.out.println("1 Make a deposit");
		System.out.println("2 Make a withdrawal");
		System.out.println("3 View your account balance");
		System.out.println("4 Logout to the welcome menu");  
		String input = sc.nextLine();
		String result = "0";
		switch (input) {
		case "1" : 
			result ="1";
			break;
		case "2": 
			result = "2";
			break;
		case "3":
			result = "3"; 
			break; 
		case "4": 
			result = "4";
			break;
		default: 
			System.out.println("I'm sorry, I didn't understand that option. Returning to the menu."); 
			System.out.println("---------------------");

		 
		}
		return result;
	}
	}
	

