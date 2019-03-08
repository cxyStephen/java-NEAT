import java.util.ArrayList;
import java.util.List;

public class Organism {
    Genome genome;
    List<Node> inputNodes;
    List<Node> outputNodes;

    public Organism(List<Double> input, int outputs) {

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

    public void determineFitness() {
        //TODO
    }
}
