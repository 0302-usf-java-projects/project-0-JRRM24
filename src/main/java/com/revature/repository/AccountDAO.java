package com.revature.repository;

import java.util.HashSet;

import com.revature.model.Account;

public class AccountDAO implements DAO<Account>{

	@Override
	public HashSet<Account> getAll() {
		return AccountRepo.accounts; 
		
	}

	@Override
	public void addAccount(Account account) {
		AccountRepo.accounts.add(account); 
		
	}

	@Override
	public void deleteAccount(Account account) {
		AccountRepo.accounts.remove(account);
		
	}

	@Override
	public void updateAccount(Account account) {
		for (Account a: AccountRepo.accounts) {
			if (account.authenticate(a.getUsername(), a.getPassword())) {
				AccountRepo.accounts.remove(a); 
				AccountRepo.accounts.add(account);
				
			}
		}
		
	}

}
