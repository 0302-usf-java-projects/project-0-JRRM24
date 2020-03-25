package com.revature;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;
import com.revature.controller.*;
import com.revature.exception.*;
import com.revature.model.*;
import com.revature.repository.*;
import com.revature.service.*;

public class ServicesTest {
	private static BankServices bs = new BankServices(); 
	private static Account testAccount1;
	private static Account testAccount2;
	@BeforeClass 
	public static void setUp() {
		ServicesTest.testAccount1 = new Account("John Smith", "password", 500, 1);
		ServicesTest.testAccount2 = new Account("Jane Doe", "mypassword", 200, 2);
		bs.accountManager.addAccount(1, testAccount1);
		bs.accountManager.addAccount(2, testAccount2);
	}
	
	@Test
	
	public void successfulDeposit() {
		Account testDeposit = bs.deposit(testAccount1, 500);
		double result = testDeposit.getBalance();
		assertTrue(result == 1000);
	}
	
	@Test
	public void successfulWithdraw() {
		Account testWithdraw = bs.withdraw(testAccount1, 300);
		double result = testWithdraw.getBalance();
		assertTrue(result == 700);
	}
	
	@Test
	public void succesfulLogIn() {
		Account testLogIn = bs.logIn("John Smith", "password");
		assertTrue(testLogIn.authenticate("John Smith", "password"));
	}
	
	@Test
	public void failedLogIn() {
		Account testLogIn = bs.logIn("username", "password");
		assertFalse(testLogIn.nullFlag);
	}
	
}
