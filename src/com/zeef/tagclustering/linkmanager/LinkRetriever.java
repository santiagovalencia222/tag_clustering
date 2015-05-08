package com.zeef.tagclustering.linkmanager;

import java.sql.ResultSet;

import com.zeef.tagclustering.db.DBConnection;

public class LinkRetriever {

	public ResultSet getAllLinks() {
		return executeQuery("SELECT link.target_url FROM link");
	}

	public ResultSet getLinksXPages() {
		return executeQuery("SELECT link.target_url, block.page_id "
				+ "FROM link, block, link_position WHERE link_position.link_block_id = block.id AND "
				+ "link.id = link_position.link_id");
	}

	public ResultSet getPositionedLinks() {
		return executeQuery("SELECT DISTINCT link.target_url FROM link, block, link_position "
				+ "WHERE link_position.link_block_id = block.id AND link.id = link_position.link_id  "
				+ "AND link.target_url LIKE 'http%' LIMIT 200");
	}

	public ResultSet getTaggedLinks() {
		return executeQuery("SELECT DISTINCT link.target_url, tag.name FROM tag, page_tag, "
				+ "page, link, block, link_position WHERE tag.id = page_tag.tag_id AND "
				+ "page.id = page_tag.page_id AND block.page_id = page.id AND "
				+ "link_position.link_block_id = block.id AND link.id = link_position.link_id AND "
				+ "link.target_url LIKE 'http%' LIMIT 100");
	}

	public ResultSet executeQuery(String query) {
		DBConnection connection = new DBConnection();
		connection.doZEEFConnection();
		return connection.executeQuery(query);
	}
}
