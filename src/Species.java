import java.util.ArrayList;
import java.util.List;

public class Species {

    NEATConfig config;

    static int globalId = 0;

    private List<Organism> organisms;
    private double fitness;
    int id;

    public Species(NEATConfig config, Organism representative) {
        //create a new species with a organism that doesn't belong to other species
        this.config = config;
        this.id = globalId++;
        organisms = new ArrayList<>();
        organisms.add(representative);
    }

    public Species(NEATConfig config, Species prevGeneration) {
        //create a new generation of the same species from an old generation
        this.config = config;
        this.id = prevGeneration.id;
        organisms = new ArrayList<>();
        organisms.add(prevGeneration.electRepresentative());
    }

    public Organism electRepresentative() {
        //randomly select an organism to represent the species
        int randomIndex = (int) (Math.random() * organisms.size());
        return organisms.get(randomIndex);
    }

    public boolean addOrganism(Organism organism) {
        fitness += organism.genome.fitness;
        return organisms.add(organism);
    }

    public boolean belongs(Organism organism) {
        double compatibility = organism.genome.compatibilityWith(representative().genome);
        return compatibility <= config.getCompatibilityThreshold();
    }

    public Organism representative() {
        return organisms.get(0);
    }

    public double adjustedFitness() {
        //adjust fitness to protect topological innovation
        return fitness / organisms.size();
    }
}
