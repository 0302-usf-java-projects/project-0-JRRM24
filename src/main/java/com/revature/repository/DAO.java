package com.revature.repository;
import java.util.*;
import com.revature.model.*;
public interface DAO <T> {
	HashSet<Account> getAll(); 
	void addAccount(Account account); 
	void deleteAccount(Account account); 
	void updateAccount(Account account); 
	
}
