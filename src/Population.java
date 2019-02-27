import java.util.ArrayList;
import java.util.List;

public class Population {

    NEATConfig config;

    List<Species> species = new ArrayList<>();
    List<Organism> organisms = new ArrayList<>();

    Organism topOrganism;
    int generation = 0;

    public Population(NEATConfig config) {
        this.config = config;
        for(int i = 0; i < config.getInitialPopulationSize(); i++) {
            Organism organism = new Organism(config.getInputs(), config.getNumOutputs());
            organisms.add(organism);
        }
    }

    public void speciate(){

    }
}
