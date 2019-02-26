import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Genome {

    Set<Node> nodes;
    Map<Connection, Connection> connections; //java set interface has no get method
    double fitness;

    Random rng = new Random();

    public Genome() {
        nodes = new HashSet<>();
        connections = new HashMap<>();
        fitness = -1; //TODO: not sure what this value should be
    }

    public Genome(Set<Node> nodes, Map<Connection, Connection> connections, double fitness) {
        this.nodes = nodes;
        this.connections = connections;
        this.fitness = fitness;
    }

    public void mutate() { //page 107
        mutateConnectionWeight();
        mutateAddConnection();
        mutateAddNode();
    }

    public Genome cross(Genome other) { //TODO: when do genes get disabled?
        //disjoint and excess genes are inherited from more fit parent
        Genome fitterParent = this.fitness > other.fitness ? this : other;
        Genome otherParent = fitterParent == this ? other : this;

        //if the parents are of equal fitness, disjoint genes are randomly inherited
        double inheritDisjoint = this.fitness == other.fitness ? 0.5 : 1;

        Genome child = new Genome();

        //randomly inherit matching genes and inherit disjoint genes from fitter parent
        for(Connection connection : fitterParent.connections.keySet()) {
            if(otherParent.connections.containsKey(connection)) {
                Genome randomParent = rng.nextDouble() < 0.5 ? fitterParent : otherParent;
                child.addConnection(randomParent.connections.get(connection));
            } else if (rng.nextDouble() < inheritDisjoint){
                child.addConnection(connection);
            }
        }

        //randomly inherit disjoint genes from the other equal fitness parent
        if(this.fitness == other.fitness) {
            for(Connection connection : otherParent.connections.keySet()) {
                if (!fitterParent.connections.containsKey(connection)
                        && rng.nextDouble() < inheritDisjoint)
                    child.addConnection(connection);
            }
        }

        return child;
    }

    void mutateConnectionWeight() {
        for(Connection connection : connections.keySet())
            connection.perturb();
    }

    void mutateAddConnection() {
        //chance to mutate
        if(rng.nextDouble() < 0.5) //TODO: config class to set chance
            return;

        //choose two valid random nodes and make a connection TODO: what's the weight?
        Connection newConnection;
        boolean valid;
        do {
            valid = true;

            Node in = randomNode();
            Node out = randomNode();
            newConnection = new Connection(in, out);

            if(in.equals(out) || connections.containsKey(newConnection))
                valid = false;  //TODO: there should be other invalid cases
            //can a connection create a cycle?
        } while(!valid);

        connections.put(newConnection, newConnection);
    }

    void mutateAddNode() {
        //chance to mutate
        if(rng.nextDouble() < 0.5) //TODO: config class to set chance
            return;

        //can't add a node if there are no connections to split
        if(connections.keySet().size() == 0)
            return;

        //choose a random connection to split
        Connection removed = connections.remove(randomConnection());

        //TODO: make packages so this import doesnt look dumb (and other reasons)
        Node newNode = new Node(Node.NodeType.HIDDEN);
        nodes.add(newNode);

        //add new connection from in->new (weight = 1)
        Connection toNew = new Connection(removed.in, newNode);
        //add new connection from new->out (weight = weight)
        Connection fromNew = new Connection(newNode, removed.out, removed.weight);
    }

    void addConnection(Connection connection) {
        connections.put(connection, connection);
        nodes.add(connection.in);
        nodes.add(connection.out);
    }

    //TODO: is there a way to do this under O(n) time?
    Node randomNode() {
        return null;
    }

    //TODO: is there a way to do this under O(n) time?
    Connection randomConnection() {
        return null;
    }
}
