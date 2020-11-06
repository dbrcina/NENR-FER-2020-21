package hr.fer.zemris.nenr.hw04;

import hr.fer.zemris.nenr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.nenr.hw04.ea.initializer.RandomDoublePopulationInitializer;
import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.util.List;

/**
 * @author dbrcina
 */
public class Demo {

    public static void main(String[] args) {
        PopulationInitializer<Solution<Double>> initializer =
                new RandomDoublePopulationInitializer(10, -4, 4);
        List<Solution<Double>> population = initializer.generatePopulation(5);
        System.out.println(population);
    }

}
