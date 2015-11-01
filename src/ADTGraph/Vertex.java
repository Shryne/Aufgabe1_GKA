package ADTGraph;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by remen on 27.10.15.
 */
public class Vertex {
    // ##########################################
    // vars
    // ##########################################
    private final String vertexName;

    private static final ArrayList<Vertex> vertexes = new ArrayList<>();
    // ##########################################
    // methods
    // ##########################################
    private Vertex(String name) {
        vertexes.add(this);
        vertexName = name;
    }

    public static Vertex createV(String name) {
        if (name == null || name.equals("") || name.contains(" "))
            return null;

        Vertex output = getVertex(name);
        return (output == null) ? (new Vertex(name)) : (output);
    }

    String getName() { return vertexName; }
    // ##########################################
    // bonus
    // ##########################################
    @Override
    public String toString() {
        return "V(" + vertexName + ")";
    }
    // ##########################################
    // invisible
    // ##########################################
    private static Vertex getVertex(String name) {
        for (Vertex v : vertexes)
            if (v.getName().equals(name)) return v;

        return null;
    }
}
