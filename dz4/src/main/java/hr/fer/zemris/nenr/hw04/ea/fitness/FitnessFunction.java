package hr.fer.zemris.nenr.hw04.ea.fitness;

import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

/**
 * Models a fitness function.
 *
 * @param <S> solution type.
 * @author dbrcina
 */
public interface FitnessFunction<S extends Solution<?>> {

    /**
     * Calculates fitness for the provided {@code solution}.
     *
     * @param solution solution
     * @return calculated fitness.
     */
    double calculateFitness(S solution);

}
