 package com.revature.service;
import com.revature.exception.*;
import com.revature.model.*;
import com.revature.repository.*;
import java.util.*;
public class BankServices {
	public BankDAO accountManager = new BankDAO();
	
	public int makeAccount(String username, String password, double initialDeposit, String FirstName, String LastName) throws UsernameAlreadyTakenException {
		
		List<Account> accounts = accountManager.getAccounts();
		
		int nextAccountNumber = accounts.size() + 100;
	
		Account newAccount = new Account(username, password, initialDeposit, nextAccountNumber, FirstName, LastName);
		accountManager.addAccount(newAccount);
		return nextAccountNumber;
	}
	public Account logIn(String username, String password) {
		Account result = null;
		List<Account> accounts = accountManager.getAccounts();
		for (Account i: accounts) {
			if (i.authenticate(username, password)) {
				result = i;
			}
		}
		return result; 
	}
	
	public Account deposit(Account account, double amount) {
		account.increaseBalance(amount);
		accountManager.updateAccount(account);
		return account;
	}
	
	public Account withdraw(Account account, double amount) throws BalanceTooLowException {
		if (amount > account.getBalance()) {throw new BalanceTooLowException();};
		account.decreaseBalance(amount);
		accountManager.updateAccount(account);
		return account;
	}
	
	public Account transfer(Account account, double amount, int accountNumber) throws BalanceTooLowException, AccountDoesNotExistException {
		List<Account> accounts = accountManager.getAccounts();
		if (amount >= account.getBalance()) {
			throw new BalanceTooLowException();
		}
		boolean exists = false;
		for (Account e: accounts) {
			if (e.getAccountNumber() == accountNumber) {
				exists = true;
			}
		}
		
		if (!exists) {
			throw new AccountDoesNotExistException();
		} else {
			Account otherAccount = accounts.get(accountNumber);
			otherAccount.increaseBalance(amount);
			accountManager.updateAccount(otherAccount);
			account.decreaseBalance(amount);
			accountManager.updateAccount(account);
			return account;
		}
		
	}
}
