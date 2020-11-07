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
    private final double minvalue;
    private final double maxvalue;

    public GaussMutation(Random random, double sigma, double minvalue, double maxvalue) {
        this.random = random;
        this.sigma = sigma;
        this.minvalue = minvalue;
        this.maxvalue = maxvalue;
    }

    @Override
    public Solution<Double> mutate(Solution<Double> solution) {
        for (int i = 0; i < solution.getNumberOfGenes(); i++) {
            double newValue = solution.getGeneAt(i) + random.nextGaussian() * sigma;
            if (newValue < minvalue) newValue = minvalue;
            else if (newValue > maxvalue) newValue = maxvalue;
            solution.setGeneAt(newValue, i);
        }
        return solution;
    }

}
