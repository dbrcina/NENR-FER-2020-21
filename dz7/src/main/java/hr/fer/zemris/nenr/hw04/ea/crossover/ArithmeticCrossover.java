package hr.fer.zemris.nenr.hw04.ea.crossover;

import hr.fer.zemris.nenr.hw04.ea.solution.DoubleArraySolution;
import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

/**
 * @author dbrcina
 */
public class ArithmeticCrossover implements Crossover<Solution<Double>> {

    @Override
    public Solution<Double> crossover(Solution<Double> parent1, Solution<Double> parent2) {
        double[] child = new double[parent1.getNumberOfGenes()];
        for (int i = 0; i < child.length; i++) {
            child[i] = (parent1.getGeneAt(i) + parent2.getGeneAt(i)) / 2;
        }
        return new DoubleArraySolution(child);
    }

}
