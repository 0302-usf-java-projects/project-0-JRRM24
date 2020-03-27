package com.revature.repository;
import com.revature.model.*;
import java.util.*;

public interface DAO {
	public HashMap<Integer, Account> getAccounts();
	public Account getAccount(int accountNumber);
	public void updateAccount(int accountNumber, Account account);
	
	public void addAccount(int accountNumber, Account account);
	
}
