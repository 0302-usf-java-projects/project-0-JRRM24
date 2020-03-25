package com.revature;
import static org.junit.Assert.*;
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

public class AccountTest {
	
	private static Account testAccount1 = null;
	@Before
	public void setUp() {
		AccountTest.testAccount1 = new Account("John Smith", "password", 500, 1);
		
	}
	
	@After
	public void remove() {
		AccountTest.testAccount1 = null; 
	}
	
	@Test 
	public void successfulAuthentication() {
		boolean result = testAccount1.authenticate("John Smith", "password");
		assertTrue(result == true);
	}
	
	@Test
	
	public void unsuccesfulAuthentication() {
		boolean result = testAccount1.authenticate("John Smith", "quizzibug");
		assertTrue(result == false);
	}
	
	@Test
	
	public void increaseBalanceBy500() {
		testAccount1.increaseBalance(500);
		double result = testAccount1.getBalance();
		assertTrue(result == 1000);
	}
	
	@Test 
	public void decreaseBalanceBy500() {
		testAccount1.decreaseBalance(500);
		double result = testAccount1.getBalance();
		assertTrue(result == 0);
	}
	

}
