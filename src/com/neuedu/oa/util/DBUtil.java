package com.neuedu.oa.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;
import com.neuedu.oa.Application;

public class DBUtil {
	private final static String DEFAULT_DRIVER="com.mysql.cj.jdbc.Driver";
	private final static String DRIVER=Application.getString("jdbc.driver",DEFAULT_DRIVER);

	
	private final static String DEFAULT_URL="jdbc:mysql://127.0.0.1:3306/oa?useSSL=false";
	private final static String URL=Application.getString("jdbc.url",DEFAULT_URL);
	
	static final private ThreadLocal<Connection> CONNECTION_THREAD_LOCAL=new ThreadLocal<>();
	
	
	private final static String USERNAME=Application.getString("jdbc.username", "root")
			,PASSWORD=Application.getString("jdbc.password","root");
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Connection getConnection() throws SQLException {
		Connection connection=CONNECTION_THREAD_LOCAL.get();
		if(connection==null) {
			connection= DriverManager.getConnection(URL, USERNAME, PASSWORD);
			connection.setAutoCommit(false);
			CONNECTION_THREAD_LOCAL.set(connection);
		}
		return connection;
	}
	
	public static void closeConnection() {
		Connection connection=CONNECTION_THREAD_LOCAL.get();
		if(connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {	}
		}
	}
}
