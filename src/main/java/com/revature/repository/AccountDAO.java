package com.revature.repository;

import java.util.HashMap;

import com.revature.model.Account;
import java.util.*;

public class AccountDAO implements DAO {

	@Override
	public HashMap<Integer, Account> getAccounts() {
		return AccountRepo.listOfAccounts;
	}
	
	public void addAccount(int accountNumber, Account account) {
		AccountRepo.listOfAccounts.put(accountNumber, account);
	}

	@Override
	public Account getAccount(int accountNumber) {
		HashMap<Integer, Account> listOfAccounts = this.getAccounts();
		return listOfAccounts.get(accountNumber);
	}

	@Override
	public void updateAccount(int accountNumber, Account account) {
		AccountRepo.listOfAccounts.put(accountNumber, account);
		
	}

	@Override
	public void updateAccounts(HashMap<Integer, Account> accounts) {
		AccountRepo.listOfAccounts = accounts;
		
	}


	

}
