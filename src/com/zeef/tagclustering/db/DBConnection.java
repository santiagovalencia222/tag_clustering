package com.zeef.tagclustering.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private Connection connection = null;

	public DBConnection() {
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://pgdb0.amsterdam.kizitos.com:5432/zeef_update", "zeef",
					"g3z3v3r");
		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
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
