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
    private class Attribute {
        public final String name;
        public final int value;

        public Attribute(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    private final String name;
    private final ArrayList<Attribute> attributes = new ArrayList<>();

    private static final ArrayList<String> vertexes = new ArrayList<>(64);
    // ##########################################
    // methods
    // ##########################################
    private Vertex(String name) {
        this.name = name;
    }

    public static Vertex createV(String name) {
        if (vertexes.contains(name)) return null;
        vertexes.add(name);
        return new Vertex(name);
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
    void addAttribute(String name, int value) {
        attributes.add(new Attribute(name, value));
    }

    Integer getValue(String name) {
        for (Attribute a : attributes)
            if (a.name.equals(name)) return a.value;
        return null;
    }

    String getName() { return name; }
}
