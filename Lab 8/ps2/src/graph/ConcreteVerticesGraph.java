package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    @Override
    public boolean add(String vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex label cannot be null");
        }
        for (Vertex v : vertices) {
            if (v.label.equals(vertex)) {
                return false; // Vertex already exists
            }
        }
        vertices.add(new Vertex(vertex));
        return true;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source or target vertex cannot be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative");
        }

        // Check if source and target vertices exist
        Vertex srcVertex = getVertexByLabel(source);
        Vertex tgtVertex = getVertexByLabel(target);
        
        if (srcVertex == null) {
            throw new IllegalArgumentException("Source vertex does not exist: " + source);
        }
        if (tgtVertex == null) {
            throw new IllegalArgumentException("Target vertex does not exist: " + target);
        }
        
        // Add the edge from source to target
        return srcVertex.addEdge(tgtVertex, weight);
    }

    @Override
    public boolean remove(String vertex) {
        Vertex vertexToRemove = getVertexByLabel(vertex);
        if (vertexToRemove == null) {
            return false;
        }

        vertices.remove(vertexToRemove);
        // Remove all edges pointing to this vertex (incoming edges)
        for (Vertex v : vertices) {
            v.removeEdgeTo(vertex);
        }
        return true;
    }

    @Override
    public Set<String> vertices() {
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex vertex : vertices) {
            vertexLabels.add(vertex.label);
        }
        return vertexLabels;
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> result = new HashMap<>();
        for (Vertex vertex : vertices) {
            Integer weight = vertex.getEdgeWeightTo(target);
            if (weight != null) {
                result.put(vertex.label, weight);
            }
        }
        return result;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> result = new HashMap<>();
        Vertex srcVertex = getVertexByLabel(source);
        if (srcVertex != null) {
            result = srcVertex.getEdges();
        }
        return result;
    }

    // Implement the addEdge method as required by the interface
    @Override
    public void addEdge(String source, String target, int weight) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source or target vertex cannot be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative");
        }

        Vertex srcVertex = getVertexByLabel(source);
        if (srcVertex == null) {
            srcVertex = new Vertex(source);
            vertices.add(srcVertex);  // Add source vertex if not found
        }

        Vertex tgtVertex = getVertexByLabel(target);
        if (tgtVertex == null) {
            tgtVertex = new Vertex(target);
            vertices.add(tgtVertex);  // Add target vertex if not found
        }

        // Add the edge from source to target with the specified weight
        srcVertex.addEdge(tgtVertex, weight);
    }

    // Helper method to get a vertex by its label
    private Vertex getVertexByLabel(String label) {
        for (Vertex vertex : vertices) {
            if (vertex.label.equals(label)) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ");
        for (Vertex vertex : vertices) {
            sb.append(vertex.label).append(" ");
        }
        sb.append("\nEdges:\n");
        for (Vertex vertex : vertices) {
            sb.append(vertex.label).append(" -> ");
            sb.append(vertex.getEdges()).append("\n");
        }
        return sb.toString();
    }

    // Representation Invariant check
    private void checkRep() {
        assert vertices != null : "vertices list cannot be null";
        Set<String> labels = new HashSet<>();
        for (Vertex v : vertices) {
            assert v.label != null : "vertex label cannot be null";
            assert labels.add(v.label) : "duplicate vertex label: " + v.label;
        }
    }

    static class Vertex {
        
        final String label;
        private final Map<String, Integer> edges = new HashMap<>();
        
        public Vertex(String label) {
            if (label == null) {
                throw new IllegalArgumentException("Vertex label cannot be null");
            }
            this.label = label;
        }
        
        public int addEdge(Vertex target, int weight) {
            if (target == null) {
                throw new IllegalArgumentException("Target vertex cannot be null");
            }
            if (weight < 0) {
                throw new IllegalArgumentException("Edge weight cannot be negative");
            }
            Integer previousWeight = edges.put(target.label, weight);
            return previousWeight != null ? previousWeight : 0;
        }

        public boolean removeEdgeTo(String targetLabel) {
            return edges.remove(targetLabel) != null;
        }

        public Map<String, Integer> getEdges() {
            return new HashMap<>(edges);
        }

        public Integer getEdgeWeightTo(String targetLabel) {
            return edges.get(targetLabel);
        }

        @Override
        public String toString() {
            return label + " -> " + edges;
        }
        
     // Remove edge method implementation
        public boolean removeEdge(Vertex targetVertex) {
            if (edges.containsKey(targetVertex)) {
                edges.remove(targetVertex);
                return true;
            }
            return false;
        }

    }
}
