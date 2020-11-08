package hr.fer.zemris.nenr.hw04.ea.crossover;

import hr.fer.zemris.nenr.hw04.ea.solution.DoubleArraySolution;
import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * An implementation of {@link Crossover} interface which provides <i>BLX-alpha crossover</i> for
 * solutions that are modeled through doubles.
 *
 * @author dbrcina
 */
public class BLXACrossover implements Crossover<Solution<Double>> {

    private final Random random;
    private final double alpha;
    private final double minValue;
    private final double maxValue;

    public BLXACrossover(Random random, double alpha, double minValue, double maxValue) {
        this.random = random;
        this.alpha = alpha;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public Solution<Double> crossover(Solution<Double> parent1, Solution<Double> parent2) {
        double[] child = new double[parent1.getNumberOfGenes()];
        for (int i = 0; i < child.length; i++) {
            double ci1 = parent1.getGeneAt(i);
            double ci2 = parent2.getGeneAt(i);
            double ciMin = Math.min(ci1, ci2);
            double ciMax = Math.max(ci1, ci2);
            double interval = ciMax - ciMin;
            double lb = Math.max(minValue, ciMin - interval * alpha);
            double ub = Math.min(maxValue, ciMax + interval * alpha);
            child[i] = lb + random.nextDouble() * (ub - lb);
        }
        return new DoubleArraySolution(child);
    }

}
