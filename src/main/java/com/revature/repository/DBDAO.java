package com.revature.repository;
import java.util.*;
import com.revature.exception.*;
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
			String firstName = rs.getString("FirstName");
			String lastName = rs.getString("LastName");
			result = new Account(newUsername, newPassword, newBalance, newAccountNumber, firstName, lastName);
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
			String firstName = rs.getString("FirstName");
			String lastName = rs.getString("LastName");
			result = new Account(newUsername, newPassword, newBalance, accountNumber, firstName, lastName);
	
		}
		
		} catch(SQLException e) {
		e.printStackTrace();
		}
		return result;
	}

	@Override
	public void updateAccount(int accountNumber, Account account)  {
		
		String username = account.getUserName();
		String password = account.getPassword();
		double balance = account.getBalance();
		String firstName = account.getfirstName();
		String lastName = account.getlastName();
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
	 public void addAccount(int accountnumber, Account account) throws UsernameAlreadyTakenException {
		HashMap<Integer, Account> accounts = new DBDAO().getAccounts();
		Set<Integer> keys = accounts.keySet();
		for (Integer key: keys) {
			Account temporary = accounts.get(key);
			String firstUsername = account.getUserName();
			String secondUsername = temporary.getUserName();
			if(firstUsername.equals(secondUsername)) {
				throw new UsernameAlreadyTakenException();
			}
		}
		String username = account.getUserName();
		String password = account.getPassword();
		double balance = account.getBalance();
		String firstName = account.getfirstName();
		String lastName = account.getlastName();
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
		java.sql.Statement s = conn.createStatement();
		s.executeUpdate(query.toString());
		
		} catch(SQLException e) {
		e.printStackTrace();
		}
		
		
	}
	

}
