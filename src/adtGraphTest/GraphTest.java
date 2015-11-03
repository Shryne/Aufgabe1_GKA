package adtGraphTest;

import ADTGraph.Graph;
import ADTGraph.Vertex;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

import static ADTGraph.Graph.importG;
import static ADTGraph.Vertex.createV;
import static ADTGraph.Graph.createG;
import static org.junit.Assert.*;

/**
 * Created by remen on 27.10.15.
 */
public class GraphTest {

    @Test
    public void testCreateV() throws Exception {
        // normal
        assertNotEquals(createV("num"), null);
        assertNotEquals(createV("NUM"), null);
        assertNotEquals(createV("1"), null);
        assertNotEquals(createV("1Z9!"), null);
        assertNotEquals(createV("f_f"), null);
        assertNotEquals(createV("kmfslfnapckjmn32ß4u23asd!dSA_FDSAß9hj9ßfvc__"), null);
        assertNotEquals(createV("____________"), null);

        // double
        Vertex v1 = createV("A");
        Vertex v2 = createV("A");
        assertTrue(v1 == v2);

        Vertex v3 = createV("A");
        Vertex v4 = createV("B");
        assertTrue(v2 == v3);
        assertFalse(v3 == v4);

        // fail
        assertEquals(createV(null), null);
        assertEquals(createV(""), null);
        assertEquals(createV(" "), null);
        assertEquals(createV("fsdn "), null);
        assertEquals(createV("sad_fdskifsd fds"), null);
        assertEquals(createV(" d"), null);
    }

    @Test
    public void testCreateG() throws Exception {
        // normal
        assertNotEquals(createG(createV("hey"), true), null);
        assertNotEquals(createG(createV("1424"), false), null);

        // fail
        assertEquals(createG(null, false), null);
    }

    @Test
    public void addDeleteGetVertices() throws Exception {
        // normal
        Vertex v1 = createV("zu");
        Vertex v2 = createV("bu");
        Vertex v3 = createV("fmfb");
        Vertex v4 = createV("1392");
        Graph graph1 = createG(v1, false);

        graph1.addVertex(v2);
        graph1.addVertex(v3);
        graph1.addVertex(v4);

        List<Vertex> result = toList(v4, v2, v3, v1);
        assertTrue(arrayEquals(result, graph1.getVertexes()));

        graph1.deleteVertex(v4);
        graph1.deleteVertex(v3);
        graph1.deleteVertex(v2);
        graph1.deleteVertex(v1);

        // differend order
        graph1.addVertex(v2);
        graph1.addVertex(v3);
        graph1.addVertex(v4);

        result = toList(v4, v2, v3, v1);
        assertTrue(arrayEquals(result, graph1.getVertexes()));

        graph1.deleteVertex(v2);
        graph1.deleteVertex(v3);
        graph1.deleteVertex(v1);
        assertTrue(arrayEquals(toList(v4), graph1.getVertexes()));

        // delete last
        graph1.deleteVertex(v4);
        assertTrue(arrayEquals(toList(v4), graph1.getVertexes()));

        // delete inexistent and more
        graph1.deleteVertex(v2);
        graph1.deleteVertex(v2);

        result = toList(v4);
        assertTrue(arrayEquals(result, graph1.getVertexes()));

        // add same twice
        graph1.addVertex(v1);
        graph1.addVertex(v2);

        result = toList(v1, v2, v4);
        assertTrue(arrayEquals(result, graph1.getVertexes()));

        // add same in two graphs
        Graph graph2 = createG(v4, true);
        graph2.addVertex(v2);

        result = toList(v1, v2, v4);
        assertTrue(arrayEquals(result, graph1.getVertexes()));

        result = toList(v2, v4);
        assertTrue(arrayEquals(result, graph2.getVertexes()));
    }


