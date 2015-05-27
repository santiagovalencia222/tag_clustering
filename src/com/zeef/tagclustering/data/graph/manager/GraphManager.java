package com.zeef.tagclustering.data.graph.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.javatuples.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.zeef.tagclustering.data.linkmanager.LinkRetriever;
import com.zeef.tagclustering.helpers.MapHelper;
import com.zeef.tagclustering.model.Resource;
import com.zeef.tagclustering.model.Tag;

public class GraphManager {

	private SimpleWeightedGraph<Tag, DefaultWeightedEdge> undirectedGraph;
	private static final Integer MAX_TAGS = 10;

	public GraphManager() {
		undirectedGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}

	public SimpleWeightedGraph<Tag, DefaultWeightedEdge> buildUndirectedGraph() {
		Map<Pair<Tag, Tag>, Set<Resource>> coTaggedResources = generateCoTaggedResourcesMap();
		for (Entry<Pair<Tag, Tag>, Set<Resource>> entry : coTaggedResources.entrySet()) {
			Pair<Tag, Tag> pair = entry.getKey();
			if (!undirectedGraph.containsEdge(pair.getValue0(), pair.getValue1()) &&
					!pair.getValue0().equals(pair.getValue1())) {
				undirectedGraph.addVertex(pair.getValue0());
				undirectedGraph.addVertex(pair.getValue1());
				DefaultWeightedEdge edge = undirectedGraph.addEdge(pair.getValue0(), pair.getValue1());
				Double weight = new Double(entry.getValue().size());
				undirectedGraph.setEdgeWeight(edge, weight);
			}
		}
		return undirectedGraph;
	}

	public Map<Pair<Tag, Tag>, Set<Resource>> generateCoTaggedResourcesMap() {
		Map<Pair<Tag, Tag>, Set<Resource>> coTaggedResources = new HashMap<>();
		LinkRetriever retriever = new LinkRetriever();
		ResultSet resultSet = retriever.getTaggedLinks();
		try {
			while (resultSet.next()) {
				if (resultSet.getString("tag_name") != null &&
						resultSet.getString("page_title") != null &&
							resultSet.getString("block_title") != null &&
								resultSet.getString("target_url") != null) {

					Tag tag = new Tag(resultSet.getString("tag_name"));
					Tag pageTitle = new Tag(resultSet.getString("page_title"));
					Tag blockTitle = new Tag(resultSet.getString("block_title"));
					Resource resource = new Resource(resultSet.getString("target_url"));

					Set<Resource> set = null;

					Pair<Tag, Tag> pair1 = new Pair<>(tag, pageTitle);
					if (coTaggedResources.get(pair1) != null) {
						coTaggedResources.get(pair1).add(resource);
					} else {
						set = new HashSet<>();
						set.add(resource);
						coTaggedResources.put(pair1, set);
					}

					Pair<Tag, Tag> pair2 = new Pair<>(tag, blockTitle);
					if (coTaggedResources.get(pair2) != null) {
						coTaggedResources.get(pair2).add(resource);
					} else {
						set = new HashSet<>();
						set.add(resource);
						coTaggedResources.put(pair2, set);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coTaggedResources;
	}

	public Map<Tag, List<Tag>> getMainCoTags(Tag tag) {
		Map<Tag, List<Tag>> mainCoTagsBucket = new HashMap<>();
		List<Tag> listOfCoTags = new ArrayList<>();
		Map<DefaultWeightedEdge, Double> allCoTagsBucket = new HashMap<>();
		for (DefaultWeightedEdge edge : undirectedGraph.edgesOf(tag)) {
			allCoTagsBucket.put(edge, undirectedGraph.getEdgeWeight(edge));
		}
		Integer count = 0;
		allCoTagsBucket = MapHelper.sortMapByValue(allCoTagsBucket);
		System.out.println(allCoTagsBucket);
		for (Entry<DefaultWeightedEdge, Double> entry : allCoTagsBucket.entrySet()) {
			if (count < MAX_TAGS) {
				if (undirectedGraph.getEdgeSource(entry.getKey()).equals(tag)) {
					listOfCoTags.add(undirectedGraph.getEdgeTarget(entry.getKey()));
				} else {
					listOfCoTags.add(undirectedGraph.getEdgeSource(entry.getKey()));
				}
			}
			count++;
		}
		mainCoTagsBucket.put(tag, listOfCoTags);
		return mainCoTagsBucket;
	}
}
