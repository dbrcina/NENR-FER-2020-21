package hr.fer.zemris.nenr.hw04.ea.crossover;

import hr.fer.zemris.nenr.hw04.ea.solution.DoubleArraySolution;
import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * @author dbrcina
 */
public class UniformCrossover implements Crossover<Solution<Double>> {

    private final Random random;

    public UniformCrossover(Random random) {
        this.random = random;
    }

    @Override
    public Solution<Double> crossover(Solution<Double> parent1, Solution<Double> parent2) {
        double[] child = new double[parent1.getNumberOfGenes()];
        for (int i = 0; i < child.length; i++) {
            child[i] = random.nextDouble() < 0.5 ? parent1.getGeneAt(i) : parent2.getGeneAt(i);
        }
        return new DoubleArraySolution(child);
    }

}
