package com.revature.repository;
import com.revature.model.*;
import java.util.*; 
public interface DAO {
	public abstract Customer getCustomer(String username); 
	public abstract HashMap<String, Customer> getAllCustomers();
	public abstract void updateCustomerAccounts(HashMap<String, Customer> customers); 
}
