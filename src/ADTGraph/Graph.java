package ADTGraph;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by remen on 27.10.15.
 */
public class Graph {
    // ##########################################
    // vars
    // ##########################################
    private ArrayList<Vertex> vertexes = new ArrayList<Vertex>(30);

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
        for (Vertex v : vertexes)
            if (vertex.getName().equals(v.getName())) return this;

        vertexes.add(vertex); // direction, edge etc?
        return this;
    }

    public Graph deleteVertex(Vertex vertex) {
        if (vertexes.size() > 1) vertexes.remove(vertex);
        return null;
    }

    public Graph addEdge(Vertex v1, Vertex v2) {
        return null;
    }

    public Graph setAtE(Vertex v1, Vertex v2, String name, int value) {
        return null;
    }

    public Graph setAtV(Vertex vertex, String name, int value) {
        return null;
    }

    public Graph importG(String filename) {
        return null;
    }

    public File exportG(String filename) {
        return null;
    }

    public ArrayList<Vertex> getIncident(Vertex vertex) {
        return null;
    }

    public ArrayList<Vertex> getAdjacent(Vertex vertex) {
        return null;
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

}