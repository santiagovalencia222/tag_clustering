package com.zeef.tagclustering.data.linkmanager;

import java.sql.ResultSet;

import com.zeef.tagclustering.data.DataRetriever;

public class LinkRetriever extends DataRetriever{

	public ResultSet getAllLinks() {
		return executeSelectQueryZEEF("SELECT link.target_url FROM link");
	}

	public ResultSet getLinksXPages() {
		return executeSelectQueryZEEF("SELECT link.target_url, block.page_id "
			+ "FROM link, block, link_position WHERE link_position.link_block_id = block.id AND "
			+ "link.id = link_position.link_id");
	}

	public ResultSet getPositionedLinks() {
		return executeSelectQueryZEEF("SELECT DISTINCT link.target_url FROM link, block, link_position "
			+ "WHERE link_position.link_block_id = block.id AND link.id = link_position.link_id  "
			+ "AND link.target_url LIKE 'http%' LIMIT 200");
	}

	public ResultSet getTaggedLinks() {
		return executeSelectQueryZEEF("SELECT DISTINCT link.target_url, tag.name AS tag_name, "
			+ "zeef_user.full_name,	alias.name AS page_title, block.title AS block_title "
			+ "FROM page "
			+ "JOIN subject ON subject.id = page.subject_id "
			+ "JOIN alias ON alias.subject_id = subject.id "
			+ "JOIN block ON block.page_id = page.id "
			+ "JOIN link_position ON link_position.link_block_id = block.id "
			+ "JOIN link ON link.id = link_position.link_id "
			+ "JOIN zeef_user ON page.user_id = zeef_user.id "
			+ "JOIN page_tag ON page.id = page_tag.page_id "
			+ "JOIN tag ON tag.id = page_tag.tag_id "
			+ "WHERE alias.default_alias");
	}

}