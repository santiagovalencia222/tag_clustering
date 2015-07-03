package com.zeef.tagclustering.data;

import java.sql.ResultSet;

import com.zeef.tagclustering.db.DBConnection;

public abstract class DataRetriever {

	public ResultSet executeSelectQueryZEEF(String query) {
		DBConnection connection = new DBConnection();
		connection.doZEEFConnection();
		return connection.executeQuery(query);
	}
	
	public ResultSet executeSelectQueryFR(String query) {
		DBConnection connection = new DBConnection();
		connection.doLocalFRConnection();
		return connection.executeQuery(query);
	}
	
	public ResultSet executeSelectQueryLocal(String query) {
		DBConnection connection = new DBConnection();
		connection.doLocalConnection();
		return connection.executeQuery(query);
	}
	
	public void executeUpdateQuery(String query) {
		DBConnection connection = new DBConnection();
		connection.doLocalConnection();
		connection.executeUpdate(query);
	}

}
