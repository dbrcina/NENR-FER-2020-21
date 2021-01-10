package hr.fer.zemris.nenr.hw04.ea.mutation;

import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * An implementation of {@link Mutation} interface which implements Gaussian mutation.
 *
 * @author dbrcina
 */
public class GaussMutation implements Mutation<Solution<Double>> {

    private final Random random;
    private final double sigma;
    private final double pm;
    private final boolean addToExisting;

    public GaussMutation(Random random, double sigma, double pm, boolean addToExisting) {
        this.random = random;
        this.sigma = sigma;
        this.pm = pm;
        this.addToExisting = addToExisting;
    }

    @Override
    public Solution<Double> mutate(Solution<Double> solution) {
        for (int i = 0; i < solution.getNumberOfGenes(); i++) {
            if (random.nextDouble() >= pm) continue;
            double normalValue = random.nextGaussian() * sigma;
            double newValue = addToExisting ? solution.getGeneAt(i) + normalValue : normalValue;
            solution.setGeneAt(newValue, i);
        }
        return solution;
    }

}
