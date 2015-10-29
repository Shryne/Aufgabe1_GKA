package ADTGraph;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by remen on 27.10.15.
 */
public class Graph {
    // ##########################################
    // vars
    // ##########################################
    private class Edge {
        public final Vertex source;
        public final Vertex target;

        public ArrayList<String> names = new ArrayList<>();
        public ArrayList<Integer> values = new ArrayList<>();

        public Edge(Vertex source, Vertex target) {
            this.target = target;
            this.source = source;
        }

        public void addAttribute(String name, int value) {
            names.add(name);
            values.add(value);
        }
    }

    private ArrayList<Vertex> vertexes = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    // ##########################################
    // methods
    // ##########################################
    private Graph(Vertex vertex) {
        vertexes.add(vertex);
    }
    private enum GraphVariants { GRAPH, DIGRAPH, }
    private final GraphVariants graphVariant = GraphVariants.GRAPH;
    public static Graph createG(Vertex vertex) {
        return new Graph(vertex);
    }

    public Graph addVertex(Vertex vertex) {
        vertexes.add(vertex);
        return this;
    }

    public Graph deleteVertex(Vertex vertex) {
        if (vertexes.size() > 1) { // precondition
            deleteEdges(vertex);
            vertexes.remove(vertex);
        }
        return this;
    }

    public Graph addEdge(Vertex v1, Vertex v2) {
        if (vertexes.contains(v1) && vertexes.contains(v2))
            edges.add(new Edge(v1, v2));

        return this;
    }

    public Graph setAtE(Vertex v1, Vertex v2, String name, int value) {
        for (Edge e : edges)
            if (e.source == v1 && e.target == v2)
                e.addAttribute(name, value);
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
        if (vertex == null) return null; // precondition
        ArrayList<Vertex> incidents = new ArrayList<>();

        for (Edge e : edges)
            if (e.source == vertex)
                addTwo(incidents, e.source, e.target);

        return incidents;
    }

    public ArrayList<Vertex> getAdjacent(Vertex vertex) {
        if (vertex == null) return null; // precondition
        ArrayList<Vertex> adjacent = new ArrayList<>();

        for (Edge e : edges)
            if      (e.source == vertex) adjacent.add(e.target);
            else if (e.target == vertex) adjacent.add(e.source);

        return adjacent;
    }

    public ArrayList<Vertex> getTarget(Vertex vertex) {
        ArrayList<Vertex> targets = new ArrayList<>();

        for (Edge e : edges)
            if (e.source == vertex)
                targets.add(e.target);

        return targets;
    }

    public ArrayList<Vertex> getSource(Vertex vertex) {
        ArrayList<Vertex> sources = new ArrayList<>();

        for (Edge e : edges)
            if (e.target == vertex)
                sources.add(e.source);

        return sources;
    }

    public ArrayList<Vertex> getEdges() {
        ArrayList<Vertex> edgeVertices = new ArrayList<>();

        for (Edge e : edges) {
            edgeVertices.add(e.source);
            edgeVertices.add(e.target);
        }

        return edgeVertices;
    }

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    public int getValE(Vertex v1, Vertex v2, String name) {
        for (Edge e : edges)
            if (e.source == v1 && e.target == v2) {
                int attributeIndex = e.names.indexOf(name);
                return e.values.get(attributeIndex);
            }

        return 0;
    }

    public int getValV(Vertex vertex, String name) {
        for (Vertex v : vertexes)
            if (v.getName().equals(vertex.getName()))
                return vertex.getValue(name);

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
        Iterator<Edge> i = edges.iterator();

        while (i.hasNext()) {
            Edge actualEdge = i.next();
            if (actualEdge.source == vertex || actualEdge.target == vertex)
                i.remove();
        }
    }

    private void addTwo(List destiny, Object element1, Object element2) {
        destiny.add(element1);
        destiny.add(element2);
    }
}