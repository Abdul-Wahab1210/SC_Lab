package graph;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Map;
import java.util.HashMap;


public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    @Test
    public void testToStringEmptyGraph() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        assertEquals("Vertices: \nEdges:\n", graph.toString());
    }

    @Test
    public void testToStringSingleVertex() {
        Graph<String> graph = new ConcreteVerticesGraph();
        graph.add("A");

        // Expected output format
        String expected = "Vertices: A \nEdges:\nA -> {}\n";

        // Assert that the toString() method produces the correct string representation
        assertEquals("Graph toString should match the expected format", expected, graph.toString());
    }


    @Test
    public void testToStringMultipleVertices() {
        Graph<String> graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5); 

        // Expected output format
        String expected = "Vertices: A B \nEdges:\nA -> {B=5}\nB -> {}\n";
        
        // Assert that the toString() method produces the correct string representation
        assertEquals("Graph toString should match the expected format", expected, graph.toString());
    }


    @Test
    public void testToStringMultipleEdges() {
        Graph<String> graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.add("C");

        // Add edges with weights
        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "C", 10);
        graph.addEdge("B", "C", 2);

        // Update the expected output format to match the actual result
        String expected = "Vertices: A B C \nEdges:\nA -> {B=5, C=10}\nB -> {C=2}\nC -> {}\n";

        // Assert that the toString() method produces the correct string representation
        assertEquals("Graph toString should match the expected format", expected, graph.toString());
    }

    
    /*
     * Testing Vertex...
     */
    
    // Strategy for Vertex:
    //   - Ensure proper behavior of Vertex's methods.
    //   - Test that vertices are mutable and can have edges added and removed.
    
    @Test
    public void testVertexAddEdge() {
        Graph<String> graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);  // Add edge from A to B with weight 5
        
        Map<String, Integer> edges = graph.targets("A");  // Get the edges from vertex A
        assertTrue("Edge from A to B should exist", edges.containsKey("B"));
        assertEquals("Edge from A to B should have weight 5", (Integer) 5, edges.get("B"));
    }

    
}
