package com.cxystephen.NEAT;

import com.cxystephen.NEAT.Configuration.NEATConfig;

import java.util.ArrayList;
import java.util.List;

public class Organism {
    Genome genome;
    List<Node> inputNodes;
    List<Node> outputNodes;

    NEATConfig config;

    public Organism(NEATConfig config) {
        //TODO: initialize default genome (no connections)
        this.config = config;
    }

    public Organism(NEATConfig config, Genome genome) {
        this.config = config;
        this.genome = genome;
    }

    public void setInput(List<Double> inputs) {
        for (int i = 0; i < inputs.size(); i++)
            inputNodes.get(i).value = inputs.get(i);
    }

    public List<Double> getOutput() {
        genome.propagateInputs();
        List<Double> out = new ArrayList<>();
        for (Node output : outputNodes)
            out.add(output.value);
        return out;
    }

    public Organism createOffspring(Organism other) {
        return new Organism(config, this.genome.cross(other.genome));
    }

    public void determineFitness() {
        //TODO
    }
}