    @Test
    public void testImportG() throws Exception {
        Graph graph = importG("graph_03.txt");

        Vertex s = v("s");
        Vertex u = v("u");
        Vertex x = v("x");
        Vertex y = v("y");
        Vertex v = v("v");

        List<Vertex> result = toList(
                s, u,
                s, x,
                u, v,
                u, x,
                x, u,
                x, y,
                y, s,
                y, v,
                v, y);

        assertTrue(edgeEquals(graph.getEdges(), result));
        assertTrue(arrayEquals(graph.getVertexes(), result));

        assertEquals(graph.getValE(s, u, "0"), 10);
        assertEquals(graph.getValE(s, x, "0"), 5);
    }

    @Test
    public void testExportG() throws Exception {
        // TODO
    }

    @Test
    public void testGetSourceTargetEdgesInAd() throws Exception {
        Vertex v1 = createV("A");
        Vertex v2 = createV("B");
        Vertex v3 = createV("C");
        Vertex v4 = createV("D");
        Vertex v5 = createV("1");

        Graph graph = createG(v1, false);

        // undirected
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);

        graph.addEdge(v1, v2);
        graph.addEdge(v1, v2);
        graph.addEdge(v2, v1);
        graph.addEdge(v4, v5);
        graph.addEdge(v4, v1);
        graph.addEdge(v2, v4);

        List<Vertex> result = toList(v1, v4);
        assertTrue(arrayEquals(graph.getSource(v2), result));
        assertTrue(arrayEquals(graph.getTarget(v2), result));

        result = toList(v1, v2, v4, v5, v4, v1, v2, v4);
        assertTrue(arrayEquals(graph.getEdges(), result));

        result = toList(v1, v2, v1, v4);
        assertTrue(edgeEquals(graph.getIncident(v1), result));

        result = toList(v5, v1, v2);
        assertTrue(arrayEquals(graph.getAdjacent(v4), result));

