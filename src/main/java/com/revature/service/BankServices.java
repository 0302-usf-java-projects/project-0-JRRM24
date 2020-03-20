package com.revature.service;

import com.revature.exception.AccountNameAlreadyTakenException;
import com.revature.exception.BalanceTooLowException;
import com.revature.exception.PasswordTooShortException;
import com.revature.model.Account;
import java.util.Scanner;
import java.util.Set;
import com.revature.model.*;

import com.revature.repository.*;
import java.util.HashSet;
public class BankServices {
	private AccountDAO accountManager = new AccountDAO(); 
	
	public void createAccount() {
		Scanner sc = new Scanner(System.in); 
		int numTries = 0; 
		while (numTries < 3) {
			System.out.println("Please enter your desired username");
			String username = sc.nextLine(); 
			System.out.println("Please enter your desired password"); 
			String password = sc.nextLine(); 
			try {
				Account newAccount = new Account(username, password); 
				this.accountManager.addAccount(newAccount);
				System.out.println("Thank you, your account has been created and you may now log in."); 
				break;
			} catch (PasswordTooShortException e) {
				System.out.println("Passwords must be at least 8 characters long, please try again."); 
			} catch (AccountNameAlreadyTakenException e) {
				System.out.println("That username is already taken. If you already have an account, please log in instead."); 
			}
			numTries += 1; 
		
		}
		if (numTries == 3) {
			System.out.println("It seems like you were having some issues. Returning to the menu, please try again.");
		}
		
	}
	
	public boolean attemptLogIn(String username, String password, User user) {
		Set<Account> accounts = accountManager.getAll(); 
		for (Account a: accounts) {
			if (a.authenticate(username, password)) {
				user.setUseraccount(a); 
				return true; 
			}
		}
		return false; 
	}
	
	public Account withdrawMoney(double input, Account userAccount) throws BalanceTooLowException {
		double balance = userAccount.getBalance();
		if (input > balance) {
			throw new BalanceTooLowException();
		} else {
			userAccount.reduceBalance(input);
			this.accountManager.updateAccount(userAccount);
			return userAccount; 
		}
	}
	
	public Account depositMoney(double input, Account userAccount) {
		 
        userAccount.increaseBalance(input);
     	this.accountManager.updateAccount(userAccount);
     	return userAccount;
	}
	
}
