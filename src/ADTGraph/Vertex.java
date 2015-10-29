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

    private ArrayList<String> attributeNames = new ArrayList<>();
    private ArrayList<Integer> attributeValues = new ArrayList<>();

    private static final ArrayList<String> vertexes = new ArrayList<>();
    // ##########################################
    // methods
    // ##########################################
    private Vertex(String name) {
        vertexes.add(name);
        vertexName = name;
    }

    public static Vertex createV(String name) {
        if (vertexes.contains(name)) return null;
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
        attributeNames.add(name);
        attributeValues.add(value);
    }

    Integer getValue(String name) {
        int attributeIndex = attributeNames.indexOf(name);
        if (attributeIndex != -1)
            return attributeValues.get(attributeIndex);
        return null;
    }

    String getName() { return vertexName; }
}
