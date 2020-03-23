package com.revature.controller;
import com.revature.model.*;
import com.revature.exception.*;
import com.revature.repository.*;
import com.revature.service.*; 
import java.util.*; 
public class MenuRunner {
	Customer user = new Customer(); 
	Scanner sc = new Scanner(System.in); 
	BankServices bs = new BankServices();
	
	public void runMenu() {
		while (!user.loggedin) {
			String input = runWelcomeMenu();
			switch (input) {
			case "1": runAccountCreation();
					break; 
			case "2": runLogIn();
				break; 
				
			case "3": 
				System.out.println("Thank you, please come again.");
				System.exit(0);
				break;
			default:
				System.out.println("I'm sorry, but I didn't understand that input. Please try again.");
			}
		}
			
			
			while (user.loggedin) {
				String input = runMainMenu();
				switch (input) {
				case "1": 
					openNewAccount();
					break;
				case "2":
					depositMoney();
					break;
				case "3":
					withdrawMoney();
					break;
				case "5":
					this.user = new Customer();
					break;
				case "4":
					checkBalance();
					break;
				default:
					System.out.println("I'm sorry, I didn't understand that input. Care to try again?");
				}
			}
			runMenu();
		
		
	}
	
	
	public String runWelcomeMenu() {
		System.out.println("Welcome to the bank. Please select one of the following options.");
		System.out.println("1: Create a new customer account.");
		System.out.println("2: Log in and manage or apply for new bank accounts.");
		System.out.println("3: Exit");
		String input = sc.nextLine(); 
		return input; 
	}
	
	public String runMainMenu() {
		System.out.println("Welcome back to the bank. Please select one of the following options.");
		System.out.println("1: Open a bank new account.");
		System.out.println("2: Deposit money in an existing account.");
		System.out.println("3: Withdraw money from an existing bank account.");
		System.out.println("4: Check the balance of an existing account");
		System.out.println("5: Log out and return to the welcome menu");
		String input = sc.nextLine(); 
		return input; 
	}
	
	public void runAccountCreation() {
		int numTries = 0; 
		while (numTries < 3) {
			System.out.println("Please enter your desired username");
			String username = sc.nextLine(); 
			System.out.println("Please enter your desired password"); 
			String password = sc.nextLine(); 
			
			try {
				bs.makeAccount(username, password);
				break;
			} catch (PasswordTooShortException e) {
				System.out.println("I'm sorry, but passwords must be at least 8 characters long. Please try again.");
				System.out.println("-----------------------------------------");
			} catch (UsernameAlreadyTakenException e) {
				System.out.println("I'm sorry, but that username is already taken. Please try again.");
				System.out.println("-----------------------------------------");
			}
			numTries += 1; 
		}
		
		if (numTries == 3) {
			System.out.println("It seems like you were having some issues. Returning to the menu, please try again.");
			System.out.println("-----------------------------------------");
		} else {
			System.out.println("Thank you, your account has been created. You may now log in.");
			System.out.println("-----------------------------------------");
		}
	}
	
	public void runLogIn() {
		System.out.println("Please enter your username.");
		String username = sc.nextLine();
		System.out.println("Please enter your password");
		String password = sc.nextLine();
		if (bs.attemptLogIn(username, password)) {
			this.user = bs.setCustomer(username);
			System.out.println("Thank you, you are now logged in.");
			System.out.println("-----------------------------------------");
		} else {
			System.out.println("I'm sorry, but your username or password was incorrect. Please try again.");
			System.out.println("-----------------------------------------");
		}
	}
	
	public void openNewAccount() {
		System.out.println("Please enter the amount of money you would like to deposit in your new account.");
		double input = Double.parseDouble(sc.nextLine());
		if (input < 200) {
			System.out.println("I'm sorry, but you must deposit at least $200 to open a new account. Returning to the menu.");
			System.out.println("-----------------------------------------");
		} else {
			this.user = bs.newAccount(this.user, input);
			System.out.println("Your balance is " + input);
			System.out.println("Please remember your account number, you will need it to use your account. Returning to the menu.");
			System.out.println("-----------------------------------------");
		}
	}
	
	public void depositMoney() {
		System.out.println("Enter the account number of the account you would like to deposit money into.");
		int accountNumber = Integer.parseInt(sc.nextLine());
		if (user.accountExists(accountNumber)) {
			System.out.println("Enter the amount of money you wish to deposit.");
			double amount = Double.parseDouble(sc.nextLine());
			if (amount < 0) {System.out.println("You must deposit a positive amount of money, please try again.");
			System.out.println("-----------------------------------------");
				} else {
					this.user = bs.depositMoney(user, accountNumber, amount);	
					System.out.println("Returning to the main menu, you may make another transaction");
					System.out.println("-----------------------------------------");
				}
		} else {
			System.out.println("That account does not exist. Please check your numbers and try again.");
			System.out.println("-----------------------------------------");
		}
		
	}
	
	public void withdrawMoney() {
		try {
			System.out.println("Please enter the account number for the account you wish to withdraw from.");
			int num = Integer.parseInt(sc.nextLine());
			if (user.accountExists(num)) {
				System.out.println("Please enter the amount of money you would like to withdraw.");
				double amount = Double.parseDouble(sc.nextLine());
				this.user = bs.withdrawMoney(user, num, amount);
			} else {
				System.out.println("I'm sorry, but that account does not exist. Please try again.");
				System.out.println("-----------------------------------------");
			}
			
		} catch (BalanceTooLowException e) {
			System.out.println("I'm sorry, but you do not have that much money in your account. Returning to the menu.");
			System.out.println("-----------------------------------------");
		}
	}
	
	public void checkBalance() {
		System.out.println("Please enter the account number for the account you wish to check.");
		int accountNumber = Integer.parseInt(sc.nextLine());
		if (user.accountExists(accountNumber)) {
			bs.printBalance(user, accountNumber);
		} else {
			System.out.println("I'm sorry, but that account does not exist. Please try again.");
			System.out.println("-----------------------------------------");
		}
	}
	
	
}
