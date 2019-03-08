package com.cxystephen.NEAT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Node implements Comparable<Node>{

    static int globalId = 0;

    public enum NodeType {
        SENSOR,
        HIDDEN,
        OUTPUT
    }

    public static final int INPUT_lAYER = Integer.MIN_VALUE;
    public static final int OUTPUT_LAYER = Integer.MAX_VALUE;

    NodeType type;
    int id;
    int layer;
    double value;
    List<Node> connected; //all nodes to forward propagate to

    private Node() {}
    //TODO: bias nodes??

    public Node(NodeType type, int layer) {
        this.type = type;
        this.id = globalId++;
        this.layer = layer;
        connected = new ArrayList<>();
    }

    public void propagate(Map<Connection, Connection> connections) {
        if (type != NodeType.SENSOR)
            value = sigmoid(value);

        //forward propagate to all connected nodes
        for(Node other : connected) {
            Connection test = new Connection(null, this, other);
            Connection actual = connections.get(test);
            if(actual != null)
                other.value += value * actual.weight;
        }
    }

    public double sigmoid(double x) {
        return 2 / (1 + Math.exp(-4.9 * x)) - 1;
    }

    @Override
    public int compareTo(Node o) {
        //order by layer and then (arbitrarily) by id
        if (this.id == o.id)
            return 0;
        if (this.layer == o.layer)
            return this.id - o.id;
        return this.layer - o.layer;
    }
}
