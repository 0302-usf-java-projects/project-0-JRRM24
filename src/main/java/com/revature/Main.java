package com.revature;

/** 
 * Create an instance of your controller and launch your application.
 * 
 * Try not to have any logic at all on this class.
 */
import com.revature.controller.*;
import org.apache.log4j.*;
import java.util.*;
import com.revature.exception.*;
import com.revature.model.*;
import com.revature.repository.*;
import com.revature.service.*;
public class Main {

	public static void main(String[] args) {
		

		MenuRunner menuRunner = new MenuRunner();
		menuRunner.runMenu();
		
	}
}
