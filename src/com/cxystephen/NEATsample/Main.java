package com.cxystephen.NEATsample;

import com.cxystephen.NEAT.Configuration.NEATConfig;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //XOR Truth Table
        // T T -> F
        // T F -> T
        // F T -> T
        // F F -> F

        Double input1 = 1.0;
        Double input2 = 1.0;

        List<Double> inputs = new ArrayList<>();
        inputs.add(input1);
        inputs.add(input2);

        NEATConfig config = new NEATConfig.NEATConfigBuilder(inputs, 1)
                            .populationSize(50).build();
        config.run();
    }
}