import java.util.Random;

public class Connection {

    NEATConfig config;

    Node in;
    Node out;
    double weight;
    boolean isDisabled;

    Random rng = new Random();

    public Connection(NEATConfig config, Node in, Node out) {
        //create connection with default weight
        this.config = config;
        this.in = in;
        this.out = out;
        this.weight = 1; //TODO: rand value from -2 to 2 i think

        in.connected.add(out);
    }

    public Connection(NEATConfig config, Node in, Node out, double weight) {
        //create connection with custom weight
        this.config = config;
        this.in = in;
        this.out = out;
        this.weight = weight;

        in.connected.add(out);
    }

    public Connection(Connection parent) {
        //create connection by cloning
        this.in = parent.in;
        this.out = parent.out;
        this.weight = parent.weight;

        if (parent.isDisabled && rng.nextDouble() < config.getInheritDisabledRate())
            this.isDisabled = true;
    }

    public void perturb() {
        //TODO: i think slightly modify weight here?
    }

    public Connection cross(Connection other, double inheritDisjoint) {
        //if its a matching gene, return the cross of this and the other
        if (other != null) {
            Connection randomConnection = rng.nextDouble() < 0.5 ? this : other;
            if (this.isDisabled || other.isDisabled)
                randomConnection.disable();
            return new Connection(randomConnection);
        //if its a disjoint gene, return a clone depending on genome fitness
        } else if (rng.nextDouble() < inheritDisjoint) {
            return new Connection(this);
        }
        //otherwise, the cross of the two genes will not be in the child
        return null;
    }

    public Connection disable() {
        isDisabled = true;
        return this;
    }

    public long getId() {
        return (((long) in.id) << Integer.SIZE) | out.id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Connection))
            return false;

        Connection other = (Connection) o;
        return this.getId() == other.getId();
        //effectively equal if it connects the same nodes
    }

    @Override
    public int hashCode() {
        return Long.hashCode(getId());
    }
}
