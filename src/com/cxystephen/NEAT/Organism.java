package com.cxystephen.NEAT;

import com.cxystephen.NEAT.Configuration.NEATConfig;
import com.cxystephen.NEAT.Node.NodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Organism {
    Genome genome;
    List<Node> inputNodes;
    List<Node> outputNodes;

    NEATConfig config;

    //TODO: why did i need this class again??
    public Organism(NEATConfig config) {
        //initialize default genome (no connections)
        this.config = config;

        this.genome = new Genome(config);
        this.inputNodes = config.inputNodes();
        this.outputNodes = config.outputNodes();

        for (Node input : config.inputNodes())
            genome.addNode(input);
        for (Node output : config.outputNodes())
            genome.addNode(output);
//        inputNodes = new ArrayList<>();
//        for (double d : config.getInputs()) {
//            inputNodes.add(Node.inputNode(d));
//            genome.addNode(inputNodes.get(0));
//        }
//
//        outputNodes = new ArrayList<>();
//        for (int i = 0; i < config.getNumOutputs(); i++) {
//            outputNodes.add(new Node(NodeType.OUTPUT, Node.OUTPUT_LAYER));
//            genome.addNode(outputNodes.get(0));
//        }
    }

    public Organism(NEATConfig config, Genome genome) {
        this.config = config;
        this.genome = genome;
        this.inputNodes = config.inputNodes();
        this.outputNodes = config.outputNodes();
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
        //TODO (temp)
        double fitness = 0;
        setInput(Arrays.asList(new Double[]{1.0,1.0}));
        if (Math.round(getOutput().get(0)) == 0)
            fitness += 1;
        setInput(Arrays.asList(new Double[]{1.0,0.0}));
        if (Math.round(getOutput().get(0)) == 1)
            fitness += 1;
        setInput(Arrays.asList(new Double[]{0.0,1.0}));
        if (Math.round(getOutput().get(0)) == 1)
            fitness += 1;
        setInput(Arrays.asList(new Double[]{0.0,0.0}));
        if (Math.round(getOutput().get(0)) == 0)
            fitness += 1;
        genome.fitness = fitness;
        System.out.println(fitness);
    }
}
