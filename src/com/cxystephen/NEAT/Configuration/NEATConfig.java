package com.cxystephen.NEAT.Configuration;

import com.cxystephen.NEAT.Node;
import java.util.List;

public class NEATConfig {
    private Node biasNode;

    private List<Double> inputs;
    private int numOutputs;

    //TODO: default values
    private double excessCoefficient;
    private double differenceCoefficient;
    private double compatibilityThreshold;

    private double addNodeMutationRate;
    private double addConnectionMutationRate;
    private double weightMutationRate;
    private double weightPerturbRate;

    private double inheritDisabledRate;

    private int populationSize;
    private double cullPercentage;
    private int stalenessThreshold;

    //TODO: factory class for each component for extendability?

    private NEATConfig () {}

    public static class NEATConfigBuilder {
        NEATConfig config;

        public NEATConfigBuilder(List<Double> inputs, int numOutputs) {
            config = new NEATConfig();
            config.inputs = inputs;
            config.numOutputs = numOutputs;
        }

        public void biasValue(double n) {
            config.biasNode = new Node(Node.NodeType.SENSOR, Node.INPUT_lAYER);
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
