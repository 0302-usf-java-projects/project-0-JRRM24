package com.revature.repository;

public class ConfigurationSettings {
	
	public static final String password = System.getenv("dbpassword");
	public static final String user = System.getenv("user");
	public static final String url = System.getenv("url");
}
