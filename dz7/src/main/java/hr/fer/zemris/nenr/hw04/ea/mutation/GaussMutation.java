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

    public GaussMutation(Random random, double sigma, double pm) {
        this.random = random;
        this.sigma = sigma;
        this.pm = pm;
    }

    @Override
    public Solution<Double> mutate(Solution<Double> solution) {
        for (int i = 0; i < solution.getNumberOfGenes(); i++) {
            if (random.nextDouble() >= pm) continue;
            double newValue = solution.getGeneAt(i) + random.nextGaussian() * sigma;
            solution.setGeneAt(newValue, i);
        }
        return solution;
    }

}
