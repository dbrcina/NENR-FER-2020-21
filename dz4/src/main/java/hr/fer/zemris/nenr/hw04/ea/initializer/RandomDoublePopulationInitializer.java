package hr.fer.zemris.nenr.hw04.ea.initializer;

import hr.fer.zemris.nenr.hw04.ea.solution.DoubleArraySolution;
import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * An implementation of {@link PopulationInitializer} interface that generates {@link DoubleArraySolution} solutions.
 *
 * @author dbrcina
 */
public class RandomDoublePopulationInitializer implements PopulationInitializer<Solution<Double>> {

    private final int unitSize;
    private final double minValue;
    private final double maxValue;
    private final Random random = new Random();

    public RandomDoublePopulationInitializer(int unitSize, double minValue, double maxValue) {
        if (unitSize <= 0) {
            throw new IllegalArgumentException("Unit size is <= 0!");
        }
        if (minValue > maxValue) {
            throw new IllegalArgumentException("MinValue > MaxValue!");
        }
        this.unitSize = unitSize;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public Solution<Double> generateSolution() {
        double[] genes = new double[unitSize];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = minValue + random.nextDouble() * (maxValue - minValue);
        }
        return new DoubleArraySolution(genes);
    }

}
