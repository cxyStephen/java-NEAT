public class Node {

    static int globalId = 0;

    public enum NodeType {
        SENSOR,
        HIDDEN,
        OUTPUT
    }

    int id;
    NodeType type;

    public Node(NodeType type) {
        this.type = type;
        this.id = globalId++;
    }
}
