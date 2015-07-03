package com.zeef.tagclustering.partitioning;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.zeef.tagclustering.model.Tag;


public class SpectralBisection {

	private static final Integer MAX_CLUSTERS = 10;

	/**
	 * This function creates a Laplacian Matrix as stated in [2]. (See thesis document)
	 */
	public Double[][] createLaplacianMatrix(SimpleWeightedGraph<Tag, DefaultWeightedEdge> graph) {
		Object[] vertices = graph.vertexSet().toArray();
		Integer verticesSize = graph.vertexSet().size();
        Double[][] matrix = new Double[verticesSize][verticesSize];
        for (int i = 0; i < verticesSize; i++) {
            for (int j = 0; j < verticesSize; j++) {
                if (i == j) {
                    matrix[i][j] = new Double(graph.degreeOf((Tag) vertices[i]));
                } else if (graph.getEdge((Tag) vertices[i], (Tag) vertices[j]) != null) {
                    matrix[i][j] = new Double(-1);
                } else {
                	matrix[i][j] = new Double(0);
                }
            }
        }
        return matrix;
    }

	public Double[][] createEigenVector(Double[][] laplacianMatrix) {
		Integer matrixDimension = laplacianMatrix.length;
        Double[][] eigenVector = new Double[matrixDimension][matrixDimension];
        Boolean issymmetric = true;
        for (int j = 0; (j < matrixDimension) & issymmetric; j++) {
            for (int i = 0; (i < matrixDimension) & issymmetric; i++) {
                issymmetric = (laplacianMatrix[i][j] == laplacianMatrix[j][i]);
            }
        }
        if (issymmetric) {
            for (int i = 0; i < matrixDimension; i++) {
                System.arraycopy(laplacianMatrix[i], 0, eigenVector[i], 0, matrixDimension);
            }
        }
        return eigenVector;
    }

}
