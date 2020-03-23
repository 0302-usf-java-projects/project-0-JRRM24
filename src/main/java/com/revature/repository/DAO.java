package com.revature.repository;
import java.util.*;
/**
 * Interface that defines some basic operations to be done with an Account DAO
 */
import com.revature.model.*;
public interface DAO <T> {
	HashSet<Account> getAll(); 
	void addAccount(Account account); 
	void deleteAccount(Account account); 
	void updateAccount(Account account); 
	
}
