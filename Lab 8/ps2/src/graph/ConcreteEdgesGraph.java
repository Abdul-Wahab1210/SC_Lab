package graph;

import java.util.*;

/**
 * An implementation of a directed weighted graph.
 * 
 * <p>The graph is represented as a set of vertices and edges. Each edge connects
 * two vertices and has an associated weight.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   The vertices are represented by a set of strings, and the edges are represented
    //   by a list of Edge objects, each of which connects two vertices with a specific weight.
    //
    // Representation invariant:
    //   vertices != null && edges != null
    //   Each edge connects vertices that exist in the set of vertices
    //   No duplicate edges between the same pair of vertices.
    //
    // Safety from rep exposure:
    //   vertices and edges are private, and their references are not exposed to the outside.
    //   Methods return copies or immutable views to ensure that the representation cannot be modified.

    /**
     * Creates a new empty graph.
     */
    public ConcreteEdgesGraph() {
        // Nothing to do, the fields are initialized inline.
    }

    /**
     * Checks that the representation invariant holds.
     */
    private void checkRep() {
        assert vertices != null : "vertices cannot be null";
        assert edges != null : "edges cannot be null";
        
        // Ensure that all edges have valid source and target vertices
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource()) : "source vertex not in set of vertices";
            assert vertices.contains(edge.getTarget()) : "target vertex not in set of vertices";
        }

        // Ensure there are no duplicate edges between the same pair of vertices
        Set<String> seenEdges = new HashSet<>();
        for (Edge edge : edges) {
            String edgePair = edge.getSource() + "->" + edge.getTarget();
            assert !seenEdges.contains(edgePair) : "duplicate edge detected between " + edge.getSource() + " and " + edge.getTarget();
            seenEdges.add(edgePair);
        }
    }

    @Override
    public boolean add(String vertex) {
        checkRep();
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        checkRep();
        return true;
    }

    @Override
    public int set(String source, String target, int weight) {
        checkRep();
        if (weight < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative");
        }
        Edge edge = new Edge(source, target, weight);
        for (Edge e : edges) {
            if (e.equals(edge)) {
                int oldWeight = e.getWeight();
                edges.remove(e);
                edges.add(edge);
                checkRep();
                return oldWeight;
            }
        }
        edges.add(edge);
        checkRep();
        return 0;
    }

    @Override
    public boolean remove(String vertex) {
        checkRep();
        if (!vertices.contains(vertex)) {
            return false;
        }
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        checkRep();
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public Map<String, Integer> sources(String target) {
        checkRep();
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return Collections.unmodifiableMap(sources);
    }

    @Override
    public Map<String, Integer> targets(String source) {
        checkRep();
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return Collections.unmodifiableMap(targets);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Edges: ");
        for (Edge edge : edges) {
            sb.append(edge.toString()).append(" ");
        }
        return sb.toString();
    }
}

/**
 * Represents an edge in a weighted directed graph.
 * An edge connects a source vertex to a target vertex with a specified weight.
 */
class Edge {
    
    private final String source;
    private final String target;
    private final int weight;
    
    // Abstraction function:
    //   An Edge connects a source vertex to a target vertex with a specific weight.
    // Representation invariant:
    //   source != null && target != null
    //   weight >= 0
    // Safety from rep exposure:
    //   All fields are final and private, ensuring immutability.

    /**
     * Creates a new Edge.
     * @param source the source vertex
     * @param target the target vertex
     * @param weight the weight of the edge
     */
    public Edge(String source, String target, int weight) {
        if (source == null || target == null || weight < 0) {
            throw new IllegalArgumentException("Invalid edge parameters");
        }
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    /**
     * Gets the source vertex of the edge.
     * @return the source vertex
     */
    public String getSource() {
        return source;
    }

    /**
     * Gets the target vertex of the edge.
     * @return the target vertex
     */
    public String getTarget() {
        return target;
    }

    /**
     * Gets the weight of the edge.
     * @return the weight of the edge
     */
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " -> " + target + " [weight=" + weight + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return weight == edge.weight &&
               source.equals(edge.source) &&
               target.equals(edge.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, weight);
    }
}
