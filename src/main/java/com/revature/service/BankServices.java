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
	
	public Account logIn(String username, String password) throws AccountNotApprovedException {
		Account result = null;
		List<Account> accounts = accountManager.getAccounts();
		for (Account i: accounts) {
			if (i.authenticate(username, password)) {
				result = i;
				if (result.pending == true) {
					throw new AccountNotApprovedException();
				}
			}
		}
		
		return result; 
	}
	
	public Account deposit(Account account, double amount) {
		account.increaseBalance(amount);
		accountManager.updateAccount(account);
		accountManager.insertTransaction(account, "deposit", 1, amount);
		return account;
	}
	
	public Account withdraw(Account account, double amount) throws BalanceTooLowException {
		if (amount > account.getBalance()) {throw new BalanceTooLowException();};
		account.decreaseBalance(amount);
		accountManager.updateAccount(account);
		accountManager.insertTransaction(account, "withdrawal", 1, amount);
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
			Account otherAccount = accountManager.getAccount(accountNumber);
			otherAccount.increaseBalance(amount);
			accountManager.updateAccount(otherAccount);
			account.decreaseBalance(amount);
			accountManager.updateAccount(account);
			accountManager.insertTransaction(account, "transfer", otherAccount.getAccountNumber(), amount);
			return account;
		}
		
	}
	
	public List<Account> getPendingAccounts() {
		List<Account> accounts = accountManager.getAccounts();
		List<Account> result = new ArrayList<Account>();
		for (Account a: accounts) {
			if (a.pending == true) {
				result.add(a);
			}
		}
		return result;
 	}
	
	public List<String> getTransactionHistory() {
		return accountManager.getTransactions();
	}
	
	public void updateMultipleAccounts(List<Account> accounts) {
		for (Account e: accounts) {
			accountManager.updateAccount(e);
		}
	}
	
	public void addLoan(Account account, double amount, String reason) {
		Loan l = new Loan(account.getAccountNumber(), amount, true, reason);
		accountManager.insertLoan(l);
	}
	
	public List<Loan> getPendingLoans() {
		return accountManager.getPendingLoans();
	}
	
	public void updateMultipleLoans(List<Loan> loans) {
		for (Loan l: loans) {
			accountManager.updateLoan(l);
		}
	}
	
	public void updateLoan(int accountnumber) {
		Loan l = accountManager.getLoan(accountnumber);
		accountManager.updateLoan(l);;
	}

	public void createEmployee(String username, String password) {
		int accountNumber = accountManager.getAccounts().size() + 100;
		System.out.println("The account number for the new employee account you just created it " + accountNumber);
		Account employee = new Account(username, password, 0, accountNumber, "Employee", "Employee", true, false); 
		accountManager.createEmployee(employee);
	}
	
	public void fireEmployee(int accountnumber) {
		accountManager.deleteEmployee(accountnumber);
	}
	
}
