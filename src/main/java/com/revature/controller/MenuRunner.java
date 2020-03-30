package com.revature.controller;
import com.revature.model.*;
import com.revature.Main;
import com.revature.exception.*;
import com.revature.repository.*;
import com.revature.service.*; 
import java.util.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger; 
public class MenuRunner {
	User user = new User(); 
	Scanner sc = new Scanner(System.in); 
	BankServices bs = new BankServices();

	public void runMenu() {
		while (!user.loggedIn) {
			String input = runWelcomeMenu();
			switch (input) {
			case "1": runAccountCreation();
					break; 
			case "2": runLogIn();
				break; 
				
			case "3": 
				System.out.println("Thank you, please come again.");
				sc.close();
				System.exit(0);
				break;
			default:
				System.out.println("I'm sorry, but I didn't understand that input. Please try again.");
			}
		}
			
			
			while (user.loggedIn && !user.account.getAdminStatus()) {
				
				String input = runMainMenu();
				switch (input) {
				case "1": 
					checkBalance();
					break;
				case "2":
					depositMoney();
					break;
				case "3":
					withdrawMoney();
					break;
				case "4":
					transferMoney();
					break;
				case "5":
					user = new User();
					System.out.println("You are now logged out.");
					System.out.println("Returning to the welcome menu.");
					System.out.println("-----------------------------------------");
					break;
				default:
					System.out.println("I'm sorry, I didn't understand that input. Care to try again?");
				}
			}
			
			while (user.loggedIn && user.account.getAdminStatus()) {
				System.out.println("Thank you for logging in, admin.");
				System.out.println("Please select from one of the followig options.");
				System.out.println("1: View pending accounts to approve or reject");
				System.out.println("2: View recent transactions.");
				System.out.println("3: Log Out");
				String input = sc.nextLine();
				switch (input) {
				case "1": approveOrRejectAccount();
						break; 
				case "2": viewTransactions();
					break; 
					
				case "3": 
					user = new User();
					System.out.println("You are now logged out.");
					System.out.println("Returning to the welcome menu.");
					System.out.println("-----------------------------------------");
					break;
				default:
					System.out.println("I'm sorry, but I didn't understand that input. Please try again.");
				}
				
			}
			runMenu();
		
		
	}
	
	public void approveOrRejectAccount() {
		List<Account> pendingAccounts = bs.getPendingAccounts();
		if (pendingAccounts.size() == 0) {
			System.out.println("There are currently no accounts requiring approval.");
		}
		List<Account> result = new ArrayList<>();
		for (Account a: pendingAccounts) {
			String username = a.getUserName();
			String password = a.getPassword();
			double balance = a.getBalance();
			String name = a.getfirstName() + " " + a.getlastName();
			System.out.println("Name: " + name + " Username: " + username + " Password: " + password);
			System.out.println("Initial deposit: " + balance);
			while (true) {
				System.out.println("Would you like to approve this account? Select y for yes, n for no.");
				String input = sc.nextLine();
			if (input.equals("y")) {
				System.out.println("Thank you, I will make the account available.");
				a.pending = false;
				result.add(a);
				break;
			} else if (input.equals("n")) {
				System.out.println("Thank you, the account will not be approved.");
				break;
			} else {
				System.out.println("I'm sorry, I didn't understand that input. Please try again.");
			}
			}
		}
		bs.updateMultipleAccounts(result);
		System.out.println("That concludes the pending business. Returning to the menu.");
		System.out.println("-----------------------------------------");
	}
	
	
	public void viewTransactions() {
		System.out.println("I will now list recent transactions.");
		List<String> transactions = bs.getTransactionHistory();
		for (String e: transactions) {
			System.out.println(e);
		}
		System.out.println("Thank you, returning to the main menu.");
		System.out.println("-----------------------------------------");
	}
	
	public String runWelcomeMenu() {
		System.out.println("Welcome to the bank. Please select one of the following options.");
		System.out.println("1: Create a new account.");
		System.out.println("2: Log in to an existing account.");
		System.out.println("3: Exit");
		String input = sc.nextLine(); 
		return input; 
	}
	
	public String runMainMenu() {
		System.out.println("Welcome back to the bank. Please select one of the following options.");
		System.out.println("1: Check your balance.");
		System.out.println("2: Deposit money.");
		System.out.println("3: Withdraw money.");
		System.out.println("4: Transfer money to a different account.");
		System.out.println("5: Log out and return to the welcome menu.");
		String input = sc.nextLine(); 
		return input; 
	}
	
