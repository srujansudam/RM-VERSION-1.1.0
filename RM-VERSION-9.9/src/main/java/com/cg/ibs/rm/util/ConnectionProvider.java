package com.cg.ibs.rm.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

	private static final String PROPS = "/ibs.properties";
	private static ConnectionProvider instance;
	private String url;
	private String username;
	private String password;

	private ConnectionProvider() throws IOException {
		Properties props = new Properties();
		String file = ConnectionProvider.class.getResource(PROPS).getFile();
		props.load(new FileInputStream(file));
		url = props.getProperty("url");
		username = props.getProperty("username");
		password = props.getProperty("password");
	}

	public static ConnectionProvider getInstance() throws IOException {
		if (instance == null) {
			synchronized (ConnectionProvider.class) {
				instance = new ConnectionProvider();
			}
		}

		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}
/*
 * // private static final String PROPS = "/ibs.properties"; private static
 * ConnectionProvider instance; private String url; private String username;
 * private String password;
 * 
 * private ConnectionProvider() throws IOException { ResourceBundle bundle =
 * ResourceBundle.getBundle("/ibs.properties"); url = bundle.getString("url");
 * username = bundle.getString("username"); password =
 * bundle.getString("password"); }
 * 
 * public static ConnectionProvider getInstance() throws IOException { if
 * (instance == null) { instance = new ConnectionProvider(); } return instance;
 * }
 * 
 * public Connection getConnection() throws SQLException { return
 * DriverManager.getConnection(url, username, password); }
 */
