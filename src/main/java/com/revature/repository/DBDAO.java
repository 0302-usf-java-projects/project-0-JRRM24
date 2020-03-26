package com.revature.repository;

import java.util.HashMap;
import java.sql.*;
import com.revature.model.*;
import com.revature.model.Account;

public class DBDAO implements DAO {

	@Override
	public HashMap<Integer, Account> getAccounts() {
		HashMap<Integer, Account> accounts = new HashMap<Integer, Account>();
		Account result = null;
		try(Connection conn = ConnectionUtil.connect())
		{
		StringBuilder query = new StringBuilder();
		query.append("select * from accounts");
		java.sql.Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query.toString());
		while(rs.next()) {
			String newUsername = rs.getString("user_name");
			String newPassword = rs.getString("password");
			int newAccountNumber = rs.getInt("account_number");
			double newBalance = rs.getDouble("balance");
			result = new Account(newUsername, newPassword, newBalance, newAccountNumber);
			accounts.put(newAccountNumber, result);
		}
		
		} catch(SQLException e) {
		e.printStackTrace();
		}
		return accounts;
	}

	@Override
	public Account getAccount(int accountNumber) {
		String username = "";
		String password = "";
		double balance = 0;
		Account result = null;
		try(Connection conn = ConnectionUtil.connect())
		{
		StringBuilder query = new StringBuilder();
		query.append("select * from accounts where account_number =");
		query.append("'" + accountNumber+"'");
		java.sql.Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query.toString());
		while(rs.next()) {
			String newUsername = rs.getString("user_name");
			String newPassword = rs.getString("password");
			int newAccountNumber = rs.getInt("account_number");
			double newBalance = rs.getDouble("balance");
			result = new Account(newUsername, newPassword, newBalance, accountNumber);
	
		}
		
		} catch(SQLException e) {
		e.printStackTrace();
		}
		return result;
	}

	@Override
	public void updateAccount(int accountNumber, Account account) {
		//update accounts set balance = account.balance where accountnumber = accountnumber
		String username = account.getUserName();
		String password = account.getPassword();
		double balance = account.getBalance();
		try(Connection conn = ConnectionUtil.connect())
		{
		StringBuilder query = new StringBuilder();
		
		query.append("update accounts set balance = ");
		query.append("'" +balance+"'");
		query.append(" where account_number =");
		query.append(" " + "'" + accountNumber + "'");
		java.sql.Statement s = conn.createStatement();
		s.executeUpdate(query.toString());
		
		} catch(SQLException e) {
		e.printStackTrace();
		}
		
	}

	@Override
	public void updateAccounts(HashMap<Integer, Account> accounts) {
		
		
	}

	@Override
	 public void addAccount(int accountnumber, Account account) {
		String username = account.getUserName();
		String password = account.getPassword();
		double balance = account.getBalance();
		try(Connection conn = ConnectionUtil.connect())
		{
		StringBuilder query = new StringBuilder();
		
		query.append("insert into accounts values (");
		query.append("'" +accountnumber+"'" + ",");
		query.append("'" +username+"'" + ",");
		query.append("'" +password+"'" + ",");
		query.append("'" +balance+"'");
		query.append(")");
		query.append(";");
		java.sql.Statement s = conn.createStatement();
		s.executeUpdate(query.toString());
		
		} catch(SQLException e) {
		e.printStackTrace();
		}
		
		
	}
	

}
