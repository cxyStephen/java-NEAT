import java.util.ArrayList;
import java.util.List;

public class Species {

    static int globalId = 0;

    private List<Genome> genomes;
    private double fitness;
    int id;

    public Species(Genome representative) {
        //create a new species with a genome that doesn't belong to other species
        this.id = globalId++;
        genomes = new ArrayList<>();
        genomes.add(representative);
    }

    public Species(Species prevGeneration) {
        //create a new generation of the same species from an old generation
        this.id = prevGeneration.id;
        genomes = new ArrayList<>();
        genomes.add(prevGeneration.electRepresentative());
    }

    public Genome electRepresentative() {
        //randomly select a genome to represent the species
        int randomIndex = (int) (Math.random() * genomes.size());
        return genomes.get(randomIndex);
    }

    public boolean addGenome(Genome genome) {
        fitness += genome.fitness;
        return genomes.add(genome);
    }

    public Genome representative() {
        return genomes.get(0);
    }

    public double adjustedFitness() {
        //adjust fitness to protect topological innovation
        return fitness / genomes.size();
    }
}
