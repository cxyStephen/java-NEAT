package com.cxystephen.NEAT.Configuration;

import com.cxystephen.NEAT.Node;
import com.cxystephen.NEAT.Population;

import java.util.ArrayList;
import java.util.List;

public class NEATConfig {
    private Node biasNode;

    private List<Double> inputs;
    private int numOutputs;

    private double excessCoefficient = 2.0;
    private double differenceCoefficient = 0.2;
    private double compatibilityThreshold = 3.0;

    private double addNodeMutationRate = 0.03;
    private double addConnectionMutationRate = 0.05;
    private double weightMutationRate = 0.8;
    private double weightPerturbRate = 0.9;

    private double inheritDisabledRate = 0.75;

    private int populationSize = 20;
    private double cullPercentage = 0.75;
    private int stalenessThreshold = 20;

    //TODO: factory class for each component for extendability?
    private List<Node> inputNodes = new ArrayList<>();
    private List<Node> outputNodes = new ArrayList<>(); //TODO: oops, don't regenerate input nodes multiple times

    public void run() { //TODO: this method should go in thread class
        Population population = new Population(this);
        while (population.tempmethod()) {
            population.produceNextGeneration();
        }
    }

    private NEATConfig () {}

    public static class NEATConfigBuilder {
        NEATConfig config;

        public NEATConfigBuilder(List<Double> inputs, int numOutputs) {
            config = new NEATConfig();
            config.inputs = inputs;
            config.numOutputs = numOutputs;
            config.biasNode = new Node(Node.NodeType.SENSOR, Node.INPUT_lAYER);

            for (double d : config.getInputs())
                config.inputNodes.add(Node.inputNode(d));
            for (int i = 0; i < config.getNumOutputs(); i++)
                config.outputNodes.add(new Node(Node.NodeType.OUTPUT, Node.OUTPUT_LAYER));
        }

        public void excessCoefficient(double n) {
            config.excessCoefficient = n;
        }
        public void differenceCoefficient(double n) {
            config.differenceCoefficient = n;
        }
        public void compatibilityThreshold(double n) {
            config.compatibilityThreshold = n;
        }
        public void addNodeMutationRate(double n) {
            config.addNodeMutationRate = n;
        }
        public void addConnectionMutationRate(double n) {
            config.addConnectionMutationRate = n;
        }
        public void weightMutationRate(double n) {
            config.weightMutationRate = n;
        }
        public void weightPerturbRate(double n) {
            config.weightPerturbRate = n;
        }
        public void inheritDisabledRate(double n) {
            config.inheritDisabledRate = n;
        }
        public void populationSize(int n) {
            config.populationSize = n;
        }
        public void cullPercentage(double n) {
            config.cullPercentage = n;
        }
        public void stalenessThreshold(int n) {
            config.stalenessThreshold = n;
        }

        public NEATConfig build() {
            return config;
        }
    }

    public Node biasNode() {
        return biasNode;
    }
    public List<Node> inputNodes() {
        return inputNodes;
    }
    public List<Node> outputNodes() {
        return outputNodes;
    }

    public List<Double> getInputs() {
        return inputs;
    }
    public int getNumOutputs() {
        return numOutputs;
    }
    public double getExcessCoefficient() {
        return excessCoefficient;
    }
    public double getDifferenceCoefficient() {
        return differenceCoefficient;
    }
    public double getCompatibilityThreshold() {
        return compatibilityThreshold;
    }
    public double getAddNodeMutationRate() {
        return addNodeMutationRate;
    }
    public double getAddConnectionMutationRate() {
        return addConnectionMutationRate;
    }
    public double getWeightMutationRate() {
        return weightMutationRate;
    }
    public double getWeightPerturbRate() {
        return weightPerturbRate;
    }
    public double getInheritDisabledRate() {
        return inheritDisabledRate;
    }
    public int getPopulationSize() {
        return populationSize;
    }
    public double getCullPercentage() {
        return cullPercentage;
    }
    public int getStalenessThreshold() {
        return stalenessThreshold;
    }
}
