package com.revature.repository;
import java.util.*;
import com.revature.model.*;
public interface BetterDAO <T> {
	public T getAccount(int accountnumber);
	public List<T> getAccounts();
	public void updateAccount(Account account);
	
}