        // directed
        graph = createG(v1, true);
        
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);

        graph.addEdge(v1, v2);
        graph.addEdge(v1, v2);
        graph.addEdge(v2, v1);
        graph.addEdge(v4, v5);
        graph.addEdge(v4, v1);
        graph.addEdge(v2, v4);

        result = toList(v1);
        assertTrue(arrayEquals(graph.getSource(v2), result));

        result = toList(v1, v4);
        assertTrue(arrayEquals(graph.getTarget(v2), result));

        result = toList(v1, v2, v2, v1, v4, v5, v4, v1, v2, v4);
        assertTrue(arrayEquals(graph.getEdges(), result));


        result = toList(v1, v2, v2, v1, v1, v4);
        assertTrue(edgeEquals(graph.getIncident(v1), result));

        result = toList(v5, v1, v2);
        assertTrue(arrayEquals(graph.getAdjacent(v4), result));
    }

    @Test
    public void testGetSetValE() throws Exception {
        // undirected
        Vertex v1 = createV("_");
        Graph graph = createG(v1, false).addEdge(v1, v1);

        graph.setAtE(v1, v1, "_", -10);
        assertEquals(graph.getValE(v1, v1, "_"), -10);

        Vertex v2 = createV("N1");
        graph.addVertex(v2).addEdge(v1, v2);

        graph.setAtE(v1, v2, "N1", 30);
        graph.setAtE(v1, v2, "NB", 12);
        graph.setAtE(v1, v2, "NB", 23);

        assertEquals(graph.getValE(v1, v2, "N1"), 30);
        assertEquals(graph.getValE(v1, v2, "NB"), 12);
        assertEquals(graph.getValE(v2, v1, "N1"), 30);
        assertEquals(graph.getValE(v1, v2, "_"), 0);

        // directed
        Vertex v3 = createV("_");
        Graph graph1 = createG(v1, true)
                .addEdge(v1, v1).addEdge(v1, v3).addEdge(v3, v1);


        graph1.setAtE(v1, v1, "_", 49);
        assertEquals(graph1.getValE(v1, v1, "_"), 49);

        Vertex v4 = createV("B3");
        graph1.addVertex(v4).addEdge(v1, v4);
        graph1.setAtE(v1, v4, "Q3", 30);

        assertEquals(graph1.getValE(v1, v4, "Q3"), 30);
        assertEquals(graph1.getValE(v4, v1, "Q3"), 0);

    }

    @Test
    public void testSetGetValV() throws Exception {
        Vertex v1 = createV("A");
        Vertex v2 = createV("B");

        Graph graph1 = createG(v1, false);
        Graph graph2 = createG(v2, true);

        // undirected
        graph1.setAtV(v1, "Num1", 15);
        graph1.setAtV(v1, "Num2", 20);
        graph1.setAtV(v1, "B", -53);
        graph1.setAtV(v2, "al", 20);
        
        assertEquals(graph1.getValV(v1, "Num1"), 15);
        assertEquals(graph1.getValV(v1, "B"), -53);
        assertEquals(graph1.getValV(v1, "Num2"), 20);
        
        assertEquals(graph1.getValV(v1, "Num"), 0);
        assertEquals(graph1.getValV(v2, "al"), 0);

        assertEquals(graph1.getValV(null, "Num"), 0);
        assertEquals(graph1.getValV(v1, null), 0);

        // fail
        graph1.setAtV(v1, "", 10);
        graph1.setAtV(v1, " ", 13);
        graph1.setAtV(v1, "       ", 402);
        graph1.setAtV(v1, null, 30); // TODO

        assertEquals(graph1.getValV(v1, ""), 0);
        assertEquals(graph1.getValV(v1, " "), 0);
        assertEquals(graph1.getValV(v1, "       "), 0);
        assertEquals(graph1.getValV(v1, null), 0);

        // directed
        graph2.setAtV(v2, "Num1", 15);
        graph2.setAtV(v2, "Num2", 20);
        graph2.setAtV(v2, "B", -53);
        graph2.setAtV(v1, "al", 20);

        assertEquals(graph2.getValV(v2, "Num1"), 15);
        assertEquals(graph2.getValV(v2, "B"), -53);
        assertEquals(graph2.getValV(v2, "Num2"), 20);

        assertEquals(graph2.getValV(v2, "Num"), 0);
        assertEquals(graph2.getValV(v1, "al"), 0);
        
        // two graphs
        Vertex v3 = createV("Z");

        graph1.addVertex(v3);
        graph2.addVertex(v3);

        graph1.setAtV(v3, "C", 50);
        graph2.setAtV(v3, "S", 20);

        assertEquals(graph2.getValV(v3, "C"), 0);
        assertEquals(graph1.getValV(v3, "C"), 50);
        assertEquals(graph1.getValV(v3, "S"), 0);
        assertEquals(graph2.getValV(v3, "S"), 20);

        graph2.setAtV(v3, "C", -2);
        assertEquals(graph1.getValV(v3, "C"), 50);
        assertEquals(graph2.getValV(v3, "C"), -2);
    }
    // ####################################################
    // private helper
    // ####################################################
    private static boolean arrayEquals(List<Vertex> arr1, List<Vertex> arr2) {
        Iterator<Vertex> i = arr2.iterator();

        while (i.hasNext()) {
            Vertex actualVertex = i.next();
            if (!arr1.contains(actualVertex)) return false;
            else arr1.remove(actualVertex);
        }
        return true;
    }

    private static boolean edgeEquals(List<Vertex> arr1, List<Vertex> arr2) {
        if (arr1.size() != arr2.size()) return false;

        Iterator<Vertex> i = arr1.iterator();
        while (i.hasNext()) {
            Vertex actualVertex = i.next();
            if (!arr2.contains(i.next())) return false;
            else arr2.remove(actualVertex);
        }
        return true;
    }
    
    private static List<Vertex> toList(Vertex... vertex) {
        return new LinkedList<>(Arrays.asList(vertex));
    }

    private static Vertex v(String name) {
        return createV(name);
    }
}