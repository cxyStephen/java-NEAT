import java.util.List;

public class NEATConfig {
    private List<Double> inputs;
    private int numOutputs;

    //TODO: default values
    private double excessCoefficient;
    private double differenceCoefficient;
    private double compatibilityThreshold;

    private double addNodeMutationRate;
    private double addConnectionMutationRate;
    private double weightMutationRate;

    private double inheritDisabledRate;

    private int initialPopulationSize;

    //TODO: factory class for each component for extendability?

    private NEATConfig () {}

    public static class NEATConfigBuilder {
        NEATConfig config;

        public NEATConfigBuilder(List<Double> inputs, int numOutputs) {
            config = new NEATConfig();
            config.inputs = inputs;
            config.numOutputs = numOutputs;
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
        public void inheritDisabledRate(double n) {
            config.inheritDisabledRate = n;
        }
        public void initialPopulationSize(int n) {
            config.initialPopulationSize = n;
        }

        public NEATConfig build() {
            return config;
        }
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
    public double getInheritDisabledRate() {
        return inheritDisabledRate;
    }
    public int getInitialPopulationSize() {
        return initialPopulationSize;
    }
}
