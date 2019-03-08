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
        for (int i = 0; i < config.getPopulationSize(); i++) {
            Organism organism = new Organism(config);
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
        //determine fitness of entire population
        double totalFitness = 0;
        for(Species specie : species)
            totalFitness += specie.adjustedFitness();
        //when the fitness of the entire population does not improve for > 20 generations,
        // only the top two species are allowed to reproduce. TODO

        //kill poorly performing organisms and then create offspring
        for(Species specie : species) {
            specie.determineNumOrganisms(totalFitness);
            specie.cull();
            specie.reproduce();
        }
    }
}
