package com.zeef.tagclustering.documentmanager;

import java.sql.ResultSet;

import com.zeef.tagclustering.db.DBConnection;

public class DocumentRetriever {

	public ResultSet getAllDocuments() {
		return executeQuery("SELECT * FROM documents");
	}

	public ResultSet executeQuery(String query) {
		DBConnection connection = new DBConnection();
		connection.doLocalConnection();
		return connection.executeQuery(query);
	}

}