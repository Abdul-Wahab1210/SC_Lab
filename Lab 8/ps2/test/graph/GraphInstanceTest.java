package graph;

import static org.junit.Assert.*;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import org.junit.Test;

public abstract class GraphInstanceTest {

    public abstract Graph<String> emptyInstance();

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    // Tests for add()
    
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected true on adding a new vertex", graph.add("A"));
        assertTrue("expected vertex to be in the graph", graph.vertices().contains("A"));
    }
    
    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertFalse("expected false on adding a duplicate vertex", graph.add("A"));
    }
    
    // Tests for set()
    
    @Test
    public void testSetNewEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals("expected 0 as previous weight", 0, graph.set("A", "B", 5));
        assertEquals("expected weight of edge A->B to be 5", (Integer) 5, graph.targets("A").get("B"));
    }
    

    // Tests for remove()
    
    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("expected true on removing existing vertex", graph.remove("A"));
        assertFalse("expected vertex to be removed", graph.vertices().contains("A"));
    }

    // Tests for vertices()
    
    @Test
    public void testVerticesAfterAdding() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        Set<String> expectedVertices = Set.of("A", "B");
        assertEquals("expected set of vertices to contain A and B", expectedVertices, graph.vertices());
    }

    // Tests for sources()
    
    @Test
    public void testSourcesOfVertexWithNoIncomingEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertEquals("expected no sources for A", Collections.emptyMap(), graph.sources("A"));
    }

    

    // Tests for targets()
    
    @Test
    public void testTargetsOfVertexWithNoOutgoingEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertEquals("expected no targets for A", Collections.emptyMap(), graph.targets("A"));
    }
}
