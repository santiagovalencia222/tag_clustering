package com.zeef.tagclustering;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zeef.tagclustering.linkmanager.LinkRetriever;
import com.zeef.tagclustering.model.MyPair;
import com.zeef.tagclustering.model.Tag;

public class TagClusteringAlgorithm {

	public List<MyPair<Tag, Tag>> generateCoTags() {
		List<MyPair<Tag, Tag>> coTags = new ArrayList<>();
		LinkRetriever retriever = new LinkRetriever();
		ResultSet resultSet = retriever.getTaggedLinks();
		try {
			while (resultSet.next()) {
				Tag tag = new Tag(resultSet.getString("tag_name"));
				Tag pageTitle = new Tag(resultSet.getString("page_title"));
				Tag blockTitle = new Tag(resultSet.getString("block_title"));
				coTags.add(new MyPair<>(tag, pageTitle));
				coTags.add(new MyPair<>(tag, blockTitle));
				coTags.add(new MyPair<>(blockTitle, pageTitle));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coTags;
	}

	public Map<MyPair<Tag, Tag>, Integer> getCoTagOccurences() {
		List<MyPair<Tag, Tag>> coTags = generateCoTags();
		Map<MyPair<Tag, Tag>, Integer> coTagOccurences = new HashMap<>();
		for (MyPair<Tag, Tag> pair : coTags) {
			coTagOccurences.put(pair, Collections.frequency(coTags, pair));
		}
		return coTagOccurences;
	}

}
