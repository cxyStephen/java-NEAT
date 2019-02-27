import java.util.List;
import java.util.Queue;

public class Population {

    List<Species> species;
    Queue<Genome> genomes; //TODO: should organisms be tracked instead??

    Genome bestGenome; //TODO: best organism?
    int generation = 0;

    public Population() {
        for(int i = 0; i < 20; i++) { //TODO: initial size in config
            Genome genome = new Genome();
            genomes.add(genome);
        }
    }
}
