package ADTGraph;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

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

        public int getValue(String name) {
            int attributeIndex = names.indexOf(name);
            if (attributeIndex == -1) return 0;

            return values.get(attributeIndex);
        }
    }

    private final boolean directedGraph;

    private ArrayList<Vertex> vertexes = new ArrayList<>();
    private ArrayList<ArrayList<String>> vertexNames = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> vertexValues = new ArrayList<>();

    private ArrayList<Edge> edges = new ArrayList<>();
    // ##########################################
    // methods
    // ##########################################

    /**
     * This private constructor makes importG a lot easier.
     */
    private Graph(boolean directed) { directedGraph = directed; }

    private Graph(Vertex vertex, boolean directed) {
        addVertex(vertex);
        directedGraph = directed;
    }

    public static Graph createG(Vertex vertex) {
        return createG(vertex, false);
    }

    public static Graph createG(Vertex vertex, boolean directed) {
        if (vertex == null) return null;
        return new Graph(vertex, directed);
    }

    public Graph addVertex(Vertex vertex) {
        vertexes.add(vertex);
        vertexNames.add(new ArrayList<>());
        vertexValues.add(new ArrayList<>());
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
        if (vertexes.contains(v1) && vertexes.contains(v2) &&
                !containsEdge(v1, v2, directedGraph))
            edges.add(new Edge(v1, v2));

        return this;
    }

    public Graph deleteEdge(Vertex v1, Vertex v2) {
        deleteEdges(v1);
        deleteEdges(v2);
        return this;
    }

    public Graph setAtE(Vertex v1, Vertex v2, String name, int value) { //TODO exception
        for (Edge e : edges)
            if (e.source == v1 && e.target == v2)
                e.addAttribute(name, value);
        return this;
    }

    public Graph setAtV(Vertex vertex, String name, int value) { //TODO exception
        if (vertex == null || name == null || name.equals("") || name.contains(" ")) return this;

        if (vertexes.contains(vertex)) addAttribute(vertex, name, value);
        return this;
    }

    public static Graph importG(String filename) throws IOException { //TODO exception
        Scanner input = new Scanner(new File(filename)).useDelimiter("\\s*[,\\n]\\s*");
        boolean directedGraph = importGraphVariation(input.nextLine());

        ArrayList<Vertex> vertexes = new ArrayList<>();
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();

        while (input.hasNextLine()) {
            vertexes.add(Vertex.createV(input.next()));
            vertexes.add(Vertex.createV(input.next()));

            values.add(new ArrayList<>());
            while (input.hasNextInt())
                values.get(values.size() - 1).add(input.nextInt());
        }

        Graph graph = new Graph(directedGraph);

        for (int i = 0; i < vertexes.size(); i += 2) {
            graph.addVertex(vertexes.get(i));
            graph.addVertex(vertexes.get(i +  1));

            graph.addEdge(vertexes.get(i), vertexes.get(i + 1));

            for (int j = 0; j < values.get(i / 2).size(); j++)
                graph.setAtE(vertexes.get(i), vertexes.get(i + 1), j + "", values.get(i / 2).get(j));
        }

        return graph;
    }

    public File exportG(String filename) throws IOException {
        if(isCorrectName(filename)) throw new IOException();
        filename += ".dot";

        Path outputFile = Paths.get(filename);
        BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8);

        String direction;
        if (directedGraph) {
            writer.write("digraph G { \n");
            direction = "->";
        } else {
            writer.write("graph G { \n");
            direction = "--";
        }

        for (Edge e : edges) {
            String attributes = "";
            for (int i = 0; i < e.names.size(); i++)
                attributes = vertexNames.get(i) + " " + vertexValues.get(i) + " ";

            writer.write(e.source.getName() + direction + e.target.getName() + "[label=\"" + attributes + "\"];\n");
        }

        writer.write("}\n");
        return new File(filename);
    }

    public ArrayList<Vertex> getIncident(Vertex vertex) {
        if (vertex == null) return null; // precondition
        ArrayList<Vertex> incidents = new ArrayList<>();

        for (Edge e : edges)
            if (e.source == vertex || e.target == vertex)
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
            else if (!directedGraph && e.target == vertex)
                targets.add(e.source);

        return targets;
    }

    public ArrayList<Vertex> getSource(Vertex vertex) {
        ArrayList<Vertex> sources = new ArrayList<>();

        for (Edge e : edges)
            if (e.target == vertex)
                sources.add(e.source);
            else if (!directedGraph && e.source == vertex)
                sources.add(e.target);

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
        return new ArrayList<>(vertexes);
    }

    public int getValE(Vertex v1, Vertex v2, String name) {
        for (Edge e : edges)
            if (e.source == v1 && e.target == v2)
                return e.getValue(name);
            else if (!directedGraph && e.source == v2 && e.target == v1)
                return e.getValue(name);


        return 0;
    }

    public int getValV(Vertex vertex, String name) {
        if (vertex == null || name == null) return 0;

        int vertexIndex = vertexes.indexOf(vertex);
        if (vertexIndex == -1) return 0;

        int attributeIndex = vertexNames.get(vertexIndex).indexOf(name);
        if (attributeIndex == -1) return 0;

        return vertexValues.get(vertexIndex).get(attributeIndex);
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

    private boolean containsEdge(Vertex v1, Vertex v2, boolean directedGraph) {
        if (directedGraph) return containsVertexCombo(v1, v2);

        return containsVertexCombo(v1, v2) || containsVertexCombo(v2, v1);
    }

    private boolean containsVertexCombo(Vertex v1, Vertex v2) {
        for (Edge e : edges)
            if (e.source == v1 && e.target == v2) return true;

        return false;
    }

    private void addTwo(List destiny, Object element1, Object element2) {
        destiny.add(element1);
        destiny.add(element2);
    }

    private void addAttribute(Vertex vertex, String name, int value) {
        int index = vertexes.indexOf(vertex);

        vertexNames.get(index).add(name);
        vertexValues.get(index).add(value);
    }

    private static Boolean importGraphVariation(String string) {
        if (string.equals("#gerichtet")) return true;
        if (string.equals("#ungerichtet")) return false;
        return null;
    }

    private static ArrayList<Vertex> importEdges(Scanner input) {
        ArrayList<Vertex> result = new ArrayList<>();

        while (input.hasNext())
            result.add(Vertex.createV(input.next()));


        return result;
    }

    private static boolean isCorrectName(String name) {
        return (name == null || name.equals("") || name.contains(" ") || name.contains(","));
    }
}