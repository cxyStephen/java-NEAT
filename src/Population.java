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
        for (int i = 0; i < config.getInitialPopulationSize(); i++) {
            Organism organism = new Organism(config.getInputs(), config.getNumOutputs());
            organisms.add(organism);
        }
    }

    public void produceNextGeneration() {
        mutateOrganisms();
        speciate();
        determineFitness();
        reproduceSpecies();
        generation++;
    }

    public void mutateOrganisms() {
        for(Organism organism : organisms)
            organism.genome.mutate();
    }

    public void speciate() {
        for (Organism organism : organisms) {
            boolean matched = false;
            for (Species specie: species) { //the singular of species is species??
                if (specie.belongs(organism)) {
                    specie.addOrganism(organism);
                    matched = true;
                }
            }

            if(!matched) //create a new species if this organism doesn't match existing ones
                species.add(new Species(config, organism));
        }
    }

    public void determineFitness() {
        for (Organism organism : organisms)
            organism.determineFitness();
    }

    public void reproduceSpecies() {
        //sort species by fitness
        //determine number of organisms for each species
        //kill poorly performing organisms
        //create offspring

        //when the fitness of the entire population does not improve for > 20 generations,
        // only the top two species are allowed to reproduce. (TODO: what's entire pop fitness?)
    }
}
