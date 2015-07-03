package com.zeef.tagclustering.partitioning;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.zeef.tagclustering.model.Tag;

public class ModularityFunction {

	/**
	 * The modularity function is formally defined in [1] (See thesis document).
	 * @return
	 */
	public Double calculateModularityFunctionMetric(SimpleWeightedGraph<Tag, DefaultWeightedEdge> graph) {
		Double result = 0.0;
		Double withinClusterEdgesWeightSum = 0.0;
		Double attachedEdgesWeightSum = 0.0;
		Double allEdgesWeightSum = 0.0;

		//TODO Call the clustering function to generate all clusters
		Set<SimpleWeightedGraph<Tag, DefaultWeightedEdge>> clusters = new HashSet<SimpleWeightedGraph<Tag, DefaultWeightedEdge>>();

		//1. Calculate the sum of all edges' in the whole graph
		allEdgesWeightSum = getGraphEdgesWeightSum(graph);

		//2. For each cluster: Calculate the sum of all within and attached edges
		//3. Compute the previous result to calculate the final result
		for (SimpleWeightedGraph<Tag, DefaultWeightedEdge> cluster : clusters) {
			withinClusterEdgesWeightSum = getGraphEdgesWeightSum(cluster);
			attachedEdgesWeightSum = getAttachedEdgesWeightSum(cluster);
			result += ((withinClusterEdgesWeightSum / allEdgesWeightSum)
						- Math.pow((attachedEdgesWeightSum / allEdgesWeightSum), 2));
		}
		return result;
	}

	private Double getGraphEdgesWeightSum(SimpleWeightedGraph<Tag, DefaultWeightedEdge> graph) {
		Double result = 0.0;
		for (DefaultWeightedEdge edge : graph.edgeSet()) {
			result += graph.getEdgeWeight(edge);
		}
		return result;
	}

	private Double getAttachedEdgesWeightSum(SimpleWeightedGraph<Tag, DefaultWeightedEdge> graph) {
		Double result = 0.0;
		for (Tag tag : graph.vertexSet()) {
			for (DefaultWeightedEdge edge : graph.edgesOf(tag)) {
				result += graph.getEdgeWeight(edge);
			}
		}
		return result;
	}

}
