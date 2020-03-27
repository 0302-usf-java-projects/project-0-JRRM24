 package com.revature.service;
import com.revature.exception.*;
import com.revature.model.*;
import com.revature.repository.*;
import java.util.*;
public class BankServices {
	public DBDAO accountManager = new DBDAO();
	
	public int makeAccount(String username, String password, double initialDeposit, String FirstName, String LastName) throws UsernameAlreadyTakenException {
		
		HashMap<Integer, Account> accounts = accountManager.getAccounts();
		
		int nextAccountNumber = accounts.keySet().size() + 100;
	
		Account newAccount = new Account(username, password, initialDeposit, nextAccountNumber, FirstName, LastName);
		accountManager.addAccount(nextAccountNumber, newAccount);
		return nextAccountNumber;
	}
	public Account logIn(String username, String password) {
		Account result = null;
		HashMap<Integer, Account> accounts = accountManager.getAccounts();
		Set<Integer> keys = accounts.keySet();
		for (Integer i: keys) {
			Account temporary = accounts.get(i);
			if (temporary.authenticate(username, password)) {
				result = temporary;
			}
		}
		return result; 
	}
	
	public Account deposit(Account account, double amount) {
		account.increaseBalance(amount);
		accountManager.updateAccount(account.getAccountNumber(), account);
		return account;
	}
	
	public Account withdraw(Account account, double amount) throws BalanceTooLowException {
		if (amount > account.getBalance()) {throw new BalanceTooLowException();};
		account.decreaseBalance(amount);
		accountManager.updateAccount(account.getAccountNumber(), account);
		return account;
	}
	
	public Account transfer(Account account, double amount, int accountNumber) throws BalanceTooLowException, AccountDoesNotExistException {
		HashMap<Integer, Account> accounts = accountManager.getAccounts();
		if (amount >= account.getBalance()) {
			throw new BalanceTooLowException();
		}
		
		if (!accounts.containsKey(accountNumber)) {
			throw new AccountDoesNotExistException();
		} else {
			Account otherAccount = accounts.get(accountNumber);
			otherAccount.increaseBalance(amount);
			accountManager.updateAccount(accountNumber, otherAccount);
			account.decreaseBalance(amount);
			accountManager.updateAccount(account.getAccountNumber(),account);
			return account;
		}
		
	}
}
