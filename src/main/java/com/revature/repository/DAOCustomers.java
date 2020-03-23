package com.revature.repository;
import com.revature.model.*;
import java.util.HashMap;

import com.revature.model.Customer;
import java.util.*; 
public class DAOCustomers implements DAO {

	@Override
	public Customer getCustomer(String username) {
		HashMap<String, Customer> accounts = CustomerRepo.customers; 
		return accounts.get(username); 
	}

	@Override
	public HashMap<String, Customer> getAllCustomers() {
		return CustomerRepo.customers; 
		
	}

	@Override
	public void updateCustomerAccounts(HashMap<String, Customer> customers) {
		CustomerRepo.customers = customers; 
		
	}
	
	public void updateCustomer(Customer customer) {
		CustomerRepo.customers.put(customer.getUsername(), customer);
	}
	
}
