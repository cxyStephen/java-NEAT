import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

public class Genome {

    NEATConfig config;

    TreeSet<Node> nodes;
    Map<Connection, Connection> connections; //java set interface has no get method
    double fitness;

    Random rng = new Random();

    public Genome(NEATConfig config) {
        nodes = new TreeSet<>();
        connections = new HashMap<>();
        this.config = config;
    }

    public void mutate() { //page 107
        mutateConnectionWeight();
        mutateAddConnection();
        mutateAddNode();
    }

    public Genome cross(Genome other) {
        //disjoint and excess genes are inherited from more fit parent
        Genome fitterParent = this.fitness > other.fitness ? this : other;
        Genome otherParent = fitterParent == this ? other : this;

        //if the parents are of equal fitness, disjoint genes are randomly inherited
        double inheritDisjoint = this.fitness == other.fitness ? 0.5 : 1;

        Genome child = new Genome(config);

        //randomly inherit matching genes and inherit disjoint genes from fitter parent
        for (Connection connection : fitterParent.connections.keySet()) {
            Connection otherConnection = otherParent.connections.get(connection);
            Connection childConnection = connection.cross(otherConnection, inheritDisjoint);
            child.addConnection(childConnection);
        }

        //randomly inherit disjoint genes from the other equal fitness parent
        if (this.fitness == other.fitness) {
            for (Connection connection : otherParent.connections.keySet()) {
                if (fitterParent.connections.get(connection) == null) {
                    Connection childConnection = connection.cross(null, inheritDisjoint);
                    child.addConnection(childConnection);
                }
            }
        }

        return child;
    }

    double compatibilityWith(Genome other) {
        //measure the compatibility distance between this genome and another
        double excessCoeff = config.getExcessCoefficient();
        double differenceCoeff = config.getDifferenceCoefficient();

        //calculate disjoint and excess genes and average weight diff of matching genes
        int matchingGenes = 0;
        double totalWeightDiff = 0;
        for (Connection connection : this.connections.keySet()) {
            if (other.connections.keySet().contains(connection)) {
                matchingGenes++;

                double thisWeight = connection.weight;
                double otherWeight = other.connections.get(connection).weight;
                totalWeightDiff += Math.abs(thisWeight - otherWeight);
            }
        }

        int disjointGenes = this.connections.size() + other.connections.size() - 2 * matchingGenes;
        double avgWeightDiff = totalWeightDiff / matchingGenes;

        //normalize for genome size (N = larger of the genomes or 1 if both are small)
        int thisSize = this.connections.size();
        int otherSize = other.connections.size();
        int normalizingFactor = Math.max(thisSize, otherSize);
        if (thisSize < 20 && otherSize < 20)
            normalizingFactor = 1;

        //return the compatibility distance delta
        return (excessCoeff * disjointGenes) / normalizingFactor + (differenceCoeff * avgWeightDiff);
    }

    void mutateConnectionWeight() {
        for (Connection connection : connections.keySet()) {
            if (rng.nextDouble() < config.getWeightMutationRate())
                connection.perturb();
        }
    }

    void mutateAddConnection() {
        //chance to mutate
        if (rng.nextDouble() < config.getAddConnectionMutationRate())
            return;

        //choose two valid random nodes and make a connection TODO: what's the weight?
        Connection newConnection;
        Node in;
        Node out;
        do {
            //select two nodes that aren't on the same layer
            do {
                in = randomNode();
                out = randomNode();
            } while (in.layer == out.layer);

            //the lower layer node should point to the higher layer
            if(in.layer > out.layer) {
                Node temp = in;
                in = out;
                out = temp;
            }
            newConnection = new Connection(config, in, out);
        //reroll if the connection already exists
        } while (connections.containsKey(newConnection));

        connections.put(newConnection, newConnection);
    }

    void mutateAddNode() {
        //chance to mutate
        if (rng.nextDouble() < config.getAddNodeMutationRate())
            return;

        //can't add a node if there are no connections to split
        if (connections.keySet().size() == 0)
            return;

        //choose a random connection to disable and split
        Connection disabled = randomConnection().disable();
        //TODO: make packages so this import doesnt look dumb (and other reasons)
        int newLayer = disabled.in.layer/2 + disabled.out.layer/2;
        Node newNode = new Node(Node.NodeType.HIDDEN, newLayer);

        //add new connection from in->new (weight = 1) and new->out (weight = weight)
        Connection toNew = new Connection(config, disabled.in, newNode);
        Connection fromNew = new Connection(config, newNode, disabled.out, disabled.weight);
        addConnection(toNew);
        addConnection(fromNew);
    }

    public void propagateInputs() {
        for(Node node : nodes)
            node.propagate(connections);
    }

    void addConnection(Connection connection) {
        if (connection == null)
            return;

        connections.put(connection, connection);
        nodes.add(connection.in);
        nodes.add(connection.out);
    }

    Node randomNode() {
        //generate a random index and iterate to that index
        int randomIndex = rng.nextInt(nodes.size());
        Node random = nodes.first();
        for(int i = 0; i < randomIndex; i++)
            random = nodes.higher(random);
        return random;
    }

    Connection randomConnection() {
        //generate a random index and iterate to that index
        int randomIndex = rng.nextInt(connections.size());
        int counter = 0;
        for(Connection connection : connections.keySet()) {
            if(counter == randomIndex)
                return connection;
            counter++;
        }
        return null; //should be unreachable
    }
}
