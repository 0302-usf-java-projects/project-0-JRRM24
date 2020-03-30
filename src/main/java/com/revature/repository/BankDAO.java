package com.revature.repository;
import com.revature.model.*;
/**
 * This class contains all of my actual calls to the database. I
 *There are two tables in the database, one for accounts and one for transactions.
 *The logic to query the database for the information to reconstruct account objects 
 *or create a list of transactions is here.
 */

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
		String query = "select * from accounts where account_number = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1,accountnumber);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
		String newUsername = rs.getString("user_name");
		String newPassword = rs.getString("password");
		int newAccountNumber = rs.getInt("account_number");
		double newBalance = rs.getDouble("balance");
		String firstName = rs.getString("FirstName");
		String lastName = rs.getString("LastName");
		boolean admin = rs.getBoolean("employee");
		boolean pending = rs.getBoolean("pending");
		result = new Account(newUsername, newPassword, newBalance, newAccountNumber, firstName, lastName, pending,admin);
		
	
		}} catch(SQLException e) {
		e.printStackTrace();
		}
		return result;
		
	}
	
	public void insertTransaction(Account account, String type, int other_account, double amount) {
		try(Connection conn = ConnectionUtil.connect())
		{
		StringBuilder query = new StringBuilder();
		
		query.append("insert into transactions values (?, ?, ?, ?);");
		//changed to prepared statement 
//		query.append("'" +account.getAccountNumber()+"'" + ",");
//		query.append("'" +type+"'" + ",");
//		query.append("'" +other_account+"'" + ",");
//		query.append("'" +amount+"'");
//		query.append(")");
//		query.append(";");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setInt(1, account.getAccountNumber());
		ps.setString(2, type);
		ps.setInt(3,  other_account);
		ps.setDouble(4, amount);
;		ps.executeUpdate();
		} catch(SQLException e) {
		e.printStackTrace();
		}
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
			boolean pending = rs.getBoolean("pending");
			result = new Account(newUsername, newPassword, newBalance, newAccountNumber, firstName, lastName, pending, admin);
			accounts.add(result);

		
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
		boolean pending = account.pending;
		try(Connection conn = ConnectionUtil.connect())
		{
		StringBuilder query = new StringBuilder();
		
		query.append("update accounts set balance = ? where account_number = ?; ");
//		query.append("'" +balance+"'");
//		query.append(" where account_number =");
//		query.append(" " + "'" + accountnumber + "'");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setDouble(1, balance);
		ps.setInt(2, accountnumber);
		ps.executeUpdate();
			if (pending == false) {
				StringBuilder sql = new StringBuilder();
				sql.append( "update accounts set pending = false where account_number =");
				sql.append(" " + "'" + accountnumber + "'");
				PreparedStatement ps2 = conn.prepareStatement(sql.toString());
				ps2.executeUpdate();
			}
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
		
		query.append("insert into accounts values (?, ?, ?, ?, ?, ?);");
//		query.append("'" +accountnumber+"'" + ",");
//		query.append("'" +username+"'" + ",");
//		query.append("'" +password+"'" + ",");
//		query.append("'" +balance+"'"+",");
//		query.append("'"+firstName+"'"+",");
//		query.append("'"+lastName+"'");
//		query.append(")");
//		query.append(";");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setInt(1, accountnumber);
		ps.setString(2, username);
		ps.setString(3, password);
		ps.setDouble(4, balance);
		ps.setString(5, firstName);
		ps.setString(6,  lastName);
		ps.executeUpdate();
		
		} catch(SQLException e) {
		e.printStackTrace();
		}
		
	}
	
	public List<String> getTransactions() {
		List<String> results = new ArrayList<>();
		try(Connection conn = ConnectionUtil.connect())
		{
		StringBuilder query = new StringBuilder();
		query.append("select * from transactions");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			int account_number = rs.getInt("account_number");
			double balance = rs.getDouble("balance");
			int other_account = rs.getInt("other_account");
			String transaction_type = rs.getString("transaction_type");
			if (transaction_type.equals("transfer")) {
				String newTransaction = "Account: " + account_number + " transfered " + balance + " to Account: " + other_account;
				results.add(newTransaction);
			} else if (transaction_type.equals("withdrawal")) 
			{
				String newTransaction = "Account: " + account_number + " withdrew " + balance;
				results.add(newTransaction);
			} else {
				String newTransaction = "Account: " + account_number + " deposited " + balance;
				results.add(newTransaction);
			}
		
		}
		
		} catch(SQLException e) {
		e.printStackTrace();
		}
		return results;
	}

}