	public void runAccountCreation() {
		int numTries = 0; 
		int accountNumber = 0;
		String password = null;
		while (numTries < 3) {
			System.out.println("Please enter your desired username");
			String username = sc.nextLine(); 
			
			try {System.out.println("Please enter your desired password"); 
				 password = sc.nextLine(); 
				if (password.length() < 8) {
					throw new PasswordTooShortException();
				}
			} catch (PasswordTooShortException e) {
				System.out.println("I'm sorry, but passwords must be at least 8 characters long. Try again.");
				numTries += 1; 
				continue;
			}
			
			
			System.out.println("Please enter your first name");
			String firstName = sc.nextLine();
			System.out.println("Please enter your last name");
			String lastName = sc.nextLine();
			
			try {
				System.out.println("Please enter the amount of money you would like to deposit in your new account.");
				double amount = 0;
				try {
				 amount = Double.parseDouble(sc.nextLine());
				} catch (Exception e) {
					System.out.println("You must enter an positive amount of money to deposit.");
					System.out.println("Try again");
					System.out.println("-----------------------------------------");
					

					amount = 0;
				}
				
				if (amount < 200) {
					System.out.println("I'm sorry, but you must deposit at least $200 to open a new account.");
					System.out.println("-----------------------------------------");
				} else {
				   accountNumber = bs.makeAccount(username, password, amount, firstName, lastName); 
					System.out.println("Creating a new account with an initial balance of " + amount);

				break;}}
				
			 catch (UsernameAlreadyTakenException e) {
				System.out.println("I'm sorry, but that username is already taken. Please try again.");
				System.out.println("-----------------------------------------");
				

			}
			numTries += 1; 
		}
		
		if (numTries == 3) {
			System.out.println("It seems like you were having some issues. Returning to the menu, please try again.");
			System.out.println("-----------------------------------------");
		} else {
			System.out.println("Thank you, your account has been created and is pending approval.");
			System.out.println("You can check back later to see if your account has been approved");
			System.out.println("Your account number is " + accountNumber + ". Please write it down.");
			System.out.println("-----------------------------------------");
		}
	}
	
	public void runLogIn() {
		System.out.println("Please enter your username.");
		String username = sc.nextLine();
		System.out.println("Please enter your password");
		String password = sc.nextLine();
		Account result = null;
		
		try {
			 result = bs.logIn(username, password);
		} catch (AccountNotApprovedException e) {
			System.out.println("I'm sorry, but your account needs to be approved by an employee before you can use it.");
			System.out.println("Returning to the main menu, please try again later.");
			System.out.println("-----------------------------------------");
			
		}
		
		
		if (result == null) {
			System.out.println("Either your username or password was incorrect. Please try again.");
		} else {	user.loggedIn = true; 
			user.account = result;
			System.out.println("Thank you, you are now logged in. Taking you to the main menu.");
			System.out.println("-----------------------------------------");

		}
	}
	
	public void checkBalance() {
		System.out.println("Your current balance is " +  user.account.getBalance());
		System.out.println("Returning to the main menu.");
		System.out.println("-----------------------------------------");

	}
	
	public void depositMoney() {
		double amount; 
		try {
			System.out.println("Please enter the amount that you would like to deposit.");
			amount = Double.parseDouble(sc.nextLine());
			if (amount > 0) {
				Account temp = user.account;
				temp = bs.deposit(temp, amount);
				user.account = temp;
				System.out.println("Thank you. Your current balance is: " + user.account.getBalance());
				System.out.println("Returning to the main menu");
				System.out.println("-----------------------------------------");
			}
		} catch (Exception e) {
			System.out.println("I didn't understand you. Please enter a numerical amount of money.");
		}
		
	}
	
	public void withdrawMoney() {
		double amount;
		try {
			System.out.println("Please enter the amount that you would like to withdraw.");
			amount = Double.parseDouble(sc.nextLine());
			Account temp = user.account;
			temp = bs.withdraw(temp, amount);
			user.account = temp;
			System.out.println("Thank you. You have withdrawn " + amount + " and your new balnace is " + user.account.getBalance());
			System.out.println("Returning to the main menu.");
			System.out.println("-----------------------------------------");

			
		} catch (BalanceTooLowException e) {
			System.out.println("You must withdraw a positive amount of money that is greater than your current balance.");
			System.out.println("Returning to the main menu. Please try again.");
			System.out.println("-----------------------------------------");
			

		} catch (Exception e) {
			System.out.println("You need to enter an amount of money to make a withdrawal. Please try again.");
		}
	}
	
	public void transferMoney() {
		int accountNumber = 0;
		double amount;
		try { 
			System.out.println("Please give the account number of the account you wish to transfer money to.");
			accountNumber = Integer.parseInt(sc.nextLine()); 
		} catch (Exception e) {
			System.out.println("That isn't a valid account number. Returning to the main menu, please try again.");
			System.out.println("-----------------------------------------");

		}
		
		
		
		try {
			System.out.println("How much money do you wish to transfer?");
			amount = Double.parseDouble(sc.nextLine());
			Account temp = user.account;
			temp = bs.transfer(temp, amount, accountNumber);
			user.account = temp;
			System.out.println("Thank you, you have transfered " + amount + " dollars.");
			System.out.println("Your current balance is " + user.account.getBalance());
			System.out.println("Returning to the main menu");
			System.out.println("-----------------------------------------");

			
		} catch (AccountDoesNotExistException e){
		    System.out.println("That account could not be located.");
		    System.out.println("Returning to the main menu.");
			System.out.println("-----------------------------------------");

		}
		catch (Exception e) {
	
		System.out.println("To transfer money you must specify a positive monetary value below your current balance.");
		System.out.println("Returning to the main menu, please try again.");
			System.out.println("-----------------------------------------");}

		}
		
	}
	



