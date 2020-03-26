package com.revature.repository;
import java.sql.*;
import static com.revature.repository.ConfigurationSettings.*;
public class ConnectionUtil {
	public static Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection(url, userName, password);
		return conn;
}


}
