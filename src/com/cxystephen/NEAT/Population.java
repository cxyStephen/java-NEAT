package com.cxystephen.NEAT;

import com.cxystephen.NEAT.Configuration.NEATConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Population {

    NEATConfig config;

    List<Species> species = new ArrayList<>();
    List<Organism> organisms = new ArrayList<>();

    Organism topOrganism;
    int generation = 0;
    int staleness = 0;

    public Population(NEATConfig config) {
        this.config = config;
        for (int i = 0; i < config.getPopulationSize(); i++) {
            Organism organism = new Organism(config);
            organisms.add(organism);
        }
    }

    public boolean tempmethod(){
        if (topOrganism == null)
            return true;
        return topOrganism.genome.fitness < 4.0;
    }

    public void produceNextGeneration() {
        System.out.println("1GENERATION: " + generation + " SPECIES " + species.size() + " ORGANISMS " + organisms.size());
        mutateOrganisms();

        System.out.println("2GENERATION: " + generation + " SPECIES " + species.size() + " ORGANISMS " + organisms.size());
        determineFitness();
        System.out.println("3GENERATION: " + generation + " SPECIES " + species.size() + " ORGANISMS " + organisms.size());

        speciate();
        for(Species s : species)
            System.out.println(s);
        System.out.println("4GENERATION: " + generation + " SPECIES " + species.size() + " ORGANISMS " + organisms.size());
        findTopOrganism();
        System.out.println("5GENERATION: " + generation + " SPECIES " + species.size() + " ORGANISMS " + organisms.size());

        reproduceSpecies();
        for(Species s : species)
            System.out.println(s);
        System.out.println("6GENERATION: " + generation + " SPECIES " + species.size() + " ORGANISMS " + organisms.size());
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

    public void findTopOrganism() {
        boolean newTopOrganism = false;

        for (Organism organism : organisms) {
            if (topOrganism == null)
                topOrganism = organism;
            else if (organism.genome.fitness > topOrganism.genome.fitness) {
                topOrganism = organism;
                newTopOrganism = true;
                staleness = 0;
            }
        }
System.out.println("TOP ORGANISM: " + topOrganism.genome.fitness);
        if(!newTopOrganism)
            staleness++;
    }

    public void reproduceSpecies() {
        //if no improvements in n generations, only retain top two species
        if (staleness > config.getStalenessThreshold())
            killMostSpecies();

        //determine fitness of entire population
        double totalFitness = 0;
        for(Species specie : species)
            totalFitness += specie.adjustedFitness();

        //determine how many organisms each species should contain
        // axe the entire species if it wouldn't contain anything
        for(int i = species.size() - 1; i >= 0; i--) {
            Species specie = species.get(i);
            if (specie.determineNumOrganisms(totalFitness) == 0 || specie.culledSize() == 0)
                species.remove(i);
        }

        //clear organisms to create new generation
        organisms.clear();

        //kill poorly performing organisms and then create offspring
        for(Species specie : species) {
            specie.cull();
            specie.reproduce();
            organisms.addAll(specie.organisms);
        }
    }

    public void killMostSpecies() {
        Collections.sort(species, Comparator.comparingDouble(s -> s.adjustedFitness()));
        while(species.size() > 2)
            species.remove(species.size()-1);
    }
}
