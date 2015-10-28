package ADTGraph;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by remen on 27.10.15.
 */
public class Graph {
    // ##########################################
    // vars
    // ##########################################
    private class Attributes {
        public final ArrayList<String> names = new ArrayList<>();
        public final ArrayList<Integer> values = new ArrayList<>();
    }

    private class Range {
        public final int min, max;

        public Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    private ArrayList<Vertex> vertexes = new ArrayList<>();

    private ArrayList<Vertex> edges = new ArrayList<>();
    private ArrayList<Attributes> edgeAttributes = new ArrayList<>();

    private enum GraphVariants { GRAPH, DIGRAPH, }
    private final GraphVariants graphVariant = GraphVariants.GRAPH;
    // ##########################################
    // methods
    // ##########################################
    private Graph(Vertex vertex) {
        vertexes.add(vertex);
    }
    public static Graph createG(Vertex vertex) {
        return new Graph(vertex);
    }

    public Graph addVertex(Vertex vertex) {
        vertexes.add(vertex);
        return this;
    }

    public Graph deleteVertex(Vertex vertex) {
        if (vertexes.size() > 1) {
            vertexes.remove(vertex);
            deleteEdges(vertex);
        }
        return this;
    }

    public Graph addEdge(Vertex v1, Vertex v2) {
        if (vertexes.contains(v1) && vertexes.contains(v2)) {
            edges.add(v1);
            edges.add(v2);
        }
        return this;
    }

    public Graph setAtE(Vertex v1, Vertex v2, String name, int value) {
        Range range = getEdgeIndex(v1, v2, 0);
        if (range != null) {
            edgeAttributes.get(range.min).names.add(name);
            edgeAttributes.get(range.min).values.add(value);
        }
        return this;
    }

    public Graph setAtV(Vertex vertex, String name, int value) {
        if (vertexes.contains(vertex)) vertex.addAttribute(name, value);
        return this;
    }

    public Graph importG(String filename) {
        return null;
    }

    public File exportG(String filename) {
        return null;
    }

    public ArrayList<Vertex> getIncident(Vertex vertex) {
        if (vertex == null) return null;
        ArrayList<Vertex> incidents = new ArrayList<>();

        for (int i = 0; i < vertexes.size(); i += 2) { // vertex --> others
            if (vertexes.get(i) == vertex) {
                incidents.add(vertex);              // vertex
                incidents.add(vertexes.get(i + 1)); // other
            }
        }

        for (int i = 1; i < vertexes.size(); i += 2) { // others --> vertex
            if (vertexes.get(i) == vertex) {
                incidents.add(vertexes.get(i));
                incidents.add(vertex);
            }
        }
        return incidents;
    }

    public ArrayList<Vertex> getAdjacent(Vertex vertex) {
        if (vertex == null) return null;
        ArrayList<Vertex> adjacent = new ArrayList<>();

        for (int i = 0; i < vertexes.size(); i += 2)
            if (vertexes.get(i) == vertex)
                adjacent.add(vertexes.get(i + 1));

        for (int i = 1; i < vertexes.size(); i += 2)
            if (vertexes.get(i) == vertex)
                adjacent.add(vertexes.get(i));

        return adjacent;
    }

    public ArrayList<Vertex> getTarget(Vertex vertex) {
        return null;
    }

    public ArrayList<Vertex> getSource(Vertex vertex) {
        return null;
    }

    public ArrayList<Vertex> getEdges() {
        return null;
    }

    public ArrayList<Vertex> getVertexes() {
        return null;
    }

    public int getValE(Vertex v1, Vertex v2, String name) {
        return 0;
    }

    public int getValV(Vertex vertex, String name) {
        return 0;
    }
    // ##########################################
    // bonus
    // ##########################################
    @Override
    public String toString() {
        return null;
    }
    // ##########################################
    // invisible
    // ##########################################
    private void deleteEdges(Vertex vertex) {

    }

    private Range getEdgeIndex(Vertex v1, Vertex v2, int startIndex) {
        for (int i = startIndex; i < edges.size() - 1; i++)
            if (edgeEquals(v1, v2, i)) return new Range(i, i + 1);
        return null;
    }

    private boolean edgeEquals(Vertex v1, Vertex v2, int index) {
        return (edges.get(index) == v1 && edges.get(index + 1) == v2) ||
                (edges.get(index) == v2 && edges.get(index + 1) == v1);
    }
}