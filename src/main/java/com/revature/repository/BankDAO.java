package com.revature.repository;
import com.revature.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import com.revature.exception.*;
public class BankDAO implements BetterDAO<Account> {

	@Override
	public Account getAccount(int accountnumber) {
		Account result = null;
		try(Connection conn = ConnectionUtil.connect())
		{
		String query = "select * from accounts where account_number = " + accountnumber;
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
		String newUsername = rs.getString("user_name");
		String newPassword = rs.getString("password");
		int newAccountNumber = rs.getInt("account_number");
		double newBalance = rs.getDouble("balance");
		String firstName = rs.getString("FirstName");
		String lastName = rs.getString("LastName");
		boolean admin = rs.getBoolean("employee");
		result = new Account(newUsername, newPassword, newBalance, newAccountNumber, firstName, lastName);
		if (admin == true) {
			result.setAdminStatus();
		}
		}} catch(SQLException e) {
		e.printStackTrace();
		}
		return result;
		
	}
	

	@Override
	public List<Account> getAccounts() {
		List<Account> accounts = new ArrayList<>();
		Account result;
		try(Connection conn = ConnectionUtil.connect())
		{
		StringBuilder query = new StringBuilder();
		query.append("select * from accounts");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			String newUsername = rs.getString("user_name");
			String newPassword = rs.getString("password");
			int newAccountNumber = rs.getInt("account_number");
			double newBalance = rs.getDouble("balance");
			String firstName = rs.getString("FirstName");
			String lastName = rs.getString("LastName");
			boolean admin = rs.getBoolean("employee");
			result = new Account(newUsername, newPassword, newBalance, newAccountNumber, firstName, lastName);
			if (admin == false) 
				{accounts.add(result);}
		}
		
		} catch(SQLException e) {
		e.printStackTrace();
		}
		return accounts;
	}

	@Override
	public void updateAccount(Account account) {
		String username = account.getUserName();
		String password = account.getPassword();
		double balance = account.getBalance();
		String firstName = account.getfirstName();
		String lastName = account.getlastName();
		int accountnumber = account.getAccountNumber();
		try(Connection conn = ConnectionUtil.connect())
		{
		StringBuilder query = new StringBuilder();
		
		query.append("update accounts set balance = ");
		query.append("'" +balance+"'");
		query.append(" where account_number =");
		query.append(" " + "'" + accountnumber + "'");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.executeUpdate();
		
		} catch(SQLException e) {
		e.printStackTrace();
		}
		
	}
	
	public void addAccount(Account account) throws UsernameAlreadyTakenException {
		List<Account> accounts = this.getAccounts();
		for (Account e: accounts) {
			if (account.getUserName().equals(e.getUserName())) {
				throw new UsernameAlreadyTakenException();
			}
		}
		String username = account.getUserName();
		String password = account.getPassword();
		double balance = account.getBalance();
		String firstName = account.getfirstName();
		String lastName = account.getlastName();
		int accountnumber = account.getAccountNumber();
		try(Connection conn = ConnectionUtil.connect())
		{
		StringBuilder query = new StringBuilder();
		
		query.append("insert into accounts values (");
		query.append("'" +accountnumber+"'" + ",");
		query.append("'" +username+"'" + ",");
		query.append("'" +password+"'" + ",");
		query.append("'" +balance+"'"+",");
		query.append("'"+firstName+"'"+",");
		query.append("'"+lastName+"'");
		query.append(")");
		query.append(";");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.executeUpdate();
		
		} catch(SQLException e) {
		e.printStackTrace();
		}
		
	}

}
