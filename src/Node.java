import java.util.List;

public class Node {

    public enum NodeType {
        SENSOR,
        HIDDEN,
        OUTPUT
    }

    int id;
    NodeType type;
}
