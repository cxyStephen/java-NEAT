package com.cxystephen.NEAT;

import com.cxystephen.NEAT.Configuration.NEATConfig;

import java.util.*;

public class Species {

    NEATConfig config;

    static int globalId = 0; //TODO: this should probably go in config

    public List<Organism> organisms;
    private int numOrganisms;
    private double fitness;
    int id;

    Random rng = new Random();

    public Species(NEATConfig config, Organism representative) {
        //create a new species with a organism that doesn't belong to other species
        this.config = config;
        this.id = globalId++;
        organisms = new ArrayList<>();
        organisms.add(representative);
        fitness += representative.genome.fitness;
        System.out.println("NEW SPECIES CREATED " + id + " FITNESS " + fitness);
    }

//    public Species(NEATConfig config, Species prevGeneration) {
//        //create a new generation of the same species from an old generation
//        this.config = config;
//        this.id = prevGeneration.id;
//        organisms = new ArrayList<>();
//        organisms.add(prevGeneration.electRepresentative());
//    }

    public Organism randomOrganism() {
        return organisms.get(rng.nextInt(organisms.size()));
    }

    public boolean addOrganism(Organism organism) {
        fitness += organism.genome.fitness;
        return organisms.add(organism);
    }

    public boolean belongs(Organism organism) {
        double compatibility = organism.genome.compatibilityWith(representative().genome);
        System.out.println("COMPAT+" +compatibility);
        return compatibility <= config.getCompatibilityThreshold();
    }

    public Organism representative() {
        return organisms.get(0);
    }

    public double adjustedFitness() { System.out.println("ADJ FITNESS " + (fitness/organisms.size()));
        //adjust fitness to protect topological innovation
        return fitness / organisms.size();
    }

    public int determineNumOrganisms(double populationFitness) {
        //this species should have a number of organisms proportional to its relative fitness
        numOrganisms = (int) Math.round((adjustedFitness()/populationFitness) * config.getPopulationSize());
        return numOrganisms;
    }

    public void cull() {
        Collections.sort(organisms, Comparator.comparingDouble(o -> o.genome.fitness));
        while(organisms.size() > culledSize())
            organisms.remove(organisms.size()-1);
    }

    public int culledSize() {
        return (int) Math.round(numOrganisms * (1 - config.getCullPercentage()));
    }

    public void reproduce() {
        while (organisms.size() < numOrganisms)
            organisms.add(randomOrganism().createOffspring(randomOrganism()));
    }

    @Override
    public String toString() {
        return "species" + id + "\torganisms: " + numOrganisms + "\tactual orgs: "+ organisms.size();
    }
}
