package com.zeef.tagclustering.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private Connection connection = null;

	public void doZEEFConnection() {
		try {
			connection = DriverManager.getConnection();
		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
		}
	}

	public void doLocalConnection() {
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/tag_clustering", "admin",
					"");
		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String query) {
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
