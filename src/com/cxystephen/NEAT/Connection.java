package com.cxystephen.NEAT;

import com.cxystephen.NEAT.Configuration.NEATConfig;

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
        this.weight = (rng.nextDouble() * -2) + 1; //random weight in range (-1,1]

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

    static Connection biasConnection(NEATConfig config, Node n) {
        Connection connection = new Connection(config, config.biasNode(), n);
        config.biasNode().connected.add(n);
        return connection;
    }

    public void mutateWeight() {
        //some chance to slightly adjust the weight
        if (rng.nextDouble() < config.getWeightPerturbRate())
            weight += rng.nextGaussian()/50;
        //otherwise, generate an entirely new weight
        else
            weight = (rng.nextDouble() * -2) + 1;

        weight = Math.max(-1, Math.min(1, weight)); //clamp weight to [-1,1]
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
