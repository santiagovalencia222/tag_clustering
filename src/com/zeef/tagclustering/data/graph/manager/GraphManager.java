package com.zeef.tagclustering.data.graph.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.javatuples.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.zeef.tagclustering.data.linkmanager.LinkRetriever;
import com.zeef.tagclustering.helpers.MapHelper;
import com.zeef.tagclustering.model.Tag;

public class GraphManager {

	private SimpleWeightedGraph<String, DefaultWeightedEdge> undirectedGraph;
	private static final Integer MAX_TAGS = 10;

	public GraphManager() {
		undirectedGraph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}

	public SimpleWeightedGraph<String, DefaultWeightedEdge> buildUndirectedGraph() {
		List<Pair<Tag, Tag>> coTags = generateCoTags();
		int count = 0;
		for (Pair<Tag, Tag> pair : coTags) {
			try {
				if (!undirectedGraph.containsEdge(pair.getValue0().getName(), pair.getValue1().getName()) &&
						!pair.getValue0().getName().equalsIgnoreCase(pair.getValue1().getName())) {
					undirectedGraph.addVertex(pair.getValue0().getName());
					undirectedGraph.addVertex(pair.getValue1().getName());
					DefaultWeightedEdge e = undirectedGraph.addEdge(pair.getValue0().getName(), pair.getValue1().getName());
					Double coTagOccurrence = getCoTagsOccurrence(pair, coTags);
					undirectedGraph.setEdgeWeight(e, coTagOccurrence);
					System.out.println("Edge " + count++);
					System.out.println("Tag 1: " + pair.getValue0().getName());
					System.out.println("Tag 2: " + pair.getValue1().getName());
					System.out.println("Weight: " + undirectedGraph.getEdgeWeight(undirectedGraph.getEdge(pair.getValue0().getName(), pair.getValue1().getName())) + "\n");
				}
			} catch (NullPointerException e) {
			}
		}
		return undirectedGraph;
	}

	public List<Pair<Tag, Tag>> generateCoTags() {
		List<Pair<Tag, Tag>> coTags = new ArrayList<>();
		LinkRetriever retriever = new LinkRetriever();
		ResultSet resultSet = retriever.getTaggedLinks();
		try {
			while (resultSet.next()) {
				Tag tag = new Tag(resultSet.getString("tag_name"));
				Tag pageTitle = new Tag(resultSet.getString("page_title"));
				Tag blockTitle = new Tag(resultSet.getString("block_title"));
				coTags.add(new Pair<>(tag, pageTitle));
				coTags.add(new Pair<>(tag, blockTitle));
				//coTags.add(new Pair<>(blockTitle, pageTitle));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coTags;
	}

	public Map<Tag, List<Tag>> getMainCoTags(String tag) {
		Map<Tag, List<Tag>> mainCoTagsBucket = new HashMap<>();
		List<Tag> listOfCoTags = new ArrayList<>();
		Map<DefaultWeightedEdge, Double> completeCoTagsBucket = new HashMap<>();
		for (DefaultWeightedEdge edge : undirectedGraph.edgesOf(tag)) {
			completeCoTagsBucket.put(edge, undirectedGraph.getEdgeWeight(edge));
		}
		Integer count = 0;
		for (Entry<DefaultWeightedEdge, Double> entry : MapHelper.sortMapByValue(completeCoTagsBucket).entrySet()) {
			if (count < MAX_TAGS) {
				if (undirectedGraph.getEdgeSource(entry.getKey()).equals(tag)) {
					listOfCoTags.add(new Tag(undirectedGraph.getEdgeTarget(entry.getKey())));
				} else {
					listOfCoTags.add(new Tag(undirectedGraph.getEdgeSource(entry.getKey())));
				}
			}
			count++;
		}
		mainCoTagsBucket.put(new Tag(tag), listOfCoTags);
		return mainCoTagsBucket;
	}

	public Double getCoTagsOccurrence(Pair<Tag, Tag> pair, List<Pair<Tag, Tag>> coTags) {
		Double occurrences = 0.0;
		for (Pair<Tag, Tag> pairInCoTags : coTags) {
			if (pairInCoTags.getValue0().equals(pair.getValue0()) &&
					pairInCoTags.getValue1().equals(pair.getValue1())) {
				occurrences++;
			}
		}
		return occurrences;
	}
}
