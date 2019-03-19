package com.cxystephen.NEAT;

import com.cxystephen.NEAT.Configuration.NEATConfig;
import com.cxystephen.NEAT.Node.NodeType;

import java.util.ArrayList;
import java.util.List;

public class Organism {
    Genome genome;
    List<Node> inputNodes;
    List<Node> outputNodes;

    NEATConfig config;

    public Organism(NEATConfig config) {
        //initialize default genome (no connections)
        this.config = config;

        inputNodes = new ArrayList<>();
        for (double d : config.getInputs()) {
            inputNodes.add(Node.inputNode(d));
            genome.addNode(inputNodes.get(0));
        }

        outputNodes = new ArrayList<>();
        for (int i = 0; i < config.getNumOutputs(); i++) {
            outputNodes.add(new Node(NodeType.OUTPUT, Node.OUTPUT_LAYER));
            genome.addNode(outputNodes.get(0));
        }

        this.genome = new Genome(config);
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
