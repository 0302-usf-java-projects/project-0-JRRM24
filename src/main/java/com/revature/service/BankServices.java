package com.revature.service;
import com.revature.repository.*;
import java.util.*; 
import com.revature.exception.*;
import com.revature.model.*; 
public class BankServices {
	private static BankServices bs = null; 
	private DAOCustomers accountManager = new DAOCustomers();
	public BankServices() {
		
	}
	
	public static BankServices getBankServices() {
		if (BankServices.bs == null) {
			BankServices.bs = new BankServices(); 
			return BankServices.bs;
		} else {
			return BankServices.bs; 
		}
	}
	
	public void makeAccount(String username, String password) throws UsernameAlreadyTakenException, PasswordTooShortException {
		HashMap customers = accountManager.getAllCustomers();
		if (customers.containsKey(username)) {
			throw new UsernameAlreadyTakenException(); 
		} else {
			Customer newCustomer = new Customer(username, password); 
			customers.put(username, newCustomer);
			accountManager.updateCustomerAccounts(customers);
		}
	}
	
	public boolean attemptLogIn(String username, String password) {
		HashMap<String, Customer> customers = accountManager.getAllCustomers();
		Set<String> keys = customers.keySet();		
		for (String key: keys) {
			Customer customer = customers.get(key);
			if (customer.authenticate(username, password)) {
				return true; 
			}
		
		}
		return false; 
		
	}
	
	public Customer setCustomer(String username) {
		return accountManager.getCustomer(username);
	}
	
	/**
	 * this creates a new bank account to associate with a Customer object 
	 * @param customer
	 * @param deposit
	 * @return
	 */
	public Customer newAccount(Customer customer, double deposit) {
		Random rand = new Random(); 
		HashMap<Integer, Account> accounts = customer.getAccounts();
		while (true) {
			 int randomNumber = rand.nextInt(4000);
			 if (!accounts.containsKey(randomNumber)) {
				 Account newAccount = new Account(deposit, randomNumber);
				 accounts.put(randomNumber, newAccount);
				 customer.setAccounts(accounts);
				 accountManager.updateCustomer(customer);
				 System.out.println("Your account has been created. Your new account number is " + randomNumber);

				 break; 
			 }
		}
		return customer; 
	}
	
	public Customer depositMoney(Customer customer, int accountNumber, double amount) {
		Account account = customer.getAccount(accountNumber);
		double currentBalance = account.getBalance();
		double newBalance = currentBalance += amount;
		account.setBalance(newBalance);
		customer.addAccount(accountNumber, account);
		accountManager.updateCustomer(customer);
		System.out.println("Thank you, your new balance is " + newBalance);
		return customer; 
		
		
		
	}
	
	public Customer withdrawMoney(Customer customer, int accountNumber, double amount) throws BalanceTooLowException {
		Account account = customer.getAccount(accountNumber);
		double currentBalance = account.getBalance();
		if (amount > currentBalance) {throw new BalanceTooLowException();};
		double newBalance = currentBalance -= amount;
		customer.addAccount(accountNumber, account);
		accountManager.updateCustomer(customer);
		System.out.println("Thank you, your new balance is " + newBalance);
		System.out.println("-----------------------------------------");
		return customer; 
		
	}
	
	public void printBalance(Customer customer, int accountNumber) {
		System.out.println("Your current balance is " + customer.getAccount(accountNumber).getBalance());
		System.out.println("-----------------------------------------");
	}

}
