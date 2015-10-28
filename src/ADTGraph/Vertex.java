package ADTGraph;

/**
 * Created by remen on 27.10.15.
 */
public class Vertex {
    // ##########################################
    // vars
    // ##########################################
    private final String name;

    // ##########################################
    // methods
    // ##########################################
    private Vertex(String nName) {
        name = nName;
    }

    public static Vertex createV(String name) {
        // TODO
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
    String getName() {
        return name;
    }
}
